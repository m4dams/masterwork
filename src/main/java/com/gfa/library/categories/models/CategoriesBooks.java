package com.gfa.library.categories.models;

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
@Entity
@NoArgsConstructor
@Table(name = "categories_books")
public class CategoriesBooks {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "categories_id")
  @JsonBackReference
  private Category category;
  @ManyToOne()
  @JoinColumn(name = "books_id")
  @JsonBackReference
  private Book book;

  public CategoriesBooks(String name, Book book) {
    this.id = 1;
    this.category = new Category(name);
    this.book = book;
  }


}
