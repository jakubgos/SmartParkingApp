package com.smart.smartparkingapp.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smart.smartparkingapp.R;
import com.smart.smartparkingapp.data.entity.Coordinates;
import com.smart.smartparkingapp.data.entity.LoginData;
import com.smart.smartparkingapp.data.entity.Parking;
import com.smart.smartparkingapp.map.interfaces.MapPresenter;
import com.smart.smartparkingapp.map.interfaces.MapView;
import com.smart.smartparkingapp.parkingList.ParkingListPresenterImpl;
import com.smart.smartparkingapp.parkingList.ParkingListViewImpl;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;
import java.util.List;

public class  MapsActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener, MapView, MqttCallback{

    private GoogleMap mMap;
    MapPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setupMVP();

        LoginData loginData = null;
        try {
            loginData = new ObjectMapper().readValue(getIntent().getStringExtra("LOGIN_DATA"), LoginData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mPresenter.onStartup(loginData);
       // List<Parking>lista_test=null;
       // initMQTT(lista_test);
    }

    private void setupMVP() {
        MapPresenter presenter = new MapPresenterImpl(this);
        mPresenter = presenter;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //todo update
        if (id == R.id.find_closest) {
            mPresenter.findClosestParking();
        }else if (id == R.id.favorite) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void loadParkingListView() {
        Intent myIntent = new Intent(this, ParkingListViewImpl.class);
        //myIntent.putExtra("key", value); //Optional parameters
        this.startActivity(myIntent);
    }

    @Override
    public void initGps() {
        Log.d("...","initGps");

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        // getting GPS status
        boolean  isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                mPresenter.reportLocation(new Coordinates(location.getLatitude(), location.getLongitude() ));
                Log.d("...","onLocationChanged " + "lat: "+location.getLatitude() + " long: "+ location.getLongitude());

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }

        };
// Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("...","no Prem");
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1
            );
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1
            );
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

        Log.d("...","GPS started");
    }

    @Override
    public void showUserLocation(LatLng latLng) {
        MarkerOptions marker = new MarkerOptions().position(latLng).title("You");
        marker.icon(BitmapDescriptorFactory.defaultMarker((BitmapDescriptorFactory.HUE_ORANGE)));
        mMap.addMarker(marker);    }

    @Override
    public void moveMapUserCamera(LatLng latLng) {

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
    }

    @Override
    public void showParkingPosition(List<Parking> list) {

    }

    @Override
    public void initMQTT(final List<Parking> list){
        Log.d("...","view initMQTT()");

        String clientId = MqttClient.generateClientId();
        final MqttAndroidClient client = new MqttAndroidClient(this.getApplicationContext(), "tcp://192.168.0.103:1883", clientId);
        try{
            MqttConnectOptions options = new MqttConnectOptions();
            options.setPassword("guest".toCharArray());
            options.setUserName("guest");
            IMqttToken login_token = client.connect(options);
            client.setCallback(this);

            login_token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("Mqtt","Login successful");
                    for (int i = 0; i<list.size(); i++){
                        Long parking_id = list.get(i).getId();
                        final String topic = "parking/"+parking_id;//tutaj lista topiców odpowiadających wszystkim parkingom
                        try{
                            IMqttToken subscribe_token = client.subscribe(topic,1);
                            subscribe_token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    Log.d("Mqtt","Subscription successful. Subscribed to topic: "+ topic);
                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    Log.d("Mqtt","Subscription to topic "+topic+" failed. Reason: "+exception);
                                }
                            });
                        }catch (MqttException e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d("Mqtt","Login failed. Reason: "+exception);
                }
            });

        }catch (MqttException e){
            e.printStackTrace();
        }

    }

    @Override
    public void connectionLost(Throwable cause){
        Log.d("Connection lost", "Connection lost because: "+cause);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token){
        //TODO implementation?
    }

    @Override
    public void messageArrived(String topic, MqttMessage message){
        Log.d("Message", "Topic: "+topic+" Message: "+message.toString());
    }
}
