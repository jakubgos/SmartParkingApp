package com.smart.smartparkingapp.map;

import android.util.Log;

import com.smart.smartparkingapp.data.NetworkServiceImpl;
import com.smart.smartparkingapp.data.entity.Coordinates;
import com.smart.smartparkingapp.data.entity.LoginData;
import com.smart.smartparkingapp.map.interfaces.MapModel;
import com.smart.smartparkingapp.map.interfaces.MapPresenter;
import com.smart.smartparkingapp.map.interfaces.MapPresenterCallBackFromModel;
import com.smart.smartparkingapp.map.interfaces.MapView;
import com.smart.smartparkingapp.parkingList.interfaces.ParkingListModel;

import java.lang.ref.WeakReference;

/**
 * Created by Bos on 2017-03-21.
 */
public class MapPresenterImpl implements MapPresenter, MapPresenterCallBackFromModel {
    private WeakReference<MapView> view;
    private Coordinates coordinates;
    private final MapModel mapModel;
    LoginData loginData;

    public MapPresenterImpl(MapView view) {
        this.view = new WeakReference<>(view);
        this.mapModel = new MapModelImpl(new NetworkServiceImpl());
        //this.loginModelOps = new LoginModel(this, new NetworkServiceImpl());
    }

    private MapView getView() throws NullPointerException {
        if ( view != null )
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    @Override
    public void findClosestParking() {
        getView().loadParkingListView();
    }

    @Override
    public void onStartup(LoginData loginData) {
        this.loginData = loginData;
        getView().initGps();
    }

    @Override
    public void reportLocation(Coordinates coordinates) {
        Log.d("...", "reportLocation");

        //first location update
        if(this.coordinates == null)
        {
            Log.d("...", "reportLocation, first coordinates, getting parking list ");

            mapModel.getParkingList(loginData,coordinates, this);
        }
        this.coordinates=coordinates;
        getView().showUserLocation(coordinates);
    }
}
