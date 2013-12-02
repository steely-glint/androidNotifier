package uk.co.westhawk.pulley;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PulleySetup extends Activity {

    /**
     * Called when the activity is first created.
     */
    /*    @Override
     public void onCreate(Bundle savedInstanceState)
     {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.main);
     }
     */
    Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //We get a reference to the NotificationManager
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                String MyText = "Reminder";
                Notification mNotification = new Notification(R.drawable.ic_launcher, MyText, System.currentTimeMillis());
                //The three parameters are: 1. an icon, 2. a title, 3. time when the notification appears

                String MyNotificationTitle = "Test!";
                String MyNotificationText = "We will invoke chrome from here somehow!";

                Intent myIntent = new Intent("android.intent.action.MAIN");
                myIntent.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                myIntent.addCategory("android.intent.category.LAUNCHER");
                myIntent.setData(Uri.parse("http://twelephone.com/"));
                PendingIntent StartIntent = PendingIntent.getActivity(getApplicationContext(), 0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                //A PendingIntent will be fired when the notification is clicked. The FLAG_CANCEL_CURRENT flag cancels the pendingintent

                mNotification.setLatestEventInfo(getApplicationContext(), MyNotificationTitle, MyNotificationText, StartIntent);

                int NOTIFICATION_ID = 1;
                notificationManager.notify(NOTIFICATION_ID, mNotification);
                //We are passing the notification to the NotificationManager with a unique id.
            }
        });
    }
}
