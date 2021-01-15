package com.turubarov.dadataproxy.controllers;

import com.turubarov.dadataproxy.domain.Address;
import com.turubarov.dadataproxy.servises.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("dadata")
public class DadataController {

    @Autowired
    private AddressService addressService;

    @GetMapping(value = "suggest")
    public List<Address> suggest(@RequestParam("query") String query) {
        List<Address> addresses = addressService.processRequest(query);
        return addresses;
    }

    @GetMapping(value = "search")
    public List<Address> search(@RequestParam("type") String type,
                          @RequestParam("query") String query) {
        return addressService.processSearch(type, query);
    }
}
