package com.yapp.raina.memory.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.yapp.raina.memory.MainActivity;
import com.yapp.raina.memory.R;

//	Alarm을 위해 Background로 동작할 서비스 클레스
public class AlarmService extends BroadcastReceiver
{
    int MID=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notifier = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d("ReCEIVER!!", "com");
        String title = intent.getStringExtra("Title");
        String category = intent.getStringExtra("Category");


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent intent2 = new Intent(context, MainActivity.class);

        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pender = PendingIntent.getActivity(context
                , 1
                , intent2
                , PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle("ㄱ하니");
        builder.setContentText("오늘은 '" + title + "' 날 입니다.");
        if (category.equals("HISTORICAL")) {
            builder.setSmallIcon(R.mipmap.detail_icon02);
        } else if (category.equals("NATIONAL")) {
            builder.setSmallIcon(R.mipmap.detail_icon_01);
        } else if (category.equals("CHERISH")) {
            builder.setSmallIcon(R.mipmap.detail_icon03);
        }

        builder.setContentIntent(pender);
        Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(ringtoneUri);
        long[] pattern = {200, 200, 500, 300};
        builder.setVibrate(pattern);
        builder.setLights(Color.BLUE, 500, 500);
        notifier.notify(0, builder.build());
//        Toast.makeText(AlarmService.this, "service!", Toast.LENGTH_SHORT).show();
    }
}
