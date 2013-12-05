/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.westhawk.pulley;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 *
 * @author tim
 */
public class MessageRcv extends BroadcastReceiver {
    private final static String TAG = "Pulley";

    private void sendNotification(Context con, String text,String ntitle,String ntext,String uri) {
        //We get a reference to the NotificationManager
        NotificationManager notificationManager = (NotificationManager) con.getSystemService(Context.NOTIFICATION_SERVICE);

        String myText = (text == null)?"example notification":text;
        Notification mNotification = new Notification(R.drawable.ic_launcher, myText, System.currentTimeMillis());
        //The three parameters are: 1. an icon, 2. a title, 3. time when the notification appears

        String myNotificationTitle = (ntitle == null)?"notification title ":ntitle;
        String myNotificationText = (ntext == null)?"notification text ":ntext;
        String myUri = (uri == null)?"https://github.com/steely-glint/":uri;

        Intent myIntent = new Intent("android.intent.action.MAIN");
        myIntent.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
        myIntent.addCategory("android.intent.category.LAUNCHER");
        myIntent.setData(Uri.parse(myUri));
        PendingIntent StartIntent = PendingIntent.getActivity(con.getApplicationContext(), 0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        //A PendingIntent will be fired when the notification is clicked. The FLAG_CANCEL_CURRENT flag cancels the pendingintent

        mNotification.setLatestEventInfo(con.getApplicationContext(), myNotificationTitle, myNotificationText, StartIntent);

        int NOTIFICATION_ID = 1;
        notificationManager.notify(NOTIFICATION_ID, mNotification);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Explicitly specify that GcmIntentService will handle the intent.
        //ComponentName comp = new ComponentName(context.getPackageName(),
        //       NotifierIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        //startWakefulService(context, (intent.setComponent(comp)));

        Log.d("Pulley", "Message rcvd");
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Log.d(TAG, "Extras "+ extras.toString());
                String text = (String) extras.get("text");
                                String ntitle = (String) extras.get("ntitle");
                String ntext = (String) extras.get("ntext");
                String uri = (String) extras.get("uri");

                Log.d(TAG, " "+ text+" "+ntitle+" "+ntext+" "+uri);

                sendNotification(context,text,ntitle,ntext,uri);
                
            }
        }
        setResultCode(Activity.RESULT_OK);
    }
}
