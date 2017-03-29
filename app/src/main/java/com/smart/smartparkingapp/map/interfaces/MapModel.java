package com.smart.smartparkingapp.map.interfaces;

import com.smart.smartparkingapp.data.entity.Coordinates;
import com.smart.smartparkingapp.data.entity.LoginData;
import com.smart.smartparkingapp.map.MapPresenterImpl;

/**
 * Created by Bos on 2017-03-26.
 */
public interface MapModel {
    void getParkingList(LoginData loginData, Coordinates coordinates, MapPresenterCallBackFromModel callBack);
}
