package com.smart.smartparkingapp.parkingList;

import com.smart.smartparkingapp.login.interfaces.LoginViewOps;
import com.smart.smartparkingapp.menu.interfaces.MenuViewOps;
import com.smart.smartparkingapp.parkingList.interfaces.ParkingListPresenter;
import com.smart.smartparkingapp.parkingList.interfaces.ParkingListView;

import java.lang.ref.WeakReference;

/**
 * Created by Bos on 2017-03-21.
 */

public class ParkingListPresenterImpl implements ParkingListPresenter {

    private WeakReference<ParkingListView> view;

    public ParkingListPresenterImpl(ParkingListView view) {
        this.view = new WeakReference<>(view);
        //this.loginModelOps = new LoginModel(this, new LoginServiceImpl());
    }

    private ParkingListView getView() throws NullPointerException {
        if ( view != null )
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

}
