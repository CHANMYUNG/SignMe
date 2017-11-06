package com.signme.signme.FCM;

import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.JsonObject;
import com.signme.signme.activity.HomeActivity;
import com.signme.signme.activity.MyApplication;
import com.signme.signme.server.APIInterface;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NooHeat on 16/10/2017.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";


    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + token);

        // 생성등록된 토큰을 개인 앱서버에 보내 저장해 두었다가 추가 뭔가를 하고 싶으면 할 수 있도록 한다.
        // sendRegistrationToServer(token, accessToken);
    }

    public static void sendRegistrationToServer(String token, String accessToken) {
        // Add custom implementation, as needed.

        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIInterface.URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIInterface apiInterface = retrofit.create(APIInterface.class);

        HashMap<String, Object> map = new HashMap<>();
        map.put("token", token);

        Call<Void> call = apiInterface.refreshFCMToken(map, accessToken);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                response.code();
                Log.d(TAG, "onResponse: refreshed");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "onResponse: failed");
                t.printStackTrace();
            }
        });

    }
}
