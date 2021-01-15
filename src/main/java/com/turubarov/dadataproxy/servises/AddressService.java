package com.turubarov.dadataproxy.servises;

import com.turubarov.dadataproxy.converters.DadataJsonConverter;
import com.turubarov.dadataproxy.dadataclient.DadataClient;
import com.turubarov.dadataproxy.domain.Address;
import com.turubarov.dadataproxy.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public void addAddressesForQuery(String query) {
        addAddresses(DadataJsonConverter.convert(DadataClient.suggestAdress(query)));
    }

    private void addAddresses(List<Address> listAdr) {
        listAdr.stream().forEach(adr -> addAddress(adr));
    }

    private boolean addAddress(Address adr) {
        Address adrFromDb = addressRepository.findByValue(adr.getValue());

        if (adrFromDb != null) {
            return false;
        }
        addressRepository.save(adr);
        return true;
    }
}
