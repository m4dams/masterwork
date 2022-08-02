package com.gfa.library.addresses.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gfa.library.addresses.models.dtos.AddressDto;
import com.gfa.library.users.models.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String county;
  private String city;
  private Integer postalCode;
  @Column(name = "street_and_building")
  private String streetAndBuilding;
  @OneToMany(mappedBy = "address",
      cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference
  private List<User> users;


  public Address(int id, String county, String city, Integer postalCode, String streetAndBuilding, List<User> user) {
    this.id = id;
    this.county = county;
    this.city = city;
    this.postalCode = postalCode;
    this.streetAndBuilding = streetAndBuilding;
    this.users = new ArrayList<>();
  }

  public Address(AddressDto addressDto) {
    this.id = addressDto.getId();
    this.county = addressDto.getCounty();
    this.city = addressDto.getCity();
    this.postalCode = addressDto.getPostalCode();
    this.streetAndBuilding = addressDto.getStreetAndBuilding();
    this.users = new ArrayList<>();
  }


}
