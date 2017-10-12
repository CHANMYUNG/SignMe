package com.signme.signme.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.signme.signme.R;

/**
 * Created by dsm2016 on 2017-09-27.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService
{
    private static final String TAG="FirebaseMsgService";

    private String msg;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
       super.onMessageReceived(remoteMessage);
        if(remoteMessage.getNotification()!=null){
            Log.d(TAG,"Message data payload:"+remoteMessage.getNotification().getBody());
            NotificationCompat.Builder mbuilder=new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
               .setContentTitle("새로운 가정통신문이 올라왔습니다.")
               .setContentText(msg)
               .setAutoCancel(true)
               .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).setVibrate(new long[]{1,1000});
            NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0,mbuilder.build());
        }


//        msg=remoteMessage.getNotification().getBody();
//        Intent intent=new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent contentIntent=PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class),0);
//        NotificationCompat.Builder mbuilder=new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("새로운 가정통신문이 올라왔습니다.")
//                .setContentText(msg)
//                .setAutoCancel(true)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                .setVibrate(new long[]{1,1000});
//        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0,mbuilder.build());
//        mbuilder.setContentIntent(contentIntent);
    }
}
