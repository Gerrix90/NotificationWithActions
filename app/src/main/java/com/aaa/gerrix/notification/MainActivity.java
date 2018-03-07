package com.aaa.gerrix.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFY_ID = 100;
    private static final String YES_ACTION = "com.tinbytes.simplenotificationapp.YES_ACTION";
    private static final String MAYBE_ACTION = "com.tinbytes.simplenotificationapp.MAYBE_ACTION";
    private static final String NO_ACTION = "com.tinbytes.simplenotificationapp.NO_ACTION";

    private NotificationManager notificationManager;
//    private Notification notification;
    private boolean  firstTime = true;
    private NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.bActionButtonsNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showActionButtonsNotification();
            }
        });


        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        processIntentAction(getIntent());
        getSupportActionBar().hide();
    }

    private Intent getNotificationIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    private void showActionButtonsNotification() {
        Intent yesIntent = getNotificationIntent();
        yesIntent.setAction(YES_ACTION);

        Intent maybeIntent = getNotificationIntent();
        maybeIntent.setAction(MAYBE_ACTION);

        Intent noIntent = getNotificationIntent();
        noIntent.setAction(NO_ACTION);

        notification = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentIntent(PendingIntent.getActivity(this, 0, getNotificationIntent(), PendingIntent.FLAG_UPDATE_CURRENT))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Action Buttons Notification Received")
                .setContentTitle("Hi there!")
                .setContentText("This is even more text.")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .addAction(new Action(
                        R.mipmap.ic_thumb_up_black_36dp,
                        getString(R.string.yes),
                        PendingIntent.getActivity(this, 0, yesIntent, PendingIntent.FLAG_UPDATE_CURRENT)))
                .addAction(new Action(
                        R.mipmap.ic_thumbs_up_down_black_36dp,
                        getString(R.string.maybe),
                        PendingIntent.getActivity(this, 0, maybeIntent, PendingIntent.FLAG_UPDATE_CURRENT)))
                .addAction(new Action(
                        R.mipmap.ic_thumb_down_black_36dp,
                        getString(R.string.no),
                        PendingIntent.getActivity(this, 0, noIntent, PendingIntent.FLAG_UPDATE_CURRENT)));

        update(null);
    }

    private void update(String text) {
        if (!firstTime){
            notification.setContentTitle(text + " clicked!");
        }
        notificationManager.notify(NOTIFY_ID, notification.build());
        firstTime = false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntentAction(intent);
        super.onNewIntent(intent);
    }

    private void processIntentAction(Intent intent) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case YES_ACTION:
                    update("Yes");
                    Toast.makeText(this, "Yes :)", Toast.LENGTH_SHORT).show();
                    break;
                case MAYBE_ACTION:
                    update("Maybe");
                    Toast.makeText(this, "Maybe :|", Toast.LENGTH_SHORT).show();
                    break;
                case NO_ACTION:
                    update("No");
                    Toast.makeText(this, "No :(", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

}
