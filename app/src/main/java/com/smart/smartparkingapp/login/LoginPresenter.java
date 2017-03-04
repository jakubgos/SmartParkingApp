package com.smart.smartparkingapp.login;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.smart.smartparkingapp.login.Data.LoginServiceImpl;
import com.smart.smartparkingapp.login.Entity.LoginReqParam;
import com.smart.smartparkingapp.login.Interfaces.LoginModelOps;
import com.smart.smartparkingapp.login.Interfaces.LoginModelPresenterOps;
import com.smart.smartparkingapp.login.Interfaces.LoginPresenterOps;
import com.smart.smartparkingapp.login.Interfaces.LoginViewOps;

/**
 * Created by Bos on 2017-03-04.
 */
public class LoginPresenter implements LoginPresenterOps, LoginModelPresenterOps {

    private final LoginViewOps loginViewOps;
    private final LoginModelOps loginModelOps;
    private final Handler handler = new Handler();
    public LoginPresenter(LoginViewOps loginViewOps) {
        this.loginViewOps = loginViewOps;
        this.loginModelOps = new LoginModel(this, new LoginServiceImpl());
    }

    @Override
    public void attemptLogin(LoginReqParam loginReqParam) {
        Log.d("...","presenter attemptLogin invoked");


        //TODO przerobic to trzeba wby weryfikacja byla w loginModel + ladne wyswqietlanie bledow
        boolean cancel = false;
        loginViewOps.resetLoginErrors();
        String msg = "no error";
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(loginReqParam.getPassword()) || !isPasswordValid(loginReqParam.getPassword())) {
            msg = "Password is too short";
            loginViewOps.showLoginError(msg);
            cancel = true;
        }

        Log.d("...","presenter attemptLogin: Cancel " + cancel);

        if (cancel) {

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            loginViewOps.showLoginError("no.");
        }
        else
        {
            loginViewOps.showProgress(true);
            loginModelOps.login(loginReqParam);
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

    @Override
    public void loginSuccess() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                loginViewOps.showProgress(false);
                loginViewOps.showMainMenuFragment();
            }
        });
    }

    @Override
    public void loginFailed() {

    }
}
