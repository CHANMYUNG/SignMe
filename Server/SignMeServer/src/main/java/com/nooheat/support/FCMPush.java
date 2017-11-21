package com.nooheat.support;

import okhttp3.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NooHeat on 16/10/2017.
 */

public class FCMPush {

    // Method to send Notifications from server to client end.

    public final static String AUTH_KEY_FCM = "AAAADNBuB-I:APA91bGoXg9R2R6X9NYRmZBKJbyowPPozP0gb6zQL04PrZmJvOTRG3AXCWMBNsnCGlSuVg7IDQW0N6QQmvK7codn1YjyQ3-TKrUK_UsNMW5oUXIS9lhFgAKPZwo3acejEWg7Pe4ArTRa";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    // userDeviceIdKey is the device id you will query from your database

    public static void pushFCMNotification(String userDeviceIdKey, String message)
            throws Exception {
        String authKey = AUTH_KEY_FCM; // You FCM AUTH key
        String FMCurl = API_URL_FCM;
        System.out.println("TO : " + userDeviceIdKey);
        System.out.println("message : " + message);
        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        JSONObject info = new JSONObject();

        info.put("body", message); // Notification body

        json.put("notification", info);
        json.put("to", userDeviceIdKey.trim()); // deviceID

        try (OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream())) {
//혹시나 한글 깨짐이 발생하면
//try(OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")){ 인코딩을 변경해준다.

            wr.write(json.toString());
            wr.flush();
        } catch (Exception e) {
        }

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }

        conn.disconnect();
    }

}

