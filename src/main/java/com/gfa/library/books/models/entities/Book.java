package com.gfa.library.books.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gfa.library.authors.models.AuthorsBooks;
import com.gfa.library.categories.models.CategoriesBooks;
import com.gfa.library.users.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String isbn;
  private String title;
  @OneToMany(mappedBy = "book", cascade = CascadeType.MERGE)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JsonManagedReference
  private Set<CategoriesBooks> categories;
  @OneToMany(mappedBy = "book", cascade = CascadeType.MERGE)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JsonManagedReference
  private Set<AuthorsBooks> authors;
  @Column(name = "release_year")
  private Integer releaseYear;
  @ManyToOne()
  @JoinColumn(name = "availability")
  @JsonBackReference
  private Availability availability;
  @Column(name = "return_time")
  private LocalDate returnTime;
  @ManyToOne(cascade = CascadeType.ALL)
  @JsonBackReference
  private User user;

}
