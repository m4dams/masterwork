package com.gfa.library.books.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class BooksDto {
  Set<BookFullDto> books;

  public BooksDto() {
    this.books = new HashSet<>();
  }

  public BooksDto(Set<BookFullDto> books) {
    this.books = books;
  }
}
