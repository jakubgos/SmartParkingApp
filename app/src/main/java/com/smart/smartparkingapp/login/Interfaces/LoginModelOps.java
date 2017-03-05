package com.smart.smartparkingapp.login.Interfaces;

import com.smart.smartparkingapp.login.Entity.LoginReqParam;

/**
 * Created by Bos on 2017-03-04.
 */
public interface LoginModelOps {
    void login(LoginReqParam loginReqParam);

    void validateLoginParameters(LoginReqParam loginReqParam);
}
