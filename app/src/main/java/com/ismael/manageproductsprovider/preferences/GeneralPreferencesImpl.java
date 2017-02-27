package com.ismael.manageproductsprovider.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.ismael.manageproductsprovider.interfaces.Preferences;
/**
 * Created by usuario on 10/11/16.
 */

public class GeneralPreferencesImpl implements Preferences {
    private static Preferences generalPreferences;
    private SharedPreferences sharedPreferences;

    public static Preferences getInstance(Context context) {
        if (generalPreferences == null) {
            generalPreferences= new GeneralPreferencesImpl(context);
        }
        return generalPreferences;
    }

    private GeneralPreferencesImpl(Context c) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
    }
}
