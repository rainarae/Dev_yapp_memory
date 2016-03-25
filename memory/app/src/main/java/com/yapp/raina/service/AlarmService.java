package com.yapp.raina.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

//	Alarm을 위해 Background로 동작할 서비스 클레스
public class AlarmService extends BroadcastReceiver
{
    int MID=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ReCEIVER!!", "com");
//        Toast.makeText(AlarmService.this, "service!", Toast.LENGTH_SHORT).show();
    }
}
