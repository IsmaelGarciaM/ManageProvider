package com.ismael.manageproductsprovider.presenter;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;

import com.ismael.manageproductsprovider.db.DatabaseContract;
import com.ismael.manageproductsprovider.interfaces.ICategoryPresenter;
import com.ismael.manageproductsprovider.model.Category;
import com.ismael.manageproductsprovider.provider.ManageProductContract;


/**
 * Created by usuario on 26/01/17.
 */

public class CategoryPresenterImpl implements ICategoryPresenter, LoaderManager.LoaderCallbacks<Cursor> {

    private final static int CATEGORY=1;
    private ICategoryPresenter.View view;
    private Context context;

    public CategoryPresenterImpl(ICategoryPresenter.View view) {
        this.view = view;
        this.context = view.getContext();
    }

    @Override
    public void getAllCategories(android.widget.CursorAdapter adapter) {
        ((Activity)context).getLoaderManager().initLoader(CATEGORY, null, this);
    }

    public void addCategory(Category category) {
        this.restartLoader();
    }

    private void restartLoader() {
        ((Activity) context).getLoaderManager().restartLoader(CATEGORY, null, this);
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Loader ccl;

        switch (id){
            case CATEGORY:
                ccl = new CursorLoader(context, ManageProductContract.Category.CONTENT_URI, ManageProductContract.Category.PROJECTION, null, null, DatabaseContract.CategoryEntry.DEFAULT_SORT);
                break;
            default:
                ccl=null;
        }

        return ccl;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.setNotificationUri(context.getContentResolver(), ManageProductContract.Category.CONTENT_URI); //Notificame cuando haya un cambio en algo
        view.setCursorCategory(data);
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        view.setCursorCategory(null);
    }


}
