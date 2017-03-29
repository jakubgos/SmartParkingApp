package com.smart.smartparkingapp.map.interfaces;

import com.smart.smartparkingapp.data.entity.Coordinates;

/**
 * Created by Bos on 2017-03-21.
 */
public interface MapView {
    void loadParkingListView();

    void initGps();

    void showUserLocation(Coordinates coordinates);
}
