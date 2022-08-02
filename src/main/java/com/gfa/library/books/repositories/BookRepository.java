package com.gfa.library.books.repositories;

import com.gfa.library.books.models.entities.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface BookRepository extends CrudRepository<Book, Integer> {

  @Query(value = "SELECT b.* , au.*, ca.* FROM books b \n" +
      "left join authors_books ab on b.id = ab.books_id \n" +
      "left join categories_books cb on b.id = cb.books_id \n" +
      "left join authors au on au.id=ab.authors_id \n" +
      "left join categories ca on ca.id=cb.categories_id \n" +
      "WHERE ?1 IS NULL OR b.title = ?1 AND ?2 IS NULL OR au.name = ?2 \n" +
      "AND ?3 IS NULL OR ca.name = ?3 AND ?4 IS NULL OR b.release_year= ?4 \n" +
      "GROUP BY ca.id, au.id, b.id", nativeQuery = true)
  Set<Book> findByCriteria(String title, String author, String category, Integer releaseYear);

  @Modifying
  @Query(value = "DELETE FROM authors_books WHERE authors_books.books_id = ?1 ;" +
      "DELETE FROM categories_books WHERE categories_books.books_id = ?1 ; " +
      "DELETE FROM books WHERE books.id = ?1 ;", nativeQuery = true)
  @Transactional
  void delete(Integer bookId);
}
