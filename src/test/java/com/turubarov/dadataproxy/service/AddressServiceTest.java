package com.turubarov.dadataproxy.service;

import com.turubarov.dadataproxy.dadataclient.SearchTypes;
import com.turubarov.dadataproxy.domain.Address;
import com.turubarov.dadataproxy.repository.AddressRepository;
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
    public void processRequestTest() throws Exception {
        String query = "новосибирск";
        List<Address> addresses = addressService.processRequest(query);
        assertTrue(addresses.size() > 0);
        assertNotNull(addressRepository.findByCity(query));
    }

    @Test
    public void processSearchTest() throws Exception {
        String query = "новосибирск";
        addressService.processRequest(query);
        assertNotNull(addressService.processSearch(SearchTypes.CITY, "новосибирск"));
    }
}
