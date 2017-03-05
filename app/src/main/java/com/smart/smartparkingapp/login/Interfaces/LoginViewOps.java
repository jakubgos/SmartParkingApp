package com.smart.smartparkingapp.login.Interfaces;

import com.smart.smartparkingapp.login.Entity.Result;

/**
 * Created by Bos on 2017-03-04.
 */
public interface LoginViewOps {

    void showProgress(boolean b);

    void showLoginError(Result msg);

    void resetLoginErrors();

    void showMainMenuFragment();
}

