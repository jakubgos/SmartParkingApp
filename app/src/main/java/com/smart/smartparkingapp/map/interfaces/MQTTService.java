package com.smart.smartparkingapp.map.interfaces;

import com.smart.smartparkingapp.data.entity.Parking;

import java.util.List;

/**
 * Created by Bos on 2017-03-29.
 */
public interface MQTTService {
    void init(List<Parking> list, MQTTServiceCallback mqttServiceCallback);
}
