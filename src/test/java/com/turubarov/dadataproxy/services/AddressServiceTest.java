package com.turubarov.dadataproxy.services;

import com.turubarov.dadataproxy.domain.Address;
import com.turubarov.dadataproxy.repositories.AddressRepository;
import com.turubarov.dadataproxy.servises.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

import java.util.List;

@SpringBootTest
public class AddressServiceTest {

    @Autowired
    AddressService addressService;

    @Autowired
    AddressRepository addressRepository;

    @Test
    public void processRequestTest() {
        String query = "новосибирск";
        List<Address> addresses = addressService.processRequest(query);
        assertTrue(addresses.size() > 0);
        assertNotNull(addressRepository.findByCity(query));
    }

    @Test
    public void processSearchTest() {
        String query = "новосибирск";
        addressService.processRequest(query);
        assertNotNull(addressService.processSearch("city", "новосибирск"));
    }
}
