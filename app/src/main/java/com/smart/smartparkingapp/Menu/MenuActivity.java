package com.smart.smartparkingapp.Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuPresenter;
import android.view.View;
import android.widget.Button;

import com.smart.smartparkingapp.Map.MapsActivity;
import com.smart.smartparkingapp.Menu.Interfaces.MenuPresenterOps;
import com.smart.smartparkingapp.Menu.Interfaces.MenuViewOps;
import com.smart.smartparkingapp.R;
import com.smart.smartparkingapp.login.Interfaces.LoginPresenterOps;
import com.smart.smartparkingapp.login.LoginPresenter;

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
