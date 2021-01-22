package com.turubarov.dadataproxy.repository;

import com.turubarov.dadataproxy.domain.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Integer> {

    @Override
    public List<Address> findAll();
    public Address findByValue(String value);
    public List<Address> findByRegion(String region);
    public List<Address> findByCity(String city);
    public List<Address> findBySettlement(String settlement);
    public List<Address> findByStreet(String street);
}
