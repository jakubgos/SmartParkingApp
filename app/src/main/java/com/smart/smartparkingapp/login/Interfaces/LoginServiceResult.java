package com.smart.smartparkingapp.login.interfaces;

import com.smart.smartparkingapp.data.entity.LoginData;

/**
 * Created by Bos on 2017-03-04.
 */

public interface LoginServiceResult {

    void loginSuccess(LoginData s);

    void loginFailed(LoginData loginData);
}
