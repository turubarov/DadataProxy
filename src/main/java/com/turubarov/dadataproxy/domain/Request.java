package com.turubarov.dadataproxy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "request")
public class Request {

    public Request() {
        this.addresses = new ArrayList<>();
        this.countUse = 1;
    }

    public Request(String query, Date timeOfQuery) {
        this.query = query;
        this.timeOfQuery = timeOfQuery;
        this.countUse = 0;
        this.addresses = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "query")
    private String query;

    @Column(name = "time_of_query")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOfQuery;

    @Column(name = "count_use")
    private int countUse;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "address_and_request",
            joinColumns = @JoinColumn(name = "id_request"),
            inverseJoinColumns = @JoinColumn(name = "id_address"))
    private List<Address> addresses;

    public void AddAddress(Address address) {
        this.addresses.add(address);
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Date getTimeOfQuery() {
        return timeOfQuery;
    }

    public void setTimeOfQuery(Date timeOfQuery) {
        this.timeOfQuery = timeOfQuery;
    }

    public int getCountUse() {
        return countUse;
    }

    public void incCountUse() {
        countUse++;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public boolean isNew() {
        return countUse == 1;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
