package com.smart.smartparkingapp.map.interfaces;

import com.smart.smartparkingapp.data.entity.Coordinates;
import com.smart.smartparkingapp.data.entity.LoginData;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Bos on 2017-03-21.
 */
public interface MapPresenter {


    void findClosestParking();

    void onStartup(LoginData loginData);

    void reportLocation(Coordinates coordinates);

    void messageArrivedInd(String message);
}
