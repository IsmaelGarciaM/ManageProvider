package com.ismael.manageproductsprovider.model;

/**
 * Created by joselu on 11/01/17.
 */

public class InvoiceStatus {
    int id;
    int idInvoice;
    String name;

    public InvoiceStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
