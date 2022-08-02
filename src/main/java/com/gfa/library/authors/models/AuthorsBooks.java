package com.gfa.library.authors.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gfa.library.books.models.entities.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "authors_books")
public class AuthorsBooks {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "authors_id")
  @JsonBackReference
  private Author author;
  @ManyToOne()
  @JoinColumn(name = "books_id")
  @JsonBackReference
  private Book book;

  public AuthorsBooks(String name, Book book) {
    this.id = 1;
    this.author = new Author(name);
    this.book = book;
  }


}
