package com.ismael.manageproductsprovider.model;

/**
 * Created by joselu on 11/01/17.
 */

public class Pharmacy {
    int id;
    String cif;
    String address;
    String phone;

    public Pharmacy(int id, String cif, String address, String phone) {
        this.id = id;
        this.cif = cif;
        this.address = address;
        this.phone = phone;
    }

    public Pharmacy(){}
    public int getId() {
        return id;
    }

    public String getCif() {
        return cif;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
