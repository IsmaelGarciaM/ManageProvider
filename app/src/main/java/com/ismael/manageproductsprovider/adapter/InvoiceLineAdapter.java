package com.ismael.manageproductsprovider.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.ismael.manageproductsprovider.R;
import com.ismael.manageproductsprovider.model.Invoice;
import com.ismael.manageproductsprovider.model.InvoiceLine;

/**
 * Created by ismael on 26/02/17.
 */

public class InvoiceLineAdapter extends CursorAdapter {

    public InvoiceLineAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View rootV = inflater.inflate(R.layout.item_invoice_line, parent, false);
        InvoiceLineHolder ilHolder = new InvoiceLineHolder();
        ilHolder.txvNameProduct = (TextView) rootV.findViewById(R.id.txv_invoice_line_product);
        ilHolder.txvAmount = (TextView) rootV.findViewById(R.id.txvAmountProductLine);
        ilHolder.txvPrice = (TextView) rootV.findViewById(R.id.txtPriceProductLine);
        rootV.setTag(ilHolder);
        return rootV;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        InvoiceLineHolder holder = (InvoiceLineHolder) view.getTag();
        holder.txvNameProduct.setText(cursor.getString(3));
        holder.txvAmount.setText(String.valueOf(cursor.getInt(4)));
        holder.txvPrice.setText(String.valueOf(cursor.getDouble(5)));
    }

    @Override
    public Object getItem(int position) {
        getCursor().moveToPosition(position);
        InvoiceLine iLine = new InvoiceLine(getCursor().getInt(1), getCursor().getInt(2), getCursor().getString(3), getCursor().getInt(4), getCursor().getDouble(5));
        iLine.setId(getCursor().getInt(0));
        return iLine;
    }

    class InvoiceLineHolder{
        TextView txvNameProduct;
        TextView txvAmount;
        TextView txvPrice;
    }
}
