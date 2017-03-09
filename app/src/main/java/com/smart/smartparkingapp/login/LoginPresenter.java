package com.smart.smartparkingapp.login;

import android.os.Handler;
import android.util.Log;

import com.smart.smartparkingapp.R;
import com.smart.smartparkingapp.login.Data.LoginServiceImpl;
import com.smart.smartparkingapp.login.Entity.LoginReqParam;
import com.smart.smartparkingapp.login.Entity.Result;
import com.smart.smartparkingapp.login.Interfaces.LoginModelOps;
import com.smart.smartparkingapp.login.Interfaces.LoginModelPresenterOps;
import com.smart.smartparkingapp.login.Interfaces.LoginPresenterOps;
import com.smart.smartparkingapp.login.Interfaces.LoginViewOps;

import java.lang.ref.WeakReference;

/**
 * Created by Bos on 2017-03-04.
 */
public class LoginPresenter implements LoginPresenterOps, LoginModelPresenterOps {

    private WeakReference<LoginViewOps> loginViewOps;
    private final LoginModelOps loginModelOps;
    private final Handler handler = new Handler();
    public LoginPresenter(LoginViewOps loginViewOps) {
        this.loginViewOps = new WeakReference<>(loginViewOps);
        this.loginModelOps = new LoginModel(this, new LoginServiceImpl());
    }

    private LoginViewOps  getView() throws NullPointerException {
        if ( loginViewOps != null )
            return loginViewOps.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    @Override
    public void attemptLogin(LoginReqParam loginReqParam) {
        Log.d("...","presenter attemptLogin invoked");

        getView().resetLoginErrors();

        //first check if given parameters are correct (do not check with server yet)
        loginModelOps.validateLoginParameters(loginReqParam);
    }

    @Override
    public void onResume() {
        getView().showProgress(false);
    }

    @Override
    public void loginSuccess() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().showMainMenuFragment();
                //getView().showProgress(false);

            }
        });
    }

    @Override
    public void loginFailed(Result loginInvalid) {
        getView().showProgress(false);
        getView().showLoginError(loginInvalid);
    }

    @Override
    public void validateLoginParamFailed(Result loginInvalid) {
        getView().showLoginError(loginInvalid);
    }

    @Override
    public void validateLoginParamSuccess(LoginReqParam loginReqParam) {
        getView().showProgress(true);
        loginModelOps.login(loginReqParam);
    }
}
