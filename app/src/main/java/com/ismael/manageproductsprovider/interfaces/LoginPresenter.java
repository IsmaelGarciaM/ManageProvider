package com.ismael.manageproductsprovider.interfaces;

/**
 * Interface with methods to implement by the View and the Presenter
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public interface LoginPresenter {
    /**
     * Inner interface to be implemented by the View
     */
    interface View {
        void setMessageError(String error, int idView);
        void startActivity();
    }

    /**
     * Inner interface to be implemented by the Presenter
     */
    interface Presenter {
        int validateCredentialsUser(String user);
        int validateCredentialsPassword(String password);

        /*static int validateCredentialsUser(String user) {
            if (TextUtils.isEmpty(user)) {
                return DATA_EMPTY;
            }
            return OK;
        }

        static int validateCredentialsPassword(String password) {
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
        }*/

        //boolean validateCredentials(String user, String password);
    }
}