package com.gfa.library.users.models.dtos;

import com.gfa.library.addresses.models.dtos.AddressDto;
import com.gfa.library.addresses.models.entities.Address;
import com.gfa.library.users.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
  @NotNull(message = "Username is required")
  private String username;
  @NotNull(message = "Password is required")
  @Size(min=8, message = "Password must be at least 8 characters long")
  private String password;
  @NotNull(message = "Email is required")
  private String email;
  @NotNull(message = "First name is required")
  private String firstName;
  @NotNull(message = "Last name is required")
  private String lastName;
  @NotNull(message = "Date of birth is required")
  private LocalDate dateOfBirth;
  @NotNull(message = "Address is required")
  @Embedded
  private AddressDto address;
}
