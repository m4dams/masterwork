package com.gfa.library.users.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gfa.library.addresses.models.entities.Address;
import com.gfa.library.books.models.entities.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "user_name")
  private String username;
  private String password;
  private String email;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;
  @ManyToOne(cascade = CascadeType.ALL)
  @JsonBackReference
  private Address address;
  @ManyToOne
  @JoinColumn(name = "authorization")
  @JsonBackReference
  private AccessLevel accessLevel;
  @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JsonManagedReference
  private List<Book> books;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }

  public boolean isAdmin() {
    return this.accessLevel.getName().equals("ADMIN");
  }

  public boolean isUser() {return this.accessLevel.getName().equals("USER");}
}
