package com.ismael.manageproductsprovider.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.ismael.manageproductsprovider.R;
import com.ismael.manageproductsprovider.db.DatabaseContract;
import com.ismael.manageproductsprovider.db.DatabaseHelper;

/**
 * Created by usuario on 6/02/17.
 */

public class ManageProductsContentProvider extends ContentProvider {

    private static final int PRODUCT = 1;
    private static final int PRODUCT_ID = 2;
    private static final int CATEGORY = 3;
    private static final int CATEGORY_ID = 4;
    private static final int INVOICESTATUS = 5;
    private static final int INVOICESTATUS_ID = 6;
    private static final int PHARMACY = 7;
    private static final int PHARMACY_ID = 8;
    private static final int INVOICELINE = 9;
    private static final int INVOICELINE_ID = 10;
    private static final int INVOICE = 11;
    private static final int INVOICE_ID = 12;
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        matcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.Product.CONTENT_PATH, PRODUCT);
        matcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.Product.CONTENT_PATH + "/#", PRODUCT_ID);
        matcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.Category.CONTENT_PATH, CATEGORY);
        matcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.Category.CONTENT_PATH + "/#", CATEGORY_ID);
        matcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.InvoiceStatus.CONTENT_PATH, INVOICESTATUS);
        matcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.InvoiceStatus.CONTENT_PATH + "/#", INVOICESTATUS_ID);
        matcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.Pharmacy.CONTENT_PATH, PHARMACY);
        matcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.Pharmacy.CONTENT_PATH + "/#", PHARMACY_ID);
        matcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.InvoiceLine.CONTENT_PATH, INVOICELINE);
        matcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.InvoiceLine.CONTENT_PATH + "/#", INVOICELINE_ID);
        matcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.Invoice.CONTENT_PATH, INVOICE);
        matcher.addURI(ManageProductContract.AUTHORITY, ManageProductContract.Invoice.CONTENT_PATH + "/#", INVOICE_ID);
    }

    private SQLiteDatabase _database;

    @Override
    public boolean onCreate() {
        _database = DatabaseHelper.getInstance().openDatabase();
        return true; //Si se ha realizado correctamente
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch (matcher.match(uri)){
            case CATEGORY:
                builder.setTables(DatabaseContract.CategoryEntry.TABLE_NAME);

                if(!TextUtils.isEmpty(sortOrder))
                    sortOrder = DatabaseContract.CategoryEntry.DEFAULT_SORT;
                break;
            case CATEGORY_ID:
                break;
            case PRODUCT:
                builder.setTables(DatabaseContract.ProductEntry.TABLE_NAME);
                break;
            case PRODUCT_ID:
                break;
            case PHARMACY:
                builder.setTables(DatabaseContract.PharmacyEntry.TABLE_NAME);
                break;
            case INVOICE:
                builder.setTables(DatabaseContract.InvoiceEntry.TABLE_NAME + DatabaseContract.InvoiceEntry.INVOICE_JOIN);
                break;
            case INVOICELINE:
                builder.setTables(DatabaseContract.InvoiceLineEntry.TABLE_NAME);

        }

        String sqlQuery = builder.buildQuery(projection, selection, null, null, sortOrder, null);
        Log.wtf("CONSULTA", sqlQuery);
        Cursor cursor = builder.query(_database, projection, selection, selectionArgs, null,null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        String res = "";
        switch (matcher.match(uri)) {
            case CATEGORY:
                res = "vnd.android.cursor.dir/vnd.com.ismael.manageproductsprovider.category";
                break;
            case CATEGORY_ID:
                res = "vnd.android.cursor.item/vnd.com.ismael.manageproductsprovider.category";
                break;

            case PRODUCT:
                res = "vnd.android.cursor.dir/vnd.com.ismael.manageproductsprovider.product";
                break;

            case PRODUCT_ID:
                res = "vnd.android.cursor.item/vnd.com.ismael.manageproductsprovider.product";
                break;

            case PHARMACY:
                res = "vnd.android.cursor.dir/vnd.com.ismael.manageproductsprovider.pharmacy";
                break;

            case PHARMACY_ID:
                res = "vnd.android.cursor.item/vnd.com.ismael.manageproductsprovider.pharmacy";
                break;

            case INVOICELINE:
                res = "vnd.android.cursor.dir/vnd.com.ismael.manageproductsprovider.invoiceline";
                break;

            case INVOICELINE_ID:
                res = "vnd.android.cursor.item/vnd.com.ismael.manageproductsprovider.invoiceline";
                break;
        }

        return res;

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        Uri u = null;
        long regId = -1;
        switch (matcher.match(uri)) {
            case CATEGORY:
                regId = _database.insert(DatabaseContract.CategoryEntry.TABLE_NAME, null, contentValues);
                u = ContentUris.withAppendedId(uri, regId);
                break;

            case PRODUCT:
                regId = _database.insert(DatabaseContract.ProductEntry.TABLE_NAME, null, contentValues);
                u = ContentUris.withAppendedId(uri, regId);
                break;

            case PHARMACY:
                regId = _database.insert(DatabaseContract.PharmacyEntry.TABLE_NAME, null, contentValues);
                u = ContentUris.withAppendedId(uri, regId);
                break;

            case INVOICE:
                regId = _database.insert(DatabaseContract.InvoiceEntry.TABLE_NAME, null, contentValues);
                u = ContentUris.withAppendedId(uri, regId);
                break;

            case INVOICELINE:
                regId = _database.insert(DatabaseContract.InvoiceLineEntry.TABLE_NAME, null, contentValues);
                u = ContentUris.withAppendedId(uri, regId);
                break;

            default: u = null;
        }

        if(regId != -1){
            getContext().getContentResolver().notifyChange(u, null);
        }
        else
            throw new SQLException(getContext().getResources().getString(R.string.errorInsert));
        return u;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int affected = -1;
        String rowId;
        switch (matcher.match(uri)) {
            case CATEGORY:
                affected = _database.delete(DatabaseContract.CategoryEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case CATEGORY_ID:
                //rowId = uri.getLastPathSegment();
                rowId = uri.getPathSegments().get(1);             //0       1
                //Content://com.barranquero.manageproductprovider/category/id
                selection = DatabaseContract.CategoryEntry._ID+"="+rowId;
                affected = _database.delete(DatabaseContract.CategoryEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case PRODUCT:
                affected = _database.delete(DatabaseContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case PRODUCT_ID:
                //rowId = uri.getLastPathSegment();
                rowId = uri.getPathSegments().get(1);             //0       1
                //Content://com.barranquero.manageproductprovider/category/id
                selection = DatabaseContract.ProductEntry._ID+"="+rowId;
                affected = _database.delete(DatabaseContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case INVOICE_ID:
                //rowId = uri.getLastPathSegment();
                rowId = uri.getPathSegments().get(1);             //0       1
                //Content://com.barranquero.manageproductprovider/category/id
                selection = DatabaseContract.InvoiceEntry._ID+"="+rowId;
                affected = _database.delete(DatabaseContract.InvoiceEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case INVOICELINE_ID:
                //rowId = uri.getLastPathSegment();
                rowId = uri.getPathSegments().get(1);             //0       1
                //Content://com.barranquero.manageproductprovider/category/id
                selection = DatabaseContract.InvoiceLineEntry._ID+"="+rowId;
                affected = _database.delete(DatabaseContract.InvoiceLineEntry.TABLE_NAME, selection, selectionArgs);
                break;
        }
        if(affected != -1){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        else
            throw new SQLException(getContext().getResources().getString(R.string.errorDelete));
        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        int affected = -1;
        String rowId;
        switch (matcher.match(uri)) {
            case CATEGORY:
                affected = _database.update(DatabaseContract.CategoryEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case CATEGORY_ID:
                //rowId = uri.getLastPathSegment();
                rowId = uri.getPathSegments().get(1);             //0       1
                //Content://com.barranquero.manageproductprovider/category/id
                selection = DatabaseContract.CategoryEntry._ID+"="+rowId;
                affected = _database.update(DatabaseContract.CategoryEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case PRODUCT:
                affected = _database.update(DatabaseContract.ProductEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case PRODUCT_ID:
                //rowId = uri.getLastPathSegment();
                rowId = uri.getPathSegments().get(1);             //0       1
                //Content://com.barranquero.manageproductprovider/category/id
                selection = DatabaseContract.ProductEntry._ID+"="+rowId;
                affected = _database.update(DatabaseContract.ProductEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case INVOICE:
                affected = _database.update(DatabaseContract.InvoiceEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case INVOICE_ID:
                //rowId = uri.getLastPathSegment();
                rowId = uri.getPathSegments().get(1);             //0       1
                //Content://com.barranquero.manageproductprovider/category/id
                selection = DatabaseContract.InvoiceEntry._ID+"="+rowId;
                affected = _database.update(DatabaseContract.InvoiceEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case INVOICELINE:
                affected = _database.update(DatabaseContract.InvoiceEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case INVOICELINE_ID:
                //rowId = uri.getLastPathSegment();
                rowId = uri.getPathSegments().get(1);             //0       1
                //Content://com.barranquero.manageproductprovider/category/id
                selection = DatabaseContract.InvoiceLineEntry._ID+"="+rowId;
                affected = _database.update(DatabaseContract.InvoiceLineEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
        }

        if(affected != -1){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        else
            throw new SQLException(getContext().getResources().getString(R.string.errorUpdate));

        return affected;

    }
}
