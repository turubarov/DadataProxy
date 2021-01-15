package com.turubarov.dadataproxy.repositories;

import com.turubarov.dadataproxy.domain.Request;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface RequestRepository extends CrudRepository<Request, Integer> {
    public Request findByQuery(String query);

    @Modifying
    @Transactional
    @Query("DELETE FROM Request r WHERE r.timeOfQuery < :threshold AND r.countUse < 3")
    public void deleteOldRequests(@Param("threshold") Date threshold);
}
