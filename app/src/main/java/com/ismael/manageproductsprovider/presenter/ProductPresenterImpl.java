package com.ismael.manageproductsprovider.presenter;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CursorAdapter;

import com.ismael.manageproductsprovider.db.DatabaseContract;
import com.ismael.manageproductsprovider.interfaces.IProductPresenter;
import com.ismael.manageproductsprovider.model.Product;
import com.ismael.manageproductsprovider.provider.ManageProductContract;


/**
 * Created by usuario on 9/12/16.
 */

public class ProductPresenterImpl implements IProductPresenter, LoaderManager.LoaderCallbacks<Cursor>{

    private final static int PRODUCT = 3;
    View view;
    Context context;

    public ProductPresenterImpl(IProductPresenter.View Vista){
        this.view = Vista;
        this.context = view.getContext();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Loader ccl;

        switch (i){
            case PRODUCT:
                ccl = new CursorLoader(context, ManageProductContract.Product.CONTENT_URI, ManageProductContract.Product.PROJECTION, null, null, null);
                break;
            default:
                ccl=null;
        }

        return ccl;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.setNotificationUri(context.getContentResolver(), ManageProductContract.Product.CONTENT_URI); //Notificame cuando haya un cambio en algo
        view.setProductCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        view.setProductCursor(null);
    }

    @Override
    public void getAllProducts(CursorAdapter adapter) {
        ((Activity)context).getLoaderManager().initLoader(PRODUCT, null, this); //Inicializamos el cargador dentro del presentador.
    }

    @Override
    public void addProduct(Product p) {

        ContentValues cTmp = productToValues(p);
        context.getContentResolver().insert(ManageProductContract.Product.CONTENT_URI, cTmp);

    }

    @Override
    public boolean deleteProduct(int product) {
        int res = context.getContentResolver().delete(Uri.parse(ManageProductContract.Product.CONTENT_URI+"/"+String.valueOf(product)), null, null);
        if(res == -1){
            return false;
        }
        else
         return true;
    }

    @Override
    public void updateProduct(int idOld, Product newProduct) {
        ContentValues cNewP = productToValues(newProduct);
        //String[] args = new String[]{String.valueOf(idOld)};
        context.getContentResolver().update(Uri.parse(ManageProductContract.Product.CONTENT_URI+"/"+String.valueOf(idOld)), cNewP, null, null);
    }

    private ContentValues productToValues(Product p){

        ContentValues cv = new ContentValues();

            cv.put(ManageProductContract.Product.NAME, (p.getName()));
            cv.put(ManageProductContract.Product.DESCRIPTION, (p.getDescription()));
            cv.put(ManageProductContract.Product.BRAND, (p.getBrand()));
            cv.put(ManageProductContract.Product.DOSAGE, (p.getDosage()));
            cv.put(ManageProductContract.Product.PRICE, (p.getPrice()));
            cv.put(ManageProductContract.Product.STOCK, (p.getStock()));
            cv.put(ManageProductContract.Product.IMAGE, (p.getImage()));
            cv.put(ManageProductContract.Product.CATEGORY_ID, (p.getIdCategory()));

        return cv;
    }

    public Product getProductByName(String s) {
        String selection = ManageProductContract.Product.NAME +"="+s;
        Cursor c = context.getContentResolver().query(ManageProductContract.Product.CONTENT_URI, ManageProductContract.Product.PROJECTION, selection, null, null);

        Product pTmp = new Product();
        pTmp.setID(c.getInt(0));
        pTmp.setName(c.getString(1));
        pTmp.setDescription(c.getString(2));
        pTmp.setBrand(c.getString(3));
        pTmp.setDosage(c.getString(4));
        pTmp.setPrice(c.getDouble(5));
        pTmp.setStock(c.getString(6));
        pTmp.setImage(c.getString(7));
        pTmp.setIdCategory(c.getInt(8));

        return  pTmp;
    }
}
