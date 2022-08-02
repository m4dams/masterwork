package com.gfa.library.users.controllers;

import com.gfa.library.users.models.dtos.RegResponseDto;
import com.gfa.library.users.models.dtos.RegistrationDto;
import com.gfa.library.users.models.dtos.UserFullDto;
import com.gfa.library.users.models.dtos.UserSearchDto;
import com.gfa.library.users.models.dtos.UsersDto;
import com.gfa.library.users.models.entities.User;
import com.gfa.library.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping(value = {"/register"}, consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RegResponseDto> register(@Valid @RequestBody RegistrationDto reg) {
    RegResponseDto regResponseDto = userService.register(reg);
    return ResponseEntity.status(201).body(regResponseDto);
  }

  @GetMapping("/users")
  public ResponseEntity<UsersDto> getAllUsers(Authentication auth) {
    User user = (User) auth.getPrincipal();
    UsersDto response = userService.viewAllUsers(user);
    return ResponseEntity.status(200).body(response);
  }

  @GetMapping("/users/")
  public ResponseEntity<UserFullDto> searchForUserWithCriteria(UserSearchDto search,
                                                               Authentication auth) {
    User user = (User) auth.getPrincipal();
    UserFullDto response = userService.searchForUsers(user, search);
    return ResponseEntity.status(200).body(response);
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<RegResponseDto> modifyUser(@PathVariable int id,
                                                   @RequestBody UserFullDto userFullDto,
                                                   Authentication auth) {
    User user = (User) auth.getPrincipal();
    RegResponseDto response = userService.modifyUser(user, id, userFullDto);
    return ResponseEntity.status(200).body(response);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable int id, Authentication auth) {
    User user = (User) auth.getPrincipal();
    String response = "User with id: " + id + " was deleted successfully";
    userService.deleteUser(id, user);
    return ResponseEntity.status(200).body(response);
  }


}
