package com.smart.smartparkingapp.login.data;

import android.os.SystemClock;

import com.smart.smartparkingapp.login.entity.LoginReqParam;
import com.smart.smartparkingapp.login.interfaces.LoginService;
import com.smart.smartparkingapp.login.interfaces.LoginServiceResult;

import java.util.concurrent.TimeUnit;

/**
 * Created by Bos on 2017-03-04.
 */

public class LoginServiceImpl implements LoginService {


    @Override
    public void login(LoginReqParam loginReqParam, final LoginServiceResult callBack) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //todo add impl.
                SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));
                callBack.loginSuccess();
            }
        });
        thread.start();

    }
}
