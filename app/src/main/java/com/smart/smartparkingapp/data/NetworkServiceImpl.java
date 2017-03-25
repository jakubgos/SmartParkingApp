package com.smart.smartparkingapp.data;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.smartparkingapp.data.entity.Coordinates;
import com.smart.smartparkingapp.data.entity.LoginData;
import com.smart.smartparkingapp.data.entity.Parking;
import com.smart.smartparkingapp.login.entity.LoginReqParam;
import com.smart.smartparkingapp.data.interfaces.NetworkService;
import com.smart.smartparkingapp.login.interfaces.LoginServiceResult;
import com.smart.smartparkingapp.parkingList.interfaces.FavoriteParkingCallback;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Bos on 2017-03-04.
 */

public class NetworkServiceImpl implements NetworkService {

    private static final String GETTING_TOKEN =
            "http://client:secret@192.168.0.2:8080/oauth/token?grant_type=password&client_id=client&client_secret=secret&username=john.doe@gmail.com&password=password";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void login(final LoginReqParam loginReqParam, final LoginServiceResult callBack) {

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("...", "view attemptLogin invoked");

                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new FormBody.Builder()
                        .build();
                Request request = new Request.Builder()
                        .url(GETTING_TOKEN)
                        .post(formBody)
                        .build();

               Log.d("...", "view attemptLogin request " + request.toString());

                try {
                    Response response = client.newCall(request).execute();

                    String responseJson = response.body().string();

                    Log.d("...", "view attemptLogin result " + responseJson);
                    LoginData loginData = objectMapper.readValue(responseJson, LoginData.class);
                    Log.d("...", "view attemptLogin result object" + loginData.toString());

                    if (loginData.getStatus() == -1){
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
}
