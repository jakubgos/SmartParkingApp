package com.smart.smartparkingapp.map;

import com.smart.smartparkingapp.data.entity.Coordinates;
import com.smart.smartparkingapp.data.entity.LoginData;
import com.smart.smartparkingapp.data.entity.Parking;
import com.smart.smartparkingapp.data.interfaces.NetworkService;

import com.smart.smartparkingapp.map.interfaces.MapModel;
import com.smart.smartparkingapp.map.interfaces.MapPresenterCallBackFromModel;
import com.smart.smartparkingapp.map.interfaces.ParkingListCallback;

import java.util.List;

/**
 * Created by Bos on 2017-03-26.
 */
public class MapModelImpl implements MapModel {

    NetworkService networkService;

    public MapModelImpl(NetworkService networkService) {
        this.networkService = networkService;
    }


    @Override
    public void getParkingList(LoginData loginData, Coordinates coordinates, final MapPresenterCallBackFromModel callBack) {
        networkService.getParkingList(loginData, coordinates , new ParkingListCallback(){
            @Override
            public void parkingListFailed(String s) {

            }

            @Override
            public void getParkingListResult(List<Parking> list) {
                callBack.getParkingListResult(list);
            }
        });
 }
}
