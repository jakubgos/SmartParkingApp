package com.smart.smartparkingapp.data.interfaces;

import com.smart.smartparkingapp.data.entity.Coordinates;
import com.smart.smartparkingapp.data.entity.LoginData;
import com.smart.smartparkingapp.login.entity.LoginReqParam;
import com.smart.smartparkingapp.login.interfaces.LoginServiceResult;
import com.smart.smartparkingapp.map.interfaces.ParkingListCallback;
import com.smart.smartparkingapp.parkingList.interfaces.FavoriteParkingCallback;

/**
 * Created by Bos on 2017-03-04.
 */
public interface NetworkService {
    void login(LoginReqParam loginReqParam, LoginServiceResult loginServiceResult);

    void getFavoriteParking(FavoriteParkingCallback callback);

    void getParkingList(LoginData loginData, Coordinates coordinates, ParkingListCallback parkingListCallback);
}
