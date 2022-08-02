package com.gfa.library.books.models.dtos;

import com.gfa.library.authors.dtos.AuthorDto;
import com.gfa.library.books.models.entities.Book;
import com.gfa.library.categories.dtos.CategoryDto;
import com.gfa.library.users.models.dtos.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class BookFullDto {

  private Integer id;
  private String isbn;
  private String title;
  private Set<CategoryDto> categories;
  private Set<AuthorDto> authors;
  private Integer releaseYear;
  private String availability;
  private LocalDate returnTime;
  private UserDto user;

  public BookFullDto() {
    this.categories = new HashSet<>();
    this.authors = new HashSet<>();
  }

  public BookFullDto(Book book) {
    this.id = book.getId();
    this.isbn = book.getIsbn();
    this.title = book.getTitle();
    this.categories =
        book.getCategories().stream()
            .map(e -> new CategoryDto(e.getCategory()))
            .collect(Collectors.toSet());
    this.authors = book.getAuthors().stream()
        .map(e -> new AuthorDto(e.getAuthor()))
        .collect(Collectors.toSet());
    this.releaseYear = book.getReleaseYear();
    this.availability = book.getAvailability().getName();
    this.returnTime = book.getReturnTime();
    this.user = book.getUser() == null ? null : new UserDto(book.getUser());
  }
}
