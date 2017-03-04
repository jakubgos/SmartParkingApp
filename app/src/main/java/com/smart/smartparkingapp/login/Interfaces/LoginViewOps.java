package com.smart.smartparkingapp.login.Interfaces;

/**
 * Created by Bos on 2017-03-04.
 */
public interface LoginViewOps {


    void showLoginError(String msg);

    void showProgress(boolean b);

    void resetLoginErrors();

    void showMainMenuFragment();
}

