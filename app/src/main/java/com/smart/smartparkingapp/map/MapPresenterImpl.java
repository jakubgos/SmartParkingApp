package com.smart.smartparkingapp.map;

import android.os.Handler;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.smart.smartparkingapp.data.MQTTServiceImpl;
import com.smart.smartparkingapp.data.NetworkServiceImpl;
import com.smart.smartparkingapp.data.entity.Coordinates;
import com.smart.smartparkingapp.data.entity.LoginData;
import com.smart.smartparkingapp.data.entity.Parking;
import com.smart.smartparkingapp.map.interfaces.MQTTService;
import com.smart.smartparkingapp.map.interfaces.MapModel;
import com.smart.smartparkingapp.map.interfaces.MapPresenter;
import com.smart.smartparkingapp.map.interfaces.MapPresenterCallBackFromModel;
import com.smart.smartparkingapp.map.interfaces.MapView;
import com.smart.smartparkingapp.parkingList.interfaces.ParkingListModel;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Bos on 2017-03-21.
 */
public class MapPresenterImpl implements MapPresenter, MapPresenterCallBackFromModel {
    private WeakReference<MapView> view;
    private Coordinates coordinates;
    private final MapModel mapModel;
    LoginData loginData;
    private final Handler handler = new Handler();


    public MapPresenterImpl(MapView view) {
        this.view = new WeakReference<>(view);
        this.mapModel = new MapModelImpl(new NetworkServiceImpl(), new MQTTServiceImpl());
        //this.loginModelOps = new LoginModel(this, new NetworkServiceImpl());
    }

    private MapView getView() throws NullPointerException {
        if (view != null)
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

        getView().showUserLocation(new LatLng(coordinates.getLatitude(),coordinates.getLongitude()));

        //first location update
        if (this.coordinates == null) {
            Log.d("...", "reportLocation, first coordinates, getting parking list ");
            mapModel.getParkingList(loginData, coordinates, this);
            getView().moveMapUserCamera(new LatLng(coordinates.getLatitude(),coordinates.getLongitude()));
        }
        this.coordinates = coordinates;
    }

    @Override
    public void getParkingListResult(final List<Parking> list) {
        Log.d("...","getParkingListResult()");
        getView().initMQTT(list);
        Log.d("...", "getParkingListResult executed");
        handler.post(new Runnable() {
            @Override
            public void run() {

                getView().showParkingPosition(list);
            }
        });

    }

    @Override
    public void mQTTchangeInd(final List<Parking> result) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("...", "mQTTchangeInd recived in presenter with data: " + result.toString());

            }
        });
    }

    @Override
    public void mQTTFailure(final String s) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("...", "mQTTchangeInd mQTTFailure in presenter with data: " + s);

            }
        });
    }


}
