package com.nooheat.support;

import okhttp3.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NooHeat on 16/10/2017.
 */
public class FCM {
    private static String KEY = "AAAADNBuB-I:APA91bGoXg9R2R6X9NYRmZBKJbyowPPozP0gb6zQL04PrZmJvOTRG3AXCWMBNsnCGlSuVg7IDQW0N6QQmvK7codn1YjyQ3-TKrUK_UsNMW5oUXIS9lhFgAKPZwo3acejEWg7Pe4ArTRa";

    public static void notification(String to, String message) {
        OkHttpClient client = new OkHttpClient();
        Headers headers = new Headers.Builder()
                .set("Authorization", "key=" + KEY)
                .set("Content-Type", "application/json")
                .build();
        Map<String, Object> data = new HashMap<>();
        data.put("message", message);
        RequestBody body = new FormBody.Builder()
                .addEncoded("notification", data.toString())
                .addEncoded("to", to)
                .build();

        //request
        Request request = new Request.Builder()
                .header("Content-Type", "application/json")
                .addHeader("Authorization", "key=" + KEY)
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .build();


        try {
            Response res = client.newCall(request).execute();
            System.out.println(res.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}