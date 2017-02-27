package com.ismael.manageproductsprovider.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ismael.manageproductsprovider.R;
import com.ismael.manageproductsprovider.model.Product;
import com.squareup.picasso.Picasso;


public class ProductAdapter extends CursorAdapter{

    public ProductAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

            //LayoutInflater layoutInflater = (LayoutInflater.from(context));
            LayoutInflater inflater = LayoutInflater.from(context);
            View rV = inflater.inflate(R.layout.item_list_product, parent, false);


            ProductHolder productHolder = new ProductHolder();
            productHolder.imgProduct = (ImageView)rV.findViewById(R.id.imgProduct);
            productHolder.txvProductName = (TextView)rV.findViewById(R.id.txvProductName);
            productHolder.txvProductPrice = (TextView)rV.findViewById(R.id.txvProductPrice);
            productHolder.txvProductStock = (TextView)rV.findViewById(R.id.txvProductStock);

            rV.setTag(productHolder);
        return rV;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ProductHolder productHolder = (ProductHolder)view.getTag();

        Picasso.with(context).load(cursor.getString(7)).into(productHolder.imgProduct);
        productHolder.txvProductName.setText(cursor.getString(1));

        productHolder.txvProductPrice.setText((String.valueOf(cursor.getFloat(5))));
        productHolder.txvProductStock.setText((String.valueOf(cursor.getInt(6))));
    }

    @Override
    public Object getItem(int position) {

        getCursor().moveToPosition(position);
        Product p = new Product();

        p.setID(getCursor().getInt(0));
        p.setName(getCursor().getString(1));
        p.setDescription(getCursor().getString(2));
        p.setBrand(getCursor().getString(3));
        p.setDosage(getCursor().getString(4));
        p.setPrice(getCursor().getDouble(5));
        p.setStock(getCursor().getString(6));
        p.setImage(getCursor().getString(7));
        p.setIdCategory(getCursor().getInt(8));

        return  p;
    }

    class ProductHolder {
        ImageView imgProduct;
        TextView txvProductName, txvProductPrice, txvProductStock;
    }
}
