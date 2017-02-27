package com.ismael.manageproductsprovider.model;


import android.os.Parcel;
import android.os.Parcelable;

public class InvoiceLine implements Parcelable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    int idInvoice;

    protected InvoiceLine(Parcel in) {
        id = in.readInt();
        idInvoice = in.readInt();
        nameProduct = in.readString();
        idProduct = in.readInt();
        amount = in.readInt();
        price = in.readDouble();
    }

    public static final Creator<InvoiceLine> CREATOR = new Creator<InvoiceLine>() {
        @Override
        public InvoiceLine createFromParcel(Parcel in) {
            return new InvoiceLine(in);
        }

        @Override
        public InvoiceLine[] newArray(int size) {
            return new InvoiceLine[size];
        }
    };

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    String nameProduct;
    int idProduct;
    int amount;
    double price;

    public InvoiceLine(int idInvoice, int idProduct, String nameProduct, int amount, double price) {
        this.idInvoice = idInvoice;
        this.nameProduct = nameProduct;
        this.idProduct = idProduct;
        this.amount = amount;
        this.price = price;
    }

    public InvoiceLine(){}

    public int getIdInvoice() {
        return idInvoice;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(idInvoice);
        dest.writeInt(idProduct);
        dest.writeString(nameProduct);
        dest.writeInt(amount);
        dest.writeDouble(price);
    }
}
