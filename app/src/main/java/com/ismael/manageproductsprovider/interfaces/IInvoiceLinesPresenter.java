package com.ismael.manageproductsprovider.interfaces;

import android.content.Context;
import android.database.Cursor;
import android.widget.CursorAdapter;

import com.ismael.manageproductsprovider.model.Invoice;
import com.ismael.manageproductsprovider.model.InvoiceLine;
import com.ismael.manageproductsprovider.provider.ManageProductContract;

/**
 * Created by ismael on 27/02/17.
 */

public interface IInvoiceLinesPresenter {

    void getInvoiceLines(CursorAdapter adapter, Invoice invoice);
    void addLine(InvoiceLine line);
    void deleteLine(int idLine);

    interface View{
        void setCursorLines(Cursor cursor);
        Context getContext();
    }
}
