package edu.temple.mapchat.Firebase;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    public static final String FCM_TOKEN_KEY = "fcm_token_key";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
//        sendRegistrationToServer(refreshedToken);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
                editor.putString("FCM_TOKEN", refreshedToken)
                .putBoolean("TOKEN_SAVED", false)
                .apply();
    }
}
