package com.ismael.manageproductsprovider.presenter;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CursorAdapter;

import com.ismael.manageproductsprovider.db.DatabaseContract;
import com.ismael.manageproductsprovider.interfaces.IInvoicePresenter;
import com.ismael.manageproductsprovider.model.Invoice;
import com.ismael.manageproductsprovider.provider.ManageProductContract;
/**
 * Created by joselu on 13/02/17.
 */

public class InvoicePresenterImpl implements IInvoicePresenter, LoaderManager.LoaderCallbacks<Cursor> {
    private final static int INVOICE = 5;
    IInvoicePresenter.View view;
    Context context;

    public InvoicePresenterImpl(IInvoicePresenter.View vista, Context context) {
        this.view = vista;
        this.context = context;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Loader ccl;

        switch (i) {
            case INVOICE:
                ccl = new CursorLoader(context, ManageProductContract.Invoice.CONTENT_URI, ManageProductContract.Invoice.PROJECTION, null, null, null);
                //ccl = new CursorLoader(context, ProviderContract.Invoice.CONTENT_URI, ProviderContract.Invoice.PROJECTION, DatabaseContract.InvoiceEntry.IN_PHARMACY_JOIN_PHARMACY, null, null, null);
                break;
            default:
                ccl = null;
        }

        return ccl;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.setNotificationUri(context.getContentResolver(), ManageProductContract.Invoice.CONTENT_URI); //Notificame cuando haya un cambio en algo
        view.setInvoiceCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        view.setInvoiceCursor(null);
    }

    @Override
    public void getInvoices(CursorAdapter adapter) {
        ((Activity) context).getLoaderManager().initLoader(INVOICE, null, this); //Inicializamos el cargador dentro del presentador.
    }

    @Override
    public void addInvoice(Invoice invoice) {
        ContentValues iTmp = invoiceToValues(invoice);
        context.getContentResolver().insert(ManageProductContract.Invoice.CONTENT_URI, iTmp);
    }

    @Override
    public void updateInvoice(Invoice oldINvoice, Invoice newInvoice) {

    }

    @Override
    public void deleteInvoice(Invoice invoice) {

    }

    private ContentValues invoiceToValues(Invoice i){

        ContentValues cv = new ContentValues();

        cv.put(DatabaseContract.InvoiceEntry.COLUMN_NAME, (i.getName()));
        cv.put(ManageProductContract.Invoice.PHARMACY_ID, (i.getDirPharmacy()));
        cv.put(ManageProductContract.Invoice.DATE, (i.getDate()));
        cv.put(ManageProductContract.Invoice.STATUS, (i.getStatus()));

        return cv;
    }
}
