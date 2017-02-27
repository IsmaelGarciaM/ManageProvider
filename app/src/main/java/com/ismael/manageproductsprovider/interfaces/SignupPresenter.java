package com.ismael.manageproductsprovider.interfaces;

/**
 * Created by usuario on 11/11/16.
 */
public interface SignupPresenter extends LoginPresenter {
    interface PresenterUser {
        int validateCredentialsEmail(String email);
        /*static int validateCredentialsEmail(String email) {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                return EMAIL_INVALID;
            else
                return OK;
        }*/
    }
}
