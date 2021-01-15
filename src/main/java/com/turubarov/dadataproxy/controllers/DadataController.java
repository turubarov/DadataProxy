package com.turubarov.dadataproxy.controllers;

import com.turubarov.dadataproxy.domain.Address;
import com.turubarov.dadataproxy.servises.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("dadata")
public class DadataController {

    @Autowired
    private AddressService addressService;

    @GetMapping(value = "suggest")
    public List<Address> suggest(@RequestParam("query") String query) {
        return addressService.processRequest(query);
    }
}
