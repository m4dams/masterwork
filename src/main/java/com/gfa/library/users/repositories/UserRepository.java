package com.gfa.library.users.repositories;

import com.gfa.library.users.models.dtos.RegistrationDto;
import com.gfa.library.users.models.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

  Optional<User> findFirstByUsername(String userName);

  Optional<User> findFirstByEmail(String email);

  List<User> findAll();

}
