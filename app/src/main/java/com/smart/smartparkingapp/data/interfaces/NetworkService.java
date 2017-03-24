package com.smart.smartparkingapp.data.interfaces;

import com.smart.smartparkingapp.login.entity.LoginReqParam;
import com.smart.smartparkingapp.login.interfaces.LoginServiceResult;
import com.smart.smartparkingapp.parkingList.interfaces.FavoriteParkingCallback;

/**
 * Created by Bos on 2017-03-04.
 */
public interface NetworkService {
    void login(LoginReqParam loginReqParam, LoginServiceResult loginServiceResult);

    void getFavoriteParking(FavoriteParkingCallback callback);
}
