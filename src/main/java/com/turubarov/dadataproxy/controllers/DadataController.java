package com.turubarov.dadataproxy.controllers;

import com.turubarov.dadataproxy.entities.DataValue;
import com.turubarov.dadataproxy.repositories.DataValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("dadata")
public class DadataController {

    @Autowired
    private DataValueRepository dataValueRepository;

    @GetMapping
    public Iterable<DataValue> test() {
        Iterable<DataValue> list = dataValueRepository.findAll();
        return list;
    }
}
