package com.gfa.library.users.services;

import com.gfa.library.users.models.dtos.RegistrationDto;
import com.gfa.library.users.models.dtos.RegResponseDto;
import com.gfa.library.users.models.dtos.UserFullDto;
import com.gfa.library.users.models.dtos.UserSearchDto;
import com.gfa.library.users.models.dtos.UsersDto;
import com.gfa.library.users.models.entities.User;

import java.util.List;

public interface UserService {

  RegResponseDto register(RegistrationDto reg);

  User getByUsername(String username);

  UserFullDto searchForUsers(User user, UserSearchDto search);


  void deleteUser(int id, User user);

  UsersDto convert(List<User> users);

  UsersDto viewAllUsers(User user);

  RegResponseDto modifyUser(User user, int id, UserFullDto search);

}
