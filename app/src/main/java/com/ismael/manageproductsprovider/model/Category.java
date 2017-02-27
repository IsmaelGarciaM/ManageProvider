package com.ismael.manageproductsprovider.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joselu on 11/01/17.
 */

public class Category implements Parcelable {
    int id;
    String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Category(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
