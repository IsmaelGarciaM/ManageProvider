package com.ismael.manageproductsprovider.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.ismael.manageproductsprovider.R;
import com.ismael.manageproductsprovider.model.Pharmacy;

/**
 * Created by usuario on 30/01/17.
 */

public class PharmacyAdapter extends CursorAdapter {


    public class ChemistHolder{
        TextView txvCif;
        TextView txvTlf;
        TextView txvAddress;
    }


    //El cursor debe ser nulo
    public PharmacyAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View rootV = inflater.inflate(R.layout.item_list_chemist, parent, false);

        ChemistHolder ch = new ChemistHolder();
        ch.txvAddress = (TextView) rootV.findViewById(R.id.txvChemisAddress);
        ch.txvCif = (TextView) rootV.findViewById(R.id.txvChemisCIF);
        rootV.setTag(ch);
        return rootV;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ChemistHolder ch = (ChemistHolder)view.getTag();
        ch.txvCif.setText(cursor.getString(1));
        ch.txvAddress.setText(cursor.getString(2));
    }

    @Override
    public Object getItem(int position) {
        getCursor().moveToPosition(position);
        Pharmacy ph = new Pharmacy(getCursor().getInt(0), getCursor().getString(1), getCursor().getString(2) , getCursor().getString(3));
        return  ph;
    }
}
