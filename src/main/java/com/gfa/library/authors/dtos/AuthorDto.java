package com.gfa.library.authors.dtos;

import com.gfa.library.authors.models.Author;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorDto {
  private String name;

  public AuthorDto(Author author) {
    this.name = author.getName();
  }
}
