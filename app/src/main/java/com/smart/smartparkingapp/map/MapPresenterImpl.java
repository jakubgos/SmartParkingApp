package com.smart.smartparkingapp.map;

import android.os.Handler;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.model.LatLng;
import com.smart.smartparkingapp.data.NetworkServiceImpl;
import com.smart.smartparkingapp.data.entity.Coordinates;
import com.smart.smartparkingapp.data.entity.LoginData;
import com.smart.smartparkingapp.data.entity.Parking;
import com.smart.smartparkingapp.map.interfaces.MapModel;
import com.smart.smartparkingapp.map.interfaces.MapPresenter;
import com.smart.smartparkingapp.map.interfaces.MapPresenterCallBackFromModel;
import com.smart.smartparkingapp.map.interfaces.MapView;

import java.io.IOException;
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
    List<Parking> parkingList;
    private final Handler handler = new Handler();


    public MapPresenterImpl(MapView view) {
        this.view = new WeakReference<>(view);
        this.mapModel = new MapModelImpl(new NetworkServiceImpl());
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


        //first location update
        if (this.coordinates == null) {
            Log.d("...", "reportLocation, first coordinates, getting parking list ");
            mapModel.getParkingList(loginData, coordinates, this);
            getView().createUserMarker(new LatLng(coordinates.getLatitude(),coordinates.getLongitude()));
            getView().moveUserCamera(new LatLng(coordinates.getLatitude(),coordinates.getLongitude()));

        }
        getView().moveUserMarker(new LatLng(coordinates.getLatitude(),coordinates.getLongitude()));

        this.coordinates = coordinates;
    }

    @Override
    public void messageArrivedInd(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Parking parkingToModify = objectMapper.readValue(message, Parking.class);

            getView().updateParkingMarker(parkingToModify);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getParkingListResult(final List<Parking> list) {
        parkingList= list;
        Log.d("...","getParkingListResult()");
        getView().initMQTT(list);
        Log.d("...", "getParkingListResult executed");
        handler.post(new Runnable() {
            @Override
            public void run() {

                for (Parking p:list
                     ) {
                    getView().showParkingPosition(p);
                }
                getView().centerCameraForParkings(list);

            }
        });
/* F
//OR TEST ONLY
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("...","sleep() start");

                    Thread.sleep(5000);
                    Log.d("...","sleep end()");

                    final Parking parkingtotest= list.get(1);

                    parkingtotest.setAvailablePlaces(11);
                    Log.d("...",parkingtotest.toString());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            getView().updateParkingMarker(parkingtotest);

                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
*/


    }

}
