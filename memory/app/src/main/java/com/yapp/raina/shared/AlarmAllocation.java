package com.yapp.raina.shared;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.yapp.raina.dto.AnniversaryDto;
import com.yapp.raina.memory.service.AlarmService;

import java.util.Calendar;

/**
 * Created by Raina on 16. 3. 26..
 */

public class AlarmAllocation {
    public static void registerAlarm(Context mContext, AnniversaryDto dto)
    {

        int p_id = dto.getId_pk() ; //  알람 해제시 사용할 번호 - 디비의 ID_PK를 하면 고유할 거야
        int year = Calendar.getInstance().get(Calendar.YEAR);

        String[] date = dto.getDate_ymd().split("-");
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);;
        int hour = 3;
        int minute = 59;

        String message = "메시지";
        String title = dto.getTitle();


        AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(mContext, AlarmService.class);


        Bundle bundle = new Bundle();

        //	알람시 띄울 내용 (만약 기념일 날짜라면 날짜를 여기에 넣으면 될거야)
        bundle.putString("Message", message);
        bundle.putString("Title", title);
        bundle.putString("Category", dto.getCategory());
        intent.putExtras(bundle);

        PendingIntent sender = PendingIntent.getBroadcast(mContext, p_id, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, 0);
//        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        long triggerTime = SystemClock.elapsedRealtime() + 1000*30;
        am.set(AlarmManager.ELAPSED_REALTIME, triggerTime, sender);
    }

    public static void releaseAlarm(Context mContext, AnniversaryDto dto)
    {
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        Intent Intent = new Intent(mContext, AlarmService.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(mContext, dto.getId_pk(), Intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pIntent);
        pIntent.cancel();
    }

}
