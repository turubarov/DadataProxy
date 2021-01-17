package com.turubarov.dadataproxy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "suggest")
    public String suggest(@RequestParam("query") String query) {
        try {
            return objectMapper.writeValueAsString(
                    addressService.processRequest(query));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "search")
    public String search(@RequestParam("type") String type,
                          @RequestParam("query") String query) {
        try {
            return objectMapper.writeValueAsString(
                    addressService.processSearch(type, query));
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
