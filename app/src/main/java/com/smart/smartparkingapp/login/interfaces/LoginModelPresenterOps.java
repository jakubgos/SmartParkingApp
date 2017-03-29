package com.smart.smartparkingapp.login.interfaces;

import com.smart.smartparkingapp.data.entity.LoginData;
import com.smart.smartparkingapp.login.entity.LoginReqParam;
import com.smart.smartparkingapp.login.entity.Result;

/**
 * Created by Bos on 2017-03-04.
 */
public interface LoginModelPresenterOps {
    void loginSuccess(LoginData s);

    void loginFailed(LoginData loginInvalid);

    void validateLoginParamFailed(Result loginInvalid);

    void validateLoginParamSuccess(LoginReqParam loginReqParam);
}
