package com.smart.smartparkingapp.login.Interfaces;

import com.smart.smartparkingapp.login.Entity.LoginReqParam;

/**
 * Created by Bos on 2017-03-04.
 */
public interface LoginService {
    void login(LoginReqParam loginReqParam, LoginServiceResult loginServiceResult);
}
