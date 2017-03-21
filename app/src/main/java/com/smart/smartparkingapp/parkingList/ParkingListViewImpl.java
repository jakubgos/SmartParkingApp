package com.smart.smartparkingapp.parkingList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smart.smartparkingapp.R;
import com.smart.smartparkingapp.map.MapsActivity;
import com.smart.smartparkingapp.menu.MenuPresenterImpl;
import com.smart.smartparkingapp.menu.interfaces.MenuPresenterOps;
import com.smart.smartparkingapp.menu.interfaces.MenuViewOps;
import com.smart.smartparkingapp.parkingList.interfaces.ParkingListPresenter;
import com.smart.smartparkingapp.parkingList.interfaces.ParkingListView;

public class ParkingListViewImpl extends AppCompatActivity implements ParkingListView {

    ParkingListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_list);


        setupMVP();

    }

    private void setupMVP() {
        ParkingListPresenter presenter = new ParkingListPresenterImpl(this);
        mPresenter = presenter;
    }



}
