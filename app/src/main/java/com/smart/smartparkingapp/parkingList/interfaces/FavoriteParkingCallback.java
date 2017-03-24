package com.smart.smartparkingapp.parkingList.interfaces;

import com.smart.smartparkingapp.data.entity.Parking;

import java.util.ArrayList;

/**
 * Created by Bos on 2017-03-24.
 */
public interface FavoriteParkingCallback {

    void onFavoriteParkingResult(ArrayList<Parking> parkingList);
}
