package com.ismael.manageproductsprovider.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.ismael.manageproductsprovider.R;
import com.ismael.manageproductsprovider.model.Category;

/**
 * Created by ismael on 11/02/17.
 */

public class CategoriesAdapter extends CursorAdapter {


    public CategoriesAdapter(Context context, Cursor c, int flags) {

        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(context);

        View rootV = inflater.inflate(R.layout.category_item, parent, false);

        CategoryHolder ch = new CategoryHolder();
        ch.txvNameCat = (TextView) rootV.findViewById(R.id.catName);
        rootV.setTag(ch);
        return rootV;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CategoryHolder ch = (CategoryHolder) view.getTag();
        ch.txvNameCat.setText(cursor.getString(1));
    }

    @Override
    public Object getItem(int position) {
        getCursor().moveToPosition(position);
        Category cat = new Category(getCursor().getInt(0), getCursor().getString(1));
        return  cat;
    }

    class CategoryHolder{
        TextView txvNameCat;
    }
}
