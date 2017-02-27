package com.ismael.manageproductsprovider.provider;

/*
 * Copyright (c) 2017 José Luis del Pino Gallardo.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  jose.gallardo994@gmail.com
 */

import android.net.Uri;
import android.provider.BaseColumns;

import com.ismael.manageproductsprovider.db.DatabaseContract;

/**
 * Created by usuario on 20/01/17.
 */

public final class ManageProductContract {

    public static final String AUTHORITY = "com.ismael.manageproductsprovider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    private ManageProductContract(){

    }

    public static class Category implements BaseColumns{
        public static final String CONTENT_PATH = "category";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        private static final String NAME="name";
        public static final String[] PROJECTION = {BaseColumns._ID, NAME};
    }

    public static class Product implements BaseColumns{
        public static final String CONTENT_PATH="product";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String NAME="name";
        public static final String DESCRIPTION="description";
        public static final String BRAND="brand";
        public static final String DOSAGE="dosage";
        public static final String PRICE="price";
        public static final String STOCK="stock";
        public static final String IMAGE="image";
        public static final String CATEGORY_ID="idCategory";

        public static final String[] PROJECTION = {BaseColumns._ID, NAME, DESCRIPTION, BRAND, DOSAGE, PRICE, STOCK, IMAGE, CATEGORY_ID};
        }

    public static class Invoice implements BaseColumns {
        public static final String CONTENT_PATH="invoice";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String PHARMACY_ID="idPharmacy";
        public static final String DATE="date";
        public static final String STATUS="status";
        public static final String[] PROJECTION = {
                "i."+DatabaseContract.InvoiceEntry._ID,
                DatabaseContract.InvoiceEntry.COLUMN_IDPHARMACY,
                DatabaseContract.PharmacyEntry.COLUMN_CIF,
                "i." + DatabaseContract.InvoiceStatusEntry.COLUMN_NAME,
                DATE,
                "s." + InvoiceStatus.NAME,
                "s." + InvoiceStatus._ID
        };
    }

    public static class InvoiceLine implements BaseColumns {
        public static final String CONTENT_PATH="invoiceline";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String INVOICE_ID="idInvoice";
        public static final String NAMEPRODUCT="nameProduct";
        public static final String PRODUCT_ID="idProduct";
        public static final String AMOUNT="amount";
        public static final String PRICE="price";
        public static final String[] PROJECTION = {BaseColumns._ID, INVOICE_ID, PRODUCT_ID, NAMEPRODUCT, AMOUNT, PRICE};
    }

    public static class InvoiceStatus implements BaseColumns {
        public static final String CONTENT_PATH="invoicestatus";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String NAME="name";
    }

    public static class Pharmacy implements BaseColumns {
        public static final String CONTENT_PATH="pharmacy";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String CIF="cif";
        public static final String ADDRESS="address";
        public static final String PHONE="phone";
        public static final String[] PROJECTION = {BaseColumns._ID, CIF, ADDRESS, PHONE};
    }


}
