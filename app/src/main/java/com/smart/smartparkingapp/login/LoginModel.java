package com.smart.smartparkingapp.login;

import com.smart.smartparkingapp.data.entity.LoginData;
import com.smart.smartparkingapp.login.entity.LoginReqParam;
import com.smart.smartparkingapp.login.entity.Result;
import com.smart.smartparkingapp.login.interfaces.LoginModelOps;
import com.smart.smartparkingapp.login.interfaces.LoginModelPresenterOps;
import com.smart.smartparkingapp.data.interfaces.NetworkService;
import com.smart.smartparkingapp.login.interfaces.LoginServiceResult;

/**
 * Created by Bos on 2017-03-04.
 */

public class LoginModel implements LoginModelOps {

    private final NetworkService loginService;

    //todo remove:, replace with callback
    LoginModelPresenterOps loginPresenter;


    public LoginModel(LoginModelPresenterOps loginPresenter, NetworkService loginService) {
        this.loginPresenter=loginPresenter;
        this.loginService=loginService;
    }


    @Override
    public void login(LoginReqParam loginReqParam) {
        loginService.login(loginReqParam, new LoginServiceResult() {
            @Override
            public void loginSuccess(LoginData s) {
                loginPresenter.loginSuccess(s);
            }

            @Override
            public void loginFailed(LoginData loginData) {
                loginPresenter.loginFailed(loginData);
            }
        });
    }

    @Override
    public void validateLoginParameters(LoginReqParam loginReqParam) {
        boolean error= false;
        if (!isEmailValid(loginReqParam.getEmail())) {
            error = true;
            loginPresenter.validateLoginParamFailed(Result.LoginInvalid);
        }
        if(!isPasswordValid(loginReqParam.getPassword())) {
            error = true;
            loginPresenter.validateLoginParamFailed(Result.PasswordInvalid);
        }
        if (!error) {
                loginPresenter.validateLoginParamSuccess(loginReqParam);
        }

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
