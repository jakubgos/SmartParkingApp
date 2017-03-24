package com.smart.smartparkingapp.data;

import android.os.SystemClock;

import com.smart.smartparkingapp.data.entity.Coordinates;
import com.smart.smartparkingapp.data.entity.Parking;
import com.smart.smartparkingapp.login.entity.LoginReqParam;
import com.smart.smartparkingapp.data.interfaces.NetworkService;
import com.smart.smartparkingapp.login.interfaces.LoginServiceResult;
import com.smart.smartparkingapp.parkingList.interfaces.FavoriteParkingCallback;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.R.attr.name;

/**
 * Created by Bos on 2017-03-04.
 */

public class NetworkServiceImpl implements NetworkService {


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

    @Override
    public void getFavoriteParking(FavoriteParkingCallback callback) {
        //todo change it!
        ArrayList<Parking> result = new ArrayList<Parking>();
        result.add(new Parking((long)1,10,100,"parking 1", new Coordinates(20,20)));
        result.add(new Parking((long)2,10,100,"parking 2", new Coordinates(20,20)));
        result.add(new Parking((long)3,10,100,"parking 3", new Coordinates(20,20)));
        result.add(new Parking((long)4,10,100,"parking 4", new Coordinates(20,20)));
        result.add(new Parking((long)5,10,100,"parking 5", new Coordinates(20,20)));

        callback.onFavoriteParkingResult(result);

    }
}
