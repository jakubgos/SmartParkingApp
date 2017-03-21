package com.smart.smartparkingapp.map;

import com.smart.smartparkingapp.map.interfaces.MapPresenter;
import com.smart.smartparkingapp.map.interfaces.MapView;
import com.smart.smartparkingapp.parkingList.interfaces.ParkingListView;

import java.lang.ref.WeakReference;

/**
 * Created by Bos on 2017-03-21.
 */
public class MapPresenterImpl implements MapPresenter {
    private WeakReference<MapView> view;

    public MapPresenterImpl(MapView view) {
        this.view = new WeakReference<>(view);
        //this.loginModelOps = new LoginModel(this, new LoginServiceImpl());
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
}
