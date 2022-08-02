package com.gfa.library.books.services;

import com.gfa.library.authors.dtos.AuthorDto;
import com.gfa.library.authors.models.AuthorsBooks;
import com.gfa.library.books.models.TypeOfAvailability;
import com.gfa.library.books.models.dtos.BookAddDto;
import com.gfa.library.books.models.dtos.BookFullDto;
import com.gfa.library.books.models.dtos.BookSearchDto;
import com.gfa.library.books.models.dtos.BooksDto;
import com.gfa.library.books.models.entities.Availability;
import com.gfa.library.books.models.entities.Book;
import com.gfa.library.books.repositories.BookRepository;
import com.gfa.library.categories.dtos.CategoryDto;
import com.gfa.library.categories.models.CategoriesBooks;
import com.gfa.library.exceptions.AuthorizationException;
import com.gfa.library.exceptions.NotFoundException;
import com.gfa.library.users.models.entities.User;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookServiceImpl implements BookService {
  private final BookRepository bookRepository;

  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public BookFullDto addBook(User user, BookAddDto newBook) {
    if (user.isUser()) {
      throw new AuthorizationException("Unauthorized User!");
    }
    return storeNewBook(newBook);
  }

  @Override
  public BookFullDto viewBook(int id) {
    Book book = getById(id);
    return new BookFullDto(book);
  }


  @Override
  public void deleteBookWithId(User user, int bookId) {
    if (user.isUser()) {
      throw new AuthorizationException();
    }
    Book book = getById(bookId);
    bookRepository.delete(book);
  }

  @Override
  public BooksDto searchForBooksWithCriteria(BookSearchDto search) {
    Set<Book> books = bookRepository.findByCriteria(search.getTitle()
        , search.getAuthor(), search.getCategory(), search.getReleaseYear());

    return convert(books);
  }


  @Override
  public BooksDto convert(Set<Book> books) {
    BooksDto booksDto = new BooksDto();
    booksDto.setBooks(books.stream().map(BookFullDto::new).collect(Collectors.toSet()));
    return booksDto;
  }


  private BookFullDto storeNewBook(BookAddDto newBook) {
    Book book = new Book();
    book.setIsbn(newBook.getIsbn());
    book.setTitle(newBook.getTitle());
    book.setCategories(findCategories(newBook.getCategories(), book));
    book.setAuthors(findAuthors(newBook.getAuthors(), book));
    book.setReleaseYear(newBook.getReleaseYear());
    book.setAvailability(new Availability(findAvailability(newBook.getAvailability())));
    Book storedBook = bookRepository.save(book);

    return new BookFullDto(storedBook);
  }

  private Set<AuthorsBooks> findAuthors(Set<AuthorDto> authors, Book book) {
    return authors.stream().map(e -> new AuthorsBooks(e.getName(), book))
        .collect(Collectors.toSet());
  }

  private Set<CategoriesBooks> findCategories(Set<CategoryDto> categories, Book book) {
    return categories.stream().map(e -> new CategoriesBooks(e.getName(), book))
        .collect(Collectors.toSet());
  }

  private TypeOfAvailability findAvailability(String availability) {
    return Stream.of(TypeOfAvailability.values())
        .filter(e -> e.toString().equals(availability))
        .findFirst().orElseThrow(() -> new NotFoundException("Availability type"));
  }

  private Book getById(int id) {
    return bookRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Book"));
  }

}
