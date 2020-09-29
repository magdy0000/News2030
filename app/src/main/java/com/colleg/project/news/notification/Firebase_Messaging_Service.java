package com.colleg.project.news.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.colleg.project.news.Config.Config;
import com.colleg.project.news.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class Firebase_Messaging_Service extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        handleNotification(remoteMessage.getNotification().getBody());
        sendNotification(remoteMessage);

    }

    private void handleNotification(String body) {

        Intent pushNotification = new Intent(Config.STR_PUSH);
        pushNotification.putExtra("message" , body);
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
    }
private void sendNotification (RemoteMessage remoteMessage)
{
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(remoteMessage.getFrom())
        .setContentText(remoteMessage.getNotification().getBody())
        .setAutoCancel(true);

    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
}
}
