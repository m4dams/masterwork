package com.gfa.library.addresses.repositories;

import com.gfa.library.addresses.models.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Integer> {
}
