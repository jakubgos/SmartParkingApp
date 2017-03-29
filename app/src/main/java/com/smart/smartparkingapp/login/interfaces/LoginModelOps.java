package com.smart.smartparkingapp.login.interfaces;

import com.smart.smartparkingapp.login.entity.LoginReqParam;

/**
 * Created by Bos on 2017-03-04.
 */
public interface LoginModelOps {
    void login(LoginReqParam loginReqParam);

    void validateLoginParameters(LoginReqParam loginReqParam);
}
