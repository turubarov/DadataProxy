package com.turubarov.dadataproxy.repositories;

import com.turubarov.dadataproxy.domain.Request;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, Integer> {
    public Request findByQuery(String query);
}
