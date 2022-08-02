package com.gfa.library.users.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gfa.library.users.models.TypeOfAccess;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
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
@Table(name = "access_levels")
public class AccessLevel {
  @Id
  private int id;
  private String name;
  private String description;
  @OneToMany(mappedBy = "accessLevel" , cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<User> users;

  public AccessLevel(TypeOfAccess type) {
    this.id = 1;
    this.name = type.toString();
    this.users = new ArrayList<>();
  }

}
