package com.turubarov.dadataproxy.contoller;

import com.turubarov.dadataproxy.domain.Address;
import com.turubarov.dadataproxy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dadata")

public class DadataController {

    @Autowired
    private AddressService addressService;

    @GetMapping(value = "suggest")
    public List<Address> suggest(@RequestParam("query") String query) throws Exception {
        return addressService.processRequest(query);
    }

    @GetMapping(value = "search")
    public List<Address> search(@RequestParam("type") String type,
                                @RequestParam("query") String query) throws Exception {
        return addressService.processSearch(type, query);
    }

    @ExceptionHandler(Exception.class)
    public String handleCustomException(Exception ex) {
        return ex.getMessage();
    }
}