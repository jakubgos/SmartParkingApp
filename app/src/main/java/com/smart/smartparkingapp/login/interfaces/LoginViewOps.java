package com.smart.smartparkingapp.login.interfaces;

import com.smart.smartparkingapp.data.entity.LoginData;
import com.smart.smartparkingapp.login.entity.Result;

/**
 * Created by Bos on 2017-03-04.
 */
public interface LoginViewOps {

    void showProgress(boolean b);

    void showLoginError(Result msg, String message);

    void resetLoginErrors();

    void showMapActivity(LoginData s);
}

