package com.smart.smartparkingapp.login.Interfaces;

import com.smart.smartparkingapp.login.Entity.LoginReqParam;
import com.smart.smartparkingapp.login.Entity.Result;

/**
 * Created by Bos on 2017-03-04.
 */
public interface LoginModelPresenterOps {
    void loginSuccess();

    void loginFailed(Result loginInvalid);

    void validateLoginParamFailed(Result loginInvalid);

    void validateLoginParamSuccess(LoginReqParam loginReqParam);
}
