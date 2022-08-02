package com.gfa.library.books.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchDto {
  private String title;
  private String author;
  private String category;
  private Integer releaseYear;

}
