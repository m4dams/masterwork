package com.gfa.library.authors.repositories;

import com.gfa.library.authors.models.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Integer> {
}
