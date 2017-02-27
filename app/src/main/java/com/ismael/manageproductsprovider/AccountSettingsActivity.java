package com.ismael.manageproductsprovider;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Activity which manages the account settings
 * @author José Antonio Barranquero
 * @version 1.0
 */
public class AccountSettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_account);
    }
}
