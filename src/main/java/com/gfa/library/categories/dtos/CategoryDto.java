package com.gfa.library.categories.dtos;

import com.gfa.library.categories.models.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
  private String name;

  public CategoryDto(Category category) {
    this.name = category.getName();
  }
}
