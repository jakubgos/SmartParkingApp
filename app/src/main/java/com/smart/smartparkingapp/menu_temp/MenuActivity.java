package com.smart.smartparkingapp.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smart.smartparkingapp.map.MapsActivity;
import com.smart.smartparkingapp.menu.interfaces.MenuPresenterOps;
import com.smart.smartparkingapp.menu.interfaces.MenuViewOps;
import com.smart.smartparkingapp.R;

public class MenuActivity extends AppCompatActivity implements MenuViewOps {

    MenuPresenterOps mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        Button mEmailSignInButton = (Button) findViewById(R.id.show_map_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            handleOnMapClick();
            }
        });



        setupMVP();

    }

    private void setupMVP() {
        MenuPresenterImpl presenter = new MenuPresenterImpl(this);
        mPresenter = presenter;
    }

    private void handleOnMapClick() {
        mPresenter.onMapSelect();
    }

    @Override
    public void loadMap() {
        Intent myIntent = new Intent(this, MapsActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        this.startActivity(myIntent);
    }
}