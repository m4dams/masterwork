package com.gfa.library.users.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UsersDto {
  List<UserFullDto> users;

  public UsersDto() {
    this.users = new ArrayList<>();
  }
}
