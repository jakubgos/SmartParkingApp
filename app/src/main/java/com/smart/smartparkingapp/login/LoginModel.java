package com.smart.smartparkingapp.login;

import com.smart.smartparkingapp.login.Entity.LoginReqParam;
import com.smart.smartparkingapp.login.Entity.Result;
import com.smart.smartparkingapp.login.Interfaces.LoginModelOps;
import com.smart.smartparkingapp.login.Interfaces.LoginModelPresenterOps;
import com.smart.smartparkingapp.login.Interfaces.LoginService;
import com.smart.smartparkingapp.login.Interfaces.LoginServiceResult;

/**
 * Created by Bos on 2017-03-04.
 */

public class LoginModel implements LoginModelOps {

    private final LoginService loginService;
    LoginModelPresenterOps loginPresenter;


    public LoginModel(LoginModelPresenterOps loginPresenter, LoginService loginService) {
        this.loginPresenter=loginPresenter;
        this.loginService=loginService;
    }


    @Override
    public void login(LoginReqParam loginReqParam) {
        loginService.login(loginReqParam, new LoginServiceResult() {
            @Override
            public void loginSuccess() {
                loginPresenter.loginSuccess();
            }

            @Override
            public void loginFailed() {
                loginPresenter.loginFailed(Result.LoginInvalid);
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
