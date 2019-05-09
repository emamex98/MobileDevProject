package xyz.nuel.righttime;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Random;

@TargetApi(16)
public class NotReceiver extends BroadcastReceiver {
    private NotHelper notHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        notHelper = new NotHelper(context);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(context, ExerciseActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,intent1,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = notHelper.getRightNotification("RightTime","Time to do exercise");
        notHelper.getManager().notify(new Random().nextInt(), builder.build());

    }
}
