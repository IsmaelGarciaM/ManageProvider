package com.ismael.manageproductsprovider.presenter;

/*
 * Copyright (c) 2017 José Luis del Pino Gallardo.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  jose.gallardo994@gmail.com
 */

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CursorAdapter;

import com.ismael.manageproductsprovider.interfaces.IPharmacyPresenter;
import com.ismael.manageproductsprovider.model.Pharmacy;
import com.ismael.manageproductsprovider.provider.ManageProductContract;


/**
 * Created by usuario on 30/01/17.
 */

public class PharmacyPresenterImpl implements IPharmacyPresenter, LoaderManager.LoaderCallbacks<Cursor> {

    private final static int PHARMACY = 2;
    IPharmacyPresenter.View view;
    private Context context;

    public PharmacyPresenterImpl(IPharmacyPresenter.View view) {
        this.view = view;
        this.context = view.getContext();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Loader ccl;

        switch (id){
            case PHARMACY:
                ccl = new CursorLoader(context, ManageProductContract.Pharmacy.CONTENT_URI, ManageProductContract.Pharmacy.PROJECTION, null, null, null);
                break;
            default:
                ccl=null;
        }

        return ccl;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.setNotificationUri(context.getContentResolver(), ManageProductContract.Pharmacy.CONTENT_URI); //Notificame cuando haya un cambio en algo
        view.setPharmacyCursor(data);
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
        view.setPharmacyCursor(null);
    }

    @Override
    public void getAllPharmacies(CursorAdapter adapter) {
        ((Activity)context).getLoaderManager().initLoader(PHARMACY, null, this); //Inicializamos el cargador dentro del presentador.
    }

    public void addPharmacy(Pharmacy pharmacy) {

    }
}
