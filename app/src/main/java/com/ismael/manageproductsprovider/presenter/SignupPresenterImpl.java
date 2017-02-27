package com.ismael.manageproductsprovider.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.ismael.manageproductsprovider.R;
import com.ismael.manageproductsprovider.interfaces.LoginPresenter;
import com.ismael.manageproductsprovider.interfaces.SignupPresenter;
import com.ismael.manageproductsprovider.preferences.AccountPreferencesImpl;
import com.ismael.manageproductsprovider.utils.ErrorMapUtils;

import static com.ismael.manageproductsprovider.model.Error.DATA_EMPTY;
import static com.ismael.manageproductsprovider.model.Error.INVALID_EMAIL;
import static com.ismael.manageproductsprovider.model.Error.OK;
import static com.ismael.manageproductsprovider.model.Error.PASSWORD_CASE;
import static com.ismael.manageproductsprovider.model.Error.PASSWORD_DIGIT;
import static com.ismael.manageproductsprovider.model.Error.PASSWORD_LENGTH;

/**
 * Presenter
 */
public class SignupPresenterImpl implements SignupPresenter.Presenter, SignupPresenter.PresenterUser {
    private int validateUser, validatePassword, validateEmail;
    private LoginPresenter.View view;
    private Context context;

    public SignupPresenterImpl(LoginPresenter.View view) {
        this.view = view;
        this.context = (Context)view;
    }

    public void validateCredentials(String user, String password, String email) {
        validateUser = validateCredentialsUser(user);
        validatePassword = validateCredentialsPassword(password);
        validateEmail = validateCredentialsEmail(email);

        if (validateUser == OK) {
            if (validatePassword == OK) {
                if (validateEmail == OK) {
                    savePreferences(user, password, email);
                    view.startActivity();
                } else {
                    String nameResource = ErrorMapUtils.getErrorMap(context).get(String.valueOf(validateEmail));
                    view.setMessageError(nameResource, R.id.tilEmail);
                }
            } else {
                String nameResource = ErrorMapUtils.getErrorMap(context).get(String.valueOf(validatePassword));
                view.setMessageError(nameResource, R.id.tilPassword);
            }
        } else {
            String nameResource = ErrorMapUtils.getErrorMap(context).get(String.valueOf(validateUser));
            view.setMessageError(nameResource, R.id.tilUser);
        }
    }

    private void savePreferences(String user, String password, String email) {
        AccountPreferencesImpl accountPreferences = (AccountPreferencesImpl) AccountPreferencesImpl.getInstance(this.context);
        accountPreferences.putUser(user);
        accountPreferences.putPassword(password);
        accountPreferences.putEmail(email);
    }

    @Override
    public int validateCredentialsEmail(String email) {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                return INVALID_EMAIL;
            else
                return OK;
    }

    @Override
    public int validateCredentialsUser(String user) {
        if (TextUtils.isEmpty(user)) {
            return DATA_EMPTY;
        }
        return OK;
    }

    @Override
    public int validateCredentialsPassword(String password) {
        int result = OK;
        if (TextUtils.isEmpty(password)) {
            result = DATA_EMPTY;
        } else {
            if (!(password.matches("(.*)\\d(.*)"))) {
                result = PASSWORD_DIGIT;
            }
            if (!(password.matches("(.*)\\p{Lower}(.*)") && password.matches("(.*)\\p{Upper}(.*)"))) {
                result = PASSWORD_CASE;
            }
            if (password.length() < 8) {
                result = PASSWORD_LENGTH;
            }
        }
        return result;
    }
}
