package com.smart.smartparkingapp.map.interfaces;

import com.smart.smartparkingapp.data.entity.Parking;

import java.util.List;

/**
 * Created by Bos on 2017-03-26.
 */
public interface MapPresenterCallBackFromModel {
    void getParkingListResult(List<Parking> list);

}
