package com.gfa.library.books.models.dtos;

import com.gfa.library.authors.dtos.AuthorDto;
import com.gfa.library.categories.dtos.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookAddDto {
  @NotNull(message = "ISBN number required")
  @Size(min = 10, max = 10)
  private String isbn;
  @NotNull(message = "Title required")
  private String title;
  @NotNull(message = "Category required")
  private Set<CategoryDto> categories;
  @NotNull(message = "Author(s) required")
  private Set<AuthorDto> authors;
  @NotNull(message = "Release year required")
  private Integer releaseYear;
  @NotNull(message = "Please set availability")
  private String availability;

}
