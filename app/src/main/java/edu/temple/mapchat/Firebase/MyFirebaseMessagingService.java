package edu.temple.mapchat.Firebase;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() { super(); }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Intent intent = new Intent("Get message");
        intent.putExtra("Message", remoteMessage.getData().get("message"));

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//        }
//
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message notification body: " + remoteMessage.getNotification().getBody());
//        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }

//    @Override
//    public void handleIntent(Intent intent) {
//        super.handleIntent(intent);
//    }
}
