package com.smart.smartparkingapp.map.interfaces;

import com.smart.smartparkingapp.data.entity.Parking;

import java.util.List;

/**
 * Created by Bos on 2017-03-29.
 */
public interface MQTTServiceCallback {
    void mQTTchangeInd(List<Parking> result);

    void mQTTFailure(String s);
}
