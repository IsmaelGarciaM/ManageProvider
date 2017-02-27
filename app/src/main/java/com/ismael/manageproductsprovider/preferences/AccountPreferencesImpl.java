package com.ismael.manageproductsprovider.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ismael.manageproductsprovider.interfaces.Preferences;

/**
 *
 */
public class AccountPreferencesImpl implements Preferences {
    private static Preferences accountPreferences;
    //public static final String FILE = "com.barranquero.manageproductsrecycler_preferences";
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";

    private SharedPreferences sharedPreferences;

    private AccountPreferencesImpl(Context c) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
    }

    //Singleton
    public static Preferences getInstance(Context c) {
        if (accountPreferences == null) {
            accountPreferences = new AccountPreferencesImpl(c);
        }
        return accountPreferences;
    }

    public void putUser(String user) {
        getEditor().putString(USER, user).apply();
    }

    public void putPassword(String password) {
        getEditor().putString(PASSWORD, password).apply();
    }

    public void putEmail(String email) {
        getEditor().putString(EMAIL, email).apply();
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }
}
