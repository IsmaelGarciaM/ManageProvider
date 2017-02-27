package com.ismael.manageproductsprovider;

import android.app.Application;
import android.content.Context;

import com.ismael.manageproductsprovider.db.DatabaseHelper;

/**
 * Created by usuario on 20/01/17.
 */

public class ManageProductApplication extends Application {

    static Context mpa;


    public ManageProductApplication(){
        super();
        mpa = this;
    }

    public static Context getContext(){

        return mpa;
    }

}
