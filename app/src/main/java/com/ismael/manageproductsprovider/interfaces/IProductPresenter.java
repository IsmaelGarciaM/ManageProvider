package com.ismael.manageproductsprovider.interfaces;

import android.content.Context;
import android.database.Cursor;
import android.widget.CursorAdapter;

import com.ismael.manageproductsprovider.model.Product;


/**
 * Created by usuario on 9/12/16.
 */

public interface IProductPresenter {

    void getAllProducts(CursorAdapter adapter);
    void addProduct(Product p);
    boolean deleteProduct(int product);
    void updateProduct(int idOldP, Product newP);

    interface View{
        void setProductCursor(Cursor cursor);
        Context getContext();
    }
}
