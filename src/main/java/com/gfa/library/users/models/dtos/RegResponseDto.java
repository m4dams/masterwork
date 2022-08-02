package com.gfa.library.users.models.dtos;

import com.gfa.library.addresses.models.dtos.AddressDto;
import com.gfa.library.addresses.models.entities.Address;
import com.gfa.library.users.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegResponseDto {
  private int id;
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private LocalDate dateOfBirth;
  private AddressDto addressDto;

  public RegResponseDto(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.dateOfBirth = user.getDateOfBirth();
    this.addressDto = new AddressDto(user.getAddress());
  }
}
