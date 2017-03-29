package com.smart.smartparkingapp.map.interfaces;

import com.smart.smartparkingapp.data.entity.Parking;

import java.util.List;

/**
 * Created by Bos on 2017-03-26.
 */
public interface ParkingListCallback {
    void parkingListFailed(String s);

    void getParkingListResult(List<Parking> list);
}
