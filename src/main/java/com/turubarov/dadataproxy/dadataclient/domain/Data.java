package com.turubarov.dadataproxy.dadataclient.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

    @JsonProperty("postal_code")
    private String postalCode;
    private String region;
    private String city;
    private String settlement;
    private String street;
    private String house;

    public String getPostalCode() {
        return postalCode;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public String getSettlement() {
        return settlement;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }
}
