package com.smart.smartparkingapp.parkingList;

import com.smart.smartparkingapp.data.entity.Parking;
import com.smart.smartparkingapp.data.interfaces.NetworkService;
import com.smart.smartparkingapp.parkingList.interfaces.FavoriteParkingCallback;
import com.smart.smartparkingapp.parkingList.interfaces.ParkingCallBackFromModel;
import com.smart.smartparkingapp.parkingList.interfaces.ParkingListModel;

import java.util.ArrayList;

/**
 * Created by Bos on 2017-03-24.
 */
public class ParkingListModelImpl implements ParkingListModel {


    NetworkService networkService;
    public ParkingListModelImpl(NetworkService networkService) {
        this.networkService=networkService;
    }

    @Override
    public void getFavoriteParking(final ParkingCallBackFromModel callBackFromModel) {
        networkService.getFavoriteParking(new FavoriteParkingCallback() {
            @Override
            public void onFavoriteParkingResult(ArrayList<Parking> result) {
                callBackFromModel.onFavoriteParkingResult(result);
            }
        });


    }
}
