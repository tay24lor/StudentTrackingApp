package android.zybooks.studentprogressapplication;

import static android.zybooks.studentprogressapplication.UI.CourseDetails.NOTIFICATION_CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

public class MyReceiver extends BroadcastReceiver {
    public static String NOTIFICATION = "notification";
    public static String NOTIFICATION_ID = "notification-id";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE );
        Notification notification = intent.getParcelableExtra( NOTIFICATION );

        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(notificationChannel);
        int id = intent.getIntExtra( NOTIFICATION_ID , 0 );
        notificationManager.notify(id, notification);
        /*notificationID = intent.getIntExtra("id", -1);
        Toast.makeText(context, intent.getStringExtra("key"), Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channel_id);
        Notification n = new NotificationCompat.Builder(context, channel_id)
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, n);*/
    }
    // TODO: This method is called when the BroadcastReceiver is receiving
    // an Intent broadcast.


    /*private void createNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = context.getResources().getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }*/
}