package com.smart.smartparkingapp.data;

import com.smart.smartparkingapp.data.entity.Parking;
import com.smart.smartparkingapp.map.interfaces.MQTTService;
import com.smart.smartparkingapp.map.interfaces.MQTTServiceCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bos on 2017-03-29.
 */
public class MQTTServiceImpl implements MQTTService {


    @Override
    public void init(List<Parking> list, final MQTTServiceCallback mqttServiceCallback) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //todo Implementation HERE
                List<Parking> result = new ArrayList<Parking>();

                result.add(new Parking());
                mqttServiceCallback.mQTTchangeInd(result);


                mqttServiceCallback.mQTTFailure("some msg");





            }
        });

        thread.start();

    }
}
