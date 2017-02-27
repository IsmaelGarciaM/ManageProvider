package com.ismael.manageproductsprovider.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joselu on 11/01/17.
 */

public class Invoice implements Parcelable{
    public void setName(String name) {
        this.name = name;
    }

    public void setIdPharmacy(int idPharmacy) {
        this.idPharmacy = idPharmacy;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setId(int id) {
        this.id = id;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    int id;
    String name;
    int idPharmacy;
    String date;
    int status;

    public Invoice(){

    }

    public Invoice(int id, String name, int idPharmacy, String date, int status) {
        this.id = id;
        this.name = name;
        this.idPharmacy = idPharmacy;
        this.date = date;
        this.status = status;

    }

    protected Invoice(Parcel in) {
        id = in.readInt();
        name = in.readString();
        idPharmacy = in.readInt();
        date = in.readString();
        status = in.readInt();
    }

    public static final Creator<Invoice> CREATOR = new Creator<Invoice>() {
        @Override
        public Invoice createFromParcel(Parcel in) {
            return new Invoice(in);
        }

        @Override
        public Invoice[] newArray(int size) {
            return new Invoice[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDirPharmacy() {
        return idPharmacy;
    }

    public String getDate() {
        return date;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return  hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(idPharmacy);
        dest.writeString(date);
        dest.writeInt(status);
    }
}
