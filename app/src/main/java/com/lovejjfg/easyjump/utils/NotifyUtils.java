package com.lovejjfg.easyjump.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;

import com.lovejjfg.easyjump.MainActivity;
import com.lovejjfg.easyjump.R;

/**
 * Created by joe on 2017/12/28.
 * Email: lovejjfg@gmail.com
 */

public class NotifyUtils {

    private static int COUNT = 1;

    public static void createNotify(Context context, String url, String title) {
        Intent resultIntent = JumpUtils.parseIntent(context, url, title);
        PendingIntent resultPendingIntent;
        if (!ViewUtils.isLaunchedActivity(context, MainActivity.class)) {
            //Intent resultIntent1 = new Intent(context, SplashActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            // Adds the back stack
            ComponentName component = resultIntent.getComponent();
            stackBuilder.addParentStack(component);
            // Adds the Intent to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            // Gets a PendingIntent containing the entire back stack
            resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            // TODO: 2017/12/28 demo 测试时其实会一直走这里的
            resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "test" + System.currentTimeMillis())
                        //这里没有背景透明的图片。
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(
                                TextUtils.isEmpty(title) ? context.getResources().getString(R.string.app_name) : title)
                        .setContentText("测测")
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setTicker("测测试试");

        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);

        int mNotificationId = 1212 + COUNT++;
        NotificationManager mNotifyMgr =
                (NotificationManager) context
                        .getSystemService(
                                Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
