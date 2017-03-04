package com.smart.smartparkingapp.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.smart.smartparkingapp.MapsActivity;
import com.smart.smartparkingapp.login.LoginActivity;

import java.util.concurrent.TimeUnit;

/**
 * Created by Bos on 2017-03-04.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}