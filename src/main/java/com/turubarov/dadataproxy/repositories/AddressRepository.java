package com.turubarov.dadataproxy.repositories;

import com.turubarov.dadataproxy.domain.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Integer> {
    @Override
    public List<Address> findAll();
    public Address findByValue(String value);
}
