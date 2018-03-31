package edu.temple.mapchat;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

//        sendRegistrationToServer(refreshedToken);
    }
}
