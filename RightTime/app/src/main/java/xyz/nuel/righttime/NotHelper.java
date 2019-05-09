package xyz.nuel.righttime;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

@TargetApi(26)
public class NotHelper extends ContextWrapper {

    private static final String CHANNEL_ID = "xyz.nuel.righttime.UYX";
    private static final String CHANNEL_NAME = "RightTime";
    private NotificationManager manager;
    public NotHelper(Context base) {
        super(base);
        createChannels();
    }


    private void createChannels(){
        NotificationChannel rightChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            rightChannel.enableLights(true);
            rightChannel.enableVibration(true);
            rightChannel.setLightColor(Color.GREEN);
            rightChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(rightChannel);
    }

    public NotificationManager getManager(){
        if (manager == null){
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;

    }
    public Notification.Builder getRightNotification(String title, String body){
        return new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentText(body)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.clock)
                .setAutoCancel(true);
    }
}
