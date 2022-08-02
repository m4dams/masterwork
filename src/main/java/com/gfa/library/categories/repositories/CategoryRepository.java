package com.gfa.library.categories.repositories;

import com.gfa.library.categories.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
}
