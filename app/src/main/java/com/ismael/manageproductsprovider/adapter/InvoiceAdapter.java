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

public class InvoiceAdapter extends CursorAdapter {
    private Context contexto;
    private boolean ASC = true;

    public InvoiceAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.contexto = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater lf = LayoutInflater.from(context);

        View rootView = lf.inflate(R.layout.item_invoice, viewGroup, false);

        InvoiceAdapter.InvoiceHolder ph = new InvoiceAdapter.InvoiceHolder();

        ph.status = (TextView) rootView.findViewById(R.id.tv_invoice_status);
        ph.name = (TextView) rootView.findViewById(R.id.tv_invoice_name);
        ph.pharmacy = (TextView) rootView.findViewById(R.id.tv_invoice_pharmacy);
        ph.date = (TextView) rootView.findViewById(R.id.tv_invoice_date);

        rootView.setTag(ph);

        return rootView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        InvoiceAdapter.InvoiceHolder p = (InvoiceAdapter.InvoiceHolder) view.getTag();

        String status = cursor.getString(5);
        p.status.setText(status);

        switch (status) {
            case "Activo":
                p.status.setTextColor(context.getResources().getColor(R.color.activo));
                break;
            case "Entregado":
                p.status.setTextColor(context.getResources().getColor(R.color.entregado));
                break;
            case "Error":
                p.status.setTextColor(context.getResources().getColor(R.color.colorError));
                break;
        }

        p.name.setText(cursor.getString(3));
        p.pharmacy.setText(cursor.getString(2));
        p.date.setText(cursor.getString(4));
    }

    @Override
    public Object getItem(int position) {

        getCursor().moveToPosition(position);
        Invoice i = new Invoice();
        i.setId(getCursor().getInt(0));
        i.setName(getCursor().getString(3));
        i.setIdPharmacy(getCursor().getInt(1));
        i.setDate(getCursor().getString(4));
        if(getCursor().getString(5) == "Activo")
            i.setStatus(1);
        else
            i.setStatus(0);
        return i;

    }

    class InvoiceHolder {
        TextView status;
        TextView name;
        TextView pharmacy;
        TextView date;
    }
}
