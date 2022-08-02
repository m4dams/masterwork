package com.gfa.library.users.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gfa.library.users.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
  private Integer id;
  private String username;
  private String accessLevel;

  public UserDto(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.accessLevel = user.getAccessLevel().getName();
  }
}


