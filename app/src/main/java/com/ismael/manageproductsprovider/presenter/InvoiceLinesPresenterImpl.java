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
import com.ismael.manageproductsprovider.interfaces.IInvoiceLinesPresenter;
import com.ismael.manageproductsprovider.model.Invoice;
import com.ismael.manageproductsprovider.model.InvoiceLine;
import com.ismael.manageproductsprovider.provider.ManageProductContract;

/**
 * Created by ismael on 27/02/17.
 */

public class InvoiceLinesPresenterImpl implements IInvoiceLinesPresenter, LoaderManager.LoaderCallbacks<Cursor> {

    private final static int INVOICELINE=9;
    private IInvoiceLinesPresenter.View view;
    private Context context;

    public InvoiceLinesPresenterImpl(IInvoiceLinesPresenter.View view) {
        this.context = view.getContext();
        this.view = view;
    }

    @Override
    public void getInvoiceLines(CursorAdapter adapter, Invoice invoice) {
        Bundle b = new Bundle();
        b.putParcelable("INVOICE",invoice);
        ((Activity)context).getLoaderManager().initLoader(INVOICELINE, b, this);
    }

    @Override
    public void addLine(InvoiceLine line) {
        ContentValues cTmp = invoiceLineToValues(line);
        context.getContentResolver().insert( ManageProductContract.InvoiceLine.CONTENT_URI , cTmp);
    }

    @Override
    public void deleteLine(int idLine) {

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        Loader ccl;

        switch (id){
            case INVOICELINE:
                String selection = "idInvoice="+String.valueOf(((Invoice)args.getParcelable("INVOICE")).getId());
                ccl = new CursorLoader(context, ManageProductContract.InvoiceLine.CONTENT_URI, ManageProductContract.InvoiceLine.PROJECTION, selection, null, null);
                break;
            default:
                ccl=null;
        }

        return ccl;
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        data.setNotificationUri(context.getContentResolver(), ManageProductContract.InvoiceLine.CONTENT_URI); //Notificame cuando haya un cambio en algo
        view.setCursorLines(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        view.setCursorLines(null);
    }

    private ContentValues invoiceLineToValues(InvoiceLine i){

        ContentValues cv = new ContentValues();

        cv.put(DatabaseContract.InvoiceLineEntry.COLUMN_IDPRODUCT, (i.getIdProduct()));
        cv.put(ManageProductContract.Invoice.PHARMACY_ID, (i.getIdInvoice()));
        cv.put(ManageProductContract.Invoice.DATE, (i.getAmount()));
        cv.put(ManageProductContract.Invoice.STATUS, (i.getPrice()));

        return cv;
    }
}
