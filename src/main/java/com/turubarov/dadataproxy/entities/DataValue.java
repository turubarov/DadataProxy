package com.turubarov.dadataproxy.entities;

import javax.persistence.*;

@Entity
@Table(name = "data_value")
public class DataValue {
    public DataValue() {
        id = 0;
        value = 0;
    }

    public DataValue(long value) {
        this.value = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "value")
    private long value;


    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}

