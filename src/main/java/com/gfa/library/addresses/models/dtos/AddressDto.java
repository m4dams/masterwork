package com.gfa.library.addresses.models.dtos;

import com.gfa.library.addresses.models.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
  private int id;
  @NotNull(message = "County is required")
  private String county;
  @NotNull(message = "City is required")
  private String city;
  @NotNull(message = "Postal code is required")
  private Integer postalCode;
  @NotNull(message = "Street and Building are required")
  private String streetAndBuilding;

  public AddressDto(Address address) {
    this.id = address.getId();
    this.county = address.getCounty();
    this.city = address.getCity();
    this.postalCode = address.getPostalCode();
    this.streetAndBuilding = address.getStreetAndBuilding();
  }
}
