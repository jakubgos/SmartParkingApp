package com.smart.smartparkingapp.parkingList;

import android.os.Handler;

import com.smart.smartparkingapp.data.NetworkServiceImpl;
import com.smart.smartparkingapp.data.entity.Parking;
import com.smart.smartparkingapp.parkingList.interfaces.ParkingCallBackFromModel;
import com.smart.smartparkingapp.parkingList.interfaces.ParkingListModel;
import com.smart.smartparkingapp.parkingList.interfaces.ParkingListPresenter;
import com.smart.smartparkingapp.parkingList.interfaces.ParkingListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Bos on 2017-03-21.
 */

public class ParkingListPresenterImpl implements ParkingListPresenter, ParkingCallBackFromModel {

    private WeakReference<ParkingListView> view;
    private final ParkingListModel parkingListModel;
    private final Handler handler = new Handler();

    public ParkingListPresenterImpl(ParkingListView view) {
        this.view = new WeakReference<>(view);
        this.parkingListModel = new ParkingListModelImpl(new NetworkServiceImpl());
        //this.loginModelOps = new LoginModel(this, new NetworkServiceImpl());
    }

    private ParkingListView getView() throws NullPointerException {
        if ( view != null )
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    @Override
    public void onStartUp() {
        parkingListModel.getFavoriteParking(this);
    }

    @Override
    public void onFavoriteParkingResult(final ArrayList<Parking> parkingList) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().showParkingList(parkingList);
            }
        });
    }
}
