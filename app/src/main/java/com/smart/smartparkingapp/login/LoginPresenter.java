package com.smart.smartparkingapp.login;

import android.os.Handler;
import android.util.Log;

import com.smart.smartparkingapp.data.NetworkServiceImpl;
import com.smart.smartparkingapp.data.entity.LoginData;
import com.smart.smartparkingapp.login.entity.LoginReqParam;
import com.smart.smartparkingapp.login.entity.Result;
import com.smart.smartparkingapp.login.interfaces.LoginModelOps;
import com.smart.smartparkingapp.login.interfaces.LoginModelPresenterOps;
import com.smart.smartparkingapp.login.interfaces.LoginPresenterOps;
import com.smart.smartparkingapp.login.interfaces.LoginViewOps;

import java.lang.ref.WeakReference;

import static com.smart.smartparkingapp.login.entity.Result.AuthFailed;

/**
 * Created by Bos on 2017-03-04.
 */
public class LoginPresenter implements LoginPresenterOps, LoginModelPresenterOps {

    private WeakReference<LoginViewOps> loginViewOps;
    private final LoginModelOps loginModelOps;
    private final Handler handler = new Handler();
    public LoginPresenter(LoginViewOps loginViewOps) {
        this.loginViewOps = new WeakReference<>(loginViewOps);
        this.loginModelOps = new LoginModel(this, new NetworkServiceImpl());
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
    public void onStartup() {
}

    @Override
    public void loginSuccess(final LoginData s) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().showMapActivity(s);
                //getView().showProgress(false);

            }
        });
    }

    @Override
    public void loginFailed(final LoginData loginInvalid) {
        handler.post(new Runnable() {
            @Override
            public void run() {
        getView().showProgress(false);
        getView().showLoginError(AuthFailed, loginInvalid.getMessage());
            }
        });
    }

    @Override
    public void validateLoginParamFailed(Result loginInvalid) {
        getView().showLoginError(loginInvalid, "");
    }

    @Override
    public void validateLoginParamSuccess(LoginReqParam loginReqParam) {
        getView().showProgress(true);
        loginModelOps.login(loginReqParam);
    }
}
