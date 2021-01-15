package com.turubarov.dadataproxy.converters;

import com.turubarov.dadataproxy.dadataclient.domain.DadataResponse;
import com.turubarov.dadataproxy.dadataclient.domain.Data;
import com.turubarov.dadataproxy.dadataclient.domain.Suggestion;
import com.turubarov.dadataproxy.domain.Address;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DadataJsonConverter {
    public static List<Address> convert (DadataResponse responseObject) {
        return responseObject.getSuggestions().stream().map(s -> dataToAdress(s))
                .collect(Collectors.toList());
    }

    private static Address dataToAdress(Suggestion suggestion) {
        Address address = new Address();
        Data data = suggestion.getData();
        address.setValue(suggestion.getValue());
        address.setPostalCode(data.getPostal_code());
        address.setRegion(data.getRegion());
        address.setCity(data.getCity());
        address.setSettlement(data.getSettlement());
        address.setStreet(data.getStreet());
        address.setHouse(data.getHouse());
        return address;
    }
}
