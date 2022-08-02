package com.gfa.library.books.services;

import com.gfa.library.books.models.dtos.BookAddDto;
import com.gfa.library.books.models.dtos.BookFullDto;
import com.gfa.library.books.models.dtos.BookSearchDto;
import com.gfa.library.books.models.dtos.BooksDto;
import com.gfa.library.books.models.entities.Book;
import com.gfa.library.users.models.entities.User;

import java.util.Set;

public interface BookService {

  BookFullDto addBook(User user, BookAddDto newBook);

  BookFullDto viewBook(int id);

  void deleteBookWithId(User user, int bookId);

  BooksDto searchForBooksWithCriteria(BookSearchDto search);

  BooksDto convert(Set<Book> books);
}
