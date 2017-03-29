package com.smart.smartparkingapp.data;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.smart.smartparkingapp.data.entity.Coordinates;
import com.smart.smartparkingapp.data.entity.LoginData;
import com.smart.smartparkingapp.data.entity.Parking;
import com.smart.smartparkingapp.login.entity.LoginReqParam;
import com.smart.smartparkingapp.data.interfaces.NetworkService;
import com.smart.smartparkingapp.login.interfaces.LoginServiceResult;
import com.smart.smartparkingapp.map.interfaces.ParkingListCallback;
import com.smart.smartparkingapp.parkingList.interfaces.FavoriteParkingCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Bos on 2017-03-04.
 */

public class NetworkServiceImpl implements NetworkService {

    private static final String SERVER_ADDRESS =
                                                "192.168.0.2";
    private static final String PARKING_PATH =
            "api/parkings";
    private static final String GRANT_TYPE =
            "password";
    private static final String CLIENT_ID =
            "client";
    private static final String CLIENT_SECRET =
            "secret";


    private static final String SERVER_PATH =
            "oauth/token";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void login(final LoginReqParam loginReqParam, final LoginServiceResult callBack) {

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("...", "view attemptLogin invoked");

                OkHttpClient okHttpClient = new OkHttpClient();

                HttpUrl url = new HttpUrl.Builder()
                        .scheme("http")
                        .host(SERVER_ADDRESS)
                        .port(8080)
                        .addPathSegments(SERVER_PATH)
                        .addQueryParameter("grant_type", GRANT_TYPE)
                        .addQueryParameter("client_id", CLIENT_ID)
                        .addQueryParameter("client_secret", CLIENT_SECRET)
                        .addQueryParameter("username", loginReqParam.getEmail())
                        .addQueryParameter("password", loginReqParam.getPassword())
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", Credentials.basic("client", "secret"))
                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), ""))
                        .build();
                Log.d("...", "view attemptLogin request " + request.toString());

                try {
                     Response response = okHttpClient
                        .newCall(request)
                        .execute();


                    String responseJson = response.body().string();

                    Log.d("...", "view attemptLogin result " + responseJson);
                    LoginData loginData = objectMapper.readValue(responseJson, LoginData.class);
                    Log.d("...", "view attemptLogin result loginData object" + loginData.toString());

                    if (response.isSuccessful()){
                        callBack.loginSuccess(loginData);
                    }
                    else
                    {
                        callBack.loginFailed(loginData);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    callBack.loginFailed(new LoginData("Unexpected Error"));
                    return ;
                }
            }
        });
        thread.start();
    }

    @Override
    public void getFavoriteParking(FavoriteParkingCallback callback) {
        //todo change it!
        ArrayList<Parking> result = new ArrayList<Parking>();
        result.add(new Parking((long)1,100,10,"parking 1", new Coordinates(20,20)));
        result.add(new Parking((long)2,100,10,"parking 2", new Coordinates(20,20)));
        result.add(new Parking((long)3,100,10,"parking 3", new Coordinates(20,20)));
        result.add(new Parking((long)4,100,10,"parking 4", new Coordinates(20,20)));
        result.add(new Parking((long)5,100,10,"parking 5", new Coordinates(20,20)));

        callback.onFavoriteParkingResult(result);

    }

    @Override
    public void getParkingList(final LoginData loginData, final Coordinates coordinates, final ParkingListCallback parkingListCallback) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("...", "view attemptLogin invoked");

        OkHttpClient okHttpClient = new OkHttpClient();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(coordinates);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            parkingListCallback.parkingListFailed("Failed to create Json Object");
        }

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(SERVER_ADDRESS)
                .port(8080)
                .addPathSegments(PARKING_PATH)
                .addQueryParameter("radius", "100")
                .addQueryParameter("latitude", "51.752565")
                .addQueryParameter("longitude", "19.453313")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer " + loginData.getAccess_token())
                .build();

        Log.d("...", "map getParkingList request " + request.toString() + "JSON: " + json);

        Response response = null;
        try {
            response = okHttpClient
                    .newCall(request)
                    .execute();

            String responseJson = response.body().string();
            Log.d("...", "view getParkingList result " + responseJson);

            //List<Parking> parkingList = objectMapper.readValue(responseJson, new TypeReference<List<Parking>>(){});

            List<Parking> list = objectMapper.readValue(responseJson, TypeFactory.defaultInstance().constructCollectionType(List.class,
                    Parking.class));

            Log.d("...", "view getParkingList list " + list.toString());

            parkingListCallback.getParkingListResult(list);
            //LoginData loginData = objectMapper.readValue(responseJson, LoginData.class);
            //Log.d("...", "view getParkingList result loginData object" + loginData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
            }
        });
        thread.start();

    }
}
