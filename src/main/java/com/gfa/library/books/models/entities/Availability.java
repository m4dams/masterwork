package com.gfa.library.books.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gfa.library.books.models.TypeOfAvailability;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "availabilities")
public class Availability {
  @Id
  private Integer id;
  private String name;
  @OneToMany(mappedBy = "availability")
  @JsonManagedReference
  private List<Book> books;

  public Availability(TypeOfAvailability type) {
    this.id = 1;
    this.name = type.toString();
    this.books = new ArrayList<>();
  }
}
