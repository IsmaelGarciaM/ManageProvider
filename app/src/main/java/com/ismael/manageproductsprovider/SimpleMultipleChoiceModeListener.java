package com.ismael.manageproductsprovider;

import android.app.Activity;
import android.content.Context;
import android.nfc.tech.MifareUltralight;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;

import com.ismael.manageproductsprovider.interfaces.IProduct;
import com.ismael.manageproductsprovider.interfaces.IProductPresenter;
import com.ismael.manageproductsprovider.model.Product;
import com.ismael.manageproductsprovider.presenter.ProductPresenterImpl;

/**
 * Created by usuario on 16/12/16.
 */
public class SimpleMultipleChoiceModeListener {
    /*Context myContext;
}

    MultipleListener listener;
    int pos;
    public SimpleMultipleChoiceModeListener(Context context) {
        this.myContext = context;
        listener = ()
    }

    interface MultipleListener{
        void delete(int position);
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

    }

    /**
     * When it creates
     * @param mode
     * @param menu
     * @return

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {

    }

    /**
     * When it updates
     * @param mode
     * @param menu
     * @return

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

*//*
    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
*/
}
