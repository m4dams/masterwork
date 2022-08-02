package com.gfa.library.users.services;

import com.gfa.library.addresses.models.dtos.AddressDto;
import com.gfa.library.addresses.models.entities.Address;
import com.gfa.library.exceptions.AuthorizationException;
import com.gfa.library.exceptions.ConflictException;
import com.gfa.library.exceptions.NotFoundException;
import com.gfa.library.users.models.TypeOfAccess;
import com.gfa.library.users.models.dtos.RegResponseDto;
import com.gfa.library.users.models.dtos.RegistrationDto;
import com.gfa.library.users.models.dtos.UserFullDto;
import com.gfa.library.users.models.dtos.UserSearchDto;
import com.gfa.library.users.models.dtos.UsersDto;
import com.gfa.library.users.models.entities.AccessLevel;
import com.gfa.library.users.models.entities.User;
import com.gfa.library.users.repositories.UserRepository;
import com.gfa.library.utils.PasswordService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordService passwordService;

  public UserServiceImpl(UserRepository userRepository, PasswordService passwordService) {
    this.userRepository = userRepository;
    this.passwordService = passwordService;
  }

  @Override
  public RegResponseDto register(RegistrationDto reg) {
    checkUniques(reg.getUsername(), reg.getEmail());
    User user = initUser(reg);
    userRepository.save(user);
    return new RegResponseDto(user);
  }

  @Override
  public User getByUsername(String username) {
    return userRepository.findFirstByUsername(username)
        .orElseThrow(() -> new NotFoundException("User"));
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    return getByUsername(username);
  }

  @Override
  public UserFullDto searchForUsers(User user, UserSearchDto search) {
    if (user.getAccessLevel().getName().equals("USER")) {
      throw new AuthorizationException();
    }
    return searchWithCriteria(search);

  }

  @Override
  public void deleteUser(int id, User user) {
    if (!user.isAdmin()) {
      throw new AuthorizationException("Unauthorized User!");
    }
    User toDelete = getById(id);
    userRepository.delete(toDelete);
  }

  @Override
  public UsersDto convert(List<User> users) {
    return new UsersDto(users.stream()
        .map(UserFullDto::new).collect(Collectors.toList()));
  }

  @Override
  public UsersDto viewAllUsers(User user) {
    if (!user.getAccessLevel().getName().equals("ADMIN")) {
      throw new AuthorizationException();
    }
    return convert(userRepository.findAll());
  }

  @Override
  public RegResponseDto modifyUser(User user, int id, UserFullDto change) {
    if (user.getId() != id && !user.isAdmin()) {
      throw new AuthorizationException();
    }
    checkUniques(change.getUsername(), change.getEmail());
    User toModify = getById(id);
    toModify = changeUserDetails(user, toModify, change);
    return new RegResponseDto(toModify);
  }

 private void checkUniques(String username, String email) {
    if (userRepository.findFirstByUsername(username).isPresent()) {
      throw new ConflictException("Username already taken");
    }
    if (userRepository.findFirstByEmail(email).isPresent()) {
      throw new ConflictException("Email already taken");
    }
  }

  private User changeUserDetails(User user, User toModify, UserFullDto change) {
    toModify.setUsername(change.getUsername() == null ? toModify.getUsername() : change.getUsername());
    toModify.setEmail(change.getEmail() == null ? toModify.getEmail() : change.getEmail());
    toModify.setFirstName(change.getFirstName() == null ? toModify.getFirstName() : change.getFirstName());
    toModify.setLastName(change.getLastName() == null ? toModify.getLastName() : change.getLastName());
    toModify.setDateOfBirth(change.getDateOfBirth() == null ? toModify.getDateOfBirth() : change.getDateOfBirth());
    toModify.setAddress(updateAddress(change.getAddress(), toModify.getAddress()));
    if (user.isAdmin()) {
      AccessLevel accessLevel = new AccessLevel(Stream.of(TypeOfAccess.values())
          .filter(e -> e.toString().equals(change.getAccessLevel()))
          .findFirst().orElseThrow(() -> new NotFoundException("Access level")));
      toModify.setAccessLevel(toModify.getAccessLevel() == null ? toModify.getAccessLevel() : accessLevel);
    }
    return userRepository.save(toModify);
  }

  private Address updateAddress(AddressDto address, Address address1) {
    if (address == null) {
      return address1;
    }
    address1.setCounty(address.getCounty() == null ? address1.getCounty() : address.getCounty());
    address1.setCity(address.getCity() == null ? address1.getCity() : address.getCity());
    address1.setPostalCode(address.getPostalCode() == null ? address1.getPostalCode() : address.getPostalCode());
    address1.setStreetAndBuilding(address.getStreetAndBuilding() == null
        ? address1.getStreetAndBuilding() : address.getStreetAndBuilding());
    return address1;
  }

  private UserFullDto searchWithCriteria(UserSearchDto search) {
    if (search.getId() != null) {
      return new UserFullDto(getById(search.getId()));
    }
    if (search.getUsername() != null) {
      return new UserFullDto(getByUsername(search.getUsername()));
    }
    if (search.getEmail() != null) {
      return new UserFullDto(getByEmail(search.getEmail()));
    }
    throw new NotFoundException("Search criteria");
  }

  private User getByEmail(String email) {
    return userRepository.findFirstByEmail(email)
        .orElseThrow(() -> new NotFoundException("User"));
  }

  private User getById(Integer id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("User"));
  }

  private User initUser(RegistrationDto reg) {
    User user = new User();
    user.setUsername(reg.getUsername());
    user.setPassword(passwordService.hashPassword(reg.getPassword()));
    user.setEmail(reg.getEmail());
    user.setFirstName(reg.getFirstName());
    user.setLastName(reg.getLastName());
    user.setDateOfBirth(reg.getDateOfBirth());
    user.setAddress(new Address(reg.getAddress()));
    user.setAccessLevel(new AccessLevel(TypeOfAccess.USER));
    user.getAddress().getUsers().add(user);
    user.getAccessLevel().getUsers().add(user);

    return user;
  }


}
