package com.turubarov.dadataproxy.repositories;

import com.turubarov.dadataproxy.entities.DataValue;
import org.springframework.data.repository.CrudRepository;

public interface DataValueRepository extends CrudRepository<DataValue, Long> {
    @Override
    Iterable<DataValue> findAll();
}