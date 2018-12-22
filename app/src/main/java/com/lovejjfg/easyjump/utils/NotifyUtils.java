/*
 * Copyright (c) 2017.  Joe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.lovejjfg.easyjump.utils;

import android.app.Notification;
import android.app.NotificationChannel;
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
            resultPendingIntent =
                PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        String channelId = "test" + System.currentTimeMillis();
        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(context, channelId)
                //这里没有背景透明的图片。
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle(
                    TextUtils.isEmpty(title) ? context.getResources().getString(R.string.app_name) : title)
                .setContentText("测测")
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setTicker("测测试试");

        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);

        int mNotificationId = 1212 + COUNT++;
        NotificationManager manager =
            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                new NotificationChannel(channelId, "name", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(notificationChannel);
        }
        manager.notify(mNotificationId, mBuilder.build());
    }
}
