package com.signme.signme.FCM;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by dsm2016 on 2017-09-27.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIDService";

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        String refreshedtoken = FirebaseInstanceId.getInstance().getToken();
    }
}
