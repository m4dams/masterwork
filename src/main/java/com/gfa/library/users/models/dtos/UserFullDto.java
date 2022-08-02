package com.gfa.library.users.models.dtos;

import com.gfa.library.addresses.models.dtos.AddressDto;
import com.gfa.library.books.models.dtos.BookFullDto;
import com.gfa.library.books.models.entities.Book;
import com.gfa.library.users.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFullDto {
  private Integer id;
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private LocalDate dateOfBirth;
  private AddressDto address;
  private String accessLevel;
  private List<BookFullDto> books;

  public UserFullDto(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.dateOfBirth = user.getDateOfBirth();
    this.address = new AddressDto(user.getAddress());
    this.accessLevel = user.getAccessLevel().getName();
    this.books = user.getBooks().stream().map(BookFullDto::new)
        .collect(Collectors.toList());
  }
}
