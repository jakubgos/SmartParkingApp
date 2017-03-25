package com.smart.smartparkingapp.map;

import com.smart.smartparkingapp.data.entity.Coordinates;
import com.smart.smartparkingapp.data.entity.LoginData;
import com.smart.smartparkingapp.map.interfaces.MapPresenter;
import com.smart.smartparkingapp.map.interfaces.MapView;

import java.lang.ref.WeakReference;

/**
 * Created by Bos on 2017-03-21.
 */
public class MapPresenterImpl implements MapPresenter {
    private WeakReference<MapView> view;
    private Coordinates coordinates;
    public MapPresenterImpl(MapView view) {
        this.view = new WeakReference<>(view);
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
        getView().initGps();
    }

    @Override
    public void reportLocation(Coordinates coordinates) {
        this.coordinates=coordinates;
        getView().showUserLocation(coordinates);
    }
}
