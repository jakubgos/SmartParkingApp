package com.smart.smartparkingapp.map;

import com.smart.smartparkingapp.data.entity.Coordinates;
import com.smart.smartparkingapp.data.entity.LoginData;
import com.smart.smartparkingapp.data.entity.Parking;
import com.smart.smartparkingapp.data.interfaces.NetworkService;
import com.smart.smartparkingapp.map.interfaces.MQTTService;
import com.smart.smartparkingapp.map.interfaces.MQTTServiceCallback;
import com.smart.smartparkingapp.map.interfaces.MapModel;
import com.smart.smartparkingapp.map.interfaces.MapPresenterCallBackFromModel;
import com.smart.smartparkingapp.map.interfaces.ParkingListCallback;

import java.util.List;

/**
 * Created by Bos on 2017-03-26.
 */
public class MapModelImpl implements MapModel {

    NetworkService networkService;
    MQTTService mQTTService;

    public MapModelImpl(NetworkService networkService, MQTTService mQTTService) {
        this.networkService = networkService;
        this.mQTTService=mQTTService;
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

    @Override
    public void initMQTT(List<Parking> list, final MapPresenterCallBackFromModel callBack) {
        mQTTService.init(list, new MQTTServiceCallback(){
            @Override
            public void mQTTchangeInd(List<Parking> result) {
                callBack.mQTTchangeInd(result);
            }

            @Override
            public void mQTTFailure(String s) {
                callBack.mQTTFailure(s);
            }
        });
    }
}
