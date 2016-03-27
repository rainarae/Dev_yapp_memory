package com.yapp.raina.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yapp.raina.dto.AnniversaryDto;
import com.yapp.raina.shared.AlarmAllocation;
import com.yapp.raina.shared.SharedData;

import java.util.ArrayList;
import java.util.Calendar;

public class AnniversaryDao {

    private SQLiteDatabase db;

    private static final String TABLE_NAME = "anniversary_tb";
    private static final String KEY_ID_PK = "ID_PK";
    private static final String KEY_DATE_YMD = "DATE_YMD";
    private static final String KEY_TITLE = "TITLE";
    private static final String KEY_ABSTRACT = "ABSTRACT";
    private static final String KEY_CONTENT = "CONTENT";
    private static final String KEY_REFERENCE = "REFERENCE";
    private static final String KEY_CATEGORY = "CATEGORY";
    private static final String KEY_ALARM_ST = "ALARM_ST";
    private static final String KEY_SOLAR_ST = "SOLAR_ST";
    private static final String KEY_BOOKMARK_ST = "BOOKMARK_ST";

    public AnniversaryDao(SQLiteDatabase db) {
        this.db = db;
    }

    public void insert(AnniversaryDto dto) {
//        ContentValues values = new ContentValues();
//        values.put(KEY_DATE_YMD, dto.getDate_ymd());
//        values.put(KEY_TITLE, dto.getTitle());
//        db.insert(TABLE_NAME, null, values);
    }

    public void delete(AnniversaryDto dto) {
        db.delete(TABLE_NAME, KEY_ID_PK + "=" + dto.getId_pk(), null);
    }

    public void update(AnniversaryDto dto) {
//        ContentValues values = new ContentValues();
//        values.put(KEY_BOOKMARK_ST, false);
//        String whereClause = KEY_ID_PK + "?";
//        String[] whereArgs = new String[] {dto.getId_pk()};
    }

    public ArrayList<AnniversaryDto> updateAllAlarm(boolean onOff) {
        //알람전체 켜기
        if (onOff)
            db.execSQL("update anniversary_tb set ALARM_ST = 'TRUE';");
        //알람전체 끄기
        else {
            db.execSQL("update anniversary_tb set ALARM_ST = 'FALSE';");
        }

        return selectAll();
    }

    public boolean updateAlarmByPid(Context mContext, AnniversaryDto dto){
        Log.d("before dto", dto.toString());
        if (dto.getAlarm_st()) {
            db.execSQL("update anniversary_tb set ALARM_ST = 'FALSE' where ID_PK = " + dto.getId_pk() + ";");
            AlarmAllocation.releaseAlarm(mContext, dto);

            String date[] = dto.getDate_ymd().split("-");
            int month = Calendar.getInstance().get(Calendar.MONTH) + 1;

            if (Integer.parseInt(date[1]) == month || Integer.parseInt(date[1]) == (month + 1)){
                for (int i = 0; i < SharedData.monthList.size(); i++){
                    if (SharedData.monthList.get(i).getId_pk() == dto.getId_pk()){
                        SharedData.monthList.get(i).setAlarm_st(false);
                        break;
                    }
                }

                for (int i = 0; i < SharedData.UpcomingList.size(); i++){
                    if (SharedData.UpcomingList.get(i).getId_pk() == dto.getId_pk()){
                        SharedData.UpcomingList.get(i).setAlarm_st(false);
                        break;
                    }
                }
            }
            Log.d("AlarmSetting:", "FALSE SETTING");

            return false;
        }
        else {
            db.execSQL("update anniversary_tb set ALARM_ST = 'TRUE' where ID_PK = " + dto.getId_pk() + ";");
            AlarmAllocation.registerAlarm(mContext, dto);

            String date[] = dto.getDate_ymd().split("-");
            int month = Calendar.getInstance().get(Calendar.MONTH) + 1;

            if (Integer.parseInt(date[1]) == month || Integer.parseInt(date[1]) == (month + 1)){
                for (int i = 0; i < SharedData.monthList.size(); i++){
                    if (SharedData.monthList.get(i).getId_pk() == dto.getId_pk()){
                        SharedData.monthList.get(i).setAlarm_st(true);
                        break;
                    }
                }


                for (int i = 0; i < SharedData.UpcomingList.size(); i++){
                    if (SharedData.UpcomingList.get(i).getId_pk() == dto.getId_pk()){
                        SharedData.UpcomingList.get(i).setAlarm_st(true);
                        break;
                    }
                }
            }

            Log.d("AlarmSetting:", "TRUE SETTING");
            return true;
        }


    }

    //select lists by category
    public ArrayList<AnniversaryDto> selectListByCategory(String category) {
        ArrayList<AnniversaryDto> list = new ArrayList<AnniversaryDto>();
        Cursor c = db.rawQuery("SELECT * FROM anniversary_tb WHERE CATEGORY = '" + category + "' ORDER BY strftime('%m-%d',DATE_YMD);;", null);
        while (c.moveToNext()) {

            AnniversaryDto dto = new AnniversaryDto();
            dto.setId_pk(c.getInt(c.getColumnIndex(KEY_ID_PK)));
            dto.setDate_ymd(c.getString(c.getColumnIndex(KEY_DATE_YMD)));
            dto.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
            dto.setAbstract_(c.getString(c.getColumnIndex(KEY_ABSTRACT)));
            dto.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));

            dto.setReference(c.getString(c.getColumnIndex(KEY_REFERENCE)));
            dto.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
            dto.setAlarm_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_ALARM_ST))));
            dto.setSolar_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_SOLAR_ST))));
            dto.setBookmark_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_BOOKMARK_ST))));
            list.add(dto);
            Log.i("Cherish??", dto.toString());
        }
        c.close();
        return list;


    }

    public void updateBookmark(AnniversaryDto dto) {
//        ContentValues values = new ContentValues();
//        values.put(KEY_BOOKMARK_ST, false);
//        String whereClause = KEY_ID_PK + "=?";
//        String[] whereArgs = new String[] {dto.getId_pk()};

        Log.d("before dto", dto.toString());
        if (dto.getBookmark_st())
            db.execSQL("update anniversary_tb set BOOKMARK_ST = 'FALSE' where ID_PK = " + dto.getId_pk() + ";");
        else
            db.execSQL("update anniversary_tb set BOOKMARK_ST = 'TRUE' where ID_PK = " + dto.getId_pk() + ";");
        AnniversaryDto test = selectByKey(dto.getId_pk());
        Log.d("update dto", test.toString());

    }

    public ArrayList<AnniversaryDto> selectAll() {
        ArrayList<AnniversaryDto> listDto = new ArrayList<AnniversaryDto>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        int i = 0;
        while (c.moveToNext()) {
            AnniversaryDto dto = new AnniversaryDto();
            dto.setId_pk(c.getInt(c.getColumnIndex(KEY_ID_PK)));
            dto.setDate_ymd(c.getString(c.getColumnIndex(KEY_DATE_YMD)));
            dto.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
            dto.setAbstract_(c.getString(c.getColumnIndex(KEY_ABSTRACT)));
            dto.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));

            dto.setReference(c.getString(c.getColumnIndex(KEY_REFERENCE)));
            dto.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
            dto.setAlarm_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_ALARM_ST))));
            dto.setSolar_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_SOLAR_ST))));
            dto.setBookmark_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_BOOKMARK_ST))));
            listDto.add(dto);
        }
        c.close();
        return listDto;
    }

    public ArrayList<AnniversaryDto> selectFavoriteList() {
        ArrayList<AnniversaryDto> listDto = new ArrayList<AnniversaryDto>();
        Cursor c = db.rawQuery("SELECT * FROM anniversary_tb WHERE BOOKMARK_ST = 'TRUE'", null);
        Log.d("number::::", c.getCount() + "");

        while (c.moveToNext()) {
            AnniversaryDto dto = new AnniversaryDto();
            dto.setId_pk(c.getInt(c.getColumnIndex(KEY_ID_PK)));
            dto.setDate_ymd(c.getString(c.getColumnIndex(KEY_DATE_YMD)));
            dto.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
            dto.setAbstract_(c.getString(c.getColumnIndex(KEY_ABSTRACT)));
            dto.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));

            dto.setReference(c.getString(c.getColumnIndex(KEY_REFERENCE)));
            dto.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
            dto.setAlarm_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_ALARM_ST))));
            dto.setSolar_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_SOLAR_ST))));
            dto.setBookmark_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_BOOKMARK_ST))));
            listDto.add(dto);
        }
        c.close();
        return listDto;
    }

    public ArrayList<AnniversaryDto> selectByMonth(int month) {
        ArrayList<AnniversaryDto> listDto = new ArrayList<AnniversaryDto>();

        String mon;
        if (month < 10) {
            mon = "0" + String.valueOf(month);
        } else {
            mon = String.valueOf(month);
        }
        Cursor c = db.rawQuery("SELECT * FROM anniversary_tb WHERE DATE_YMD LIKE '%-" + mon + "-%' ORDER BY strftime('%m-%d',DATE_YMD);'", null);

        while (c.moveToNext()) {
            AnniversaryDto dto = new AnniversaryDto();
            dto.setId_pk(c.getInt(c.getColumnIndex(KEY_ID_PK)));
            dto.setDate_ymd(c.getString(c.getColumnIndex(KEY_DATE_YMD)));
            dto.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
            dto.setAbstract_(c.getString(c.getColumnIndex(KEY_ABSTRACT)));
            dto.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));

            dto.setReference(c.getString(c.getColumnIndex(KEY_REFERENCE)));
            dto.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
            dto.setAlarm_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_ALARM_ST))));
            dto.setSolar_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_SOLAR_ST))));
            dto.setBookmark_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_BOOKMARK_ST))));
            listDto.add(dto);
        }

        Log.d("month_num : ", listDto.size()+"");
        c.close();
        return listDto;
    }

    public ArrayList<AnniversaryDto> selectUpcomingEvent(String today) {
        ArrayList<AnniversaryDto> listDto = new ArrayList<AnniversaryDto>();

        Log.i("today --- : ", today);

        Cursor c = db.rawQuery("SELECT *  FROM anniversary_tb WHERE strftime('%m-%d',DATE_YMD) >= '" + today + "' ORDER BY strftime('%m-%d',DATE_YMD) LIMIT 3;", null);
        while (c.moveToNext()) {
            AnniversaryDto dto = new AnniversaryDto();
            dto.setId_pk(c.getInt(c.getColumnIndex(KEY_ID_PK)));
            dto.setDate_ymd(c.getString(c.getColumnIndex(KEY_DATE_YMD)));
            dto.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
            dto.setAbstract_(c.getString(c.getColumnIndex(KEY_ABSTRACT)));
            dto.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));

            dto.setReference(c.getString(c.getColumnIndex(KEY_REFERENCE)));
            dto.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
            dto.setAlarm_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_ALARM_ST))));
            dto.setSolar_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_SOLAR_ST))));
            dto.setBookmark_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_BOOKMARK_ST))));
            listDto.add(dto);
            Log.i("datais------:", dto.toString());
        }
        c.close();
        Log.i("size------:", listDto.size() + "");
        return listDto;
    }

    public AnniversaryDto selectByKey(int key_id) {
        AnniversaryDto dto = new AnniversaryDto();
        Cursor c = db.rawQuery("SELECT * FROM anniversary_tb where ID_PK = " + dto.getId_pk() + ";", null);
        while (c.moveToNext()) {
            dto.setId_pk(c.getInt(c.getColumnIndex(KEY_ID_PK)));
            dto.setDate_ymd(c.getString(c.getColumnIndex(KEY_DATE_YMD)));
            dto.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
            dto.setAbstract_(c.getString(c.getColumnIndex(KEY_ABSTRACT)));
            dto.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));

            dto.setReference(c.getString(c.getColumnIndex(KEY_REFERENCE)));
            dto.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
            dto.setAlarm_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_ALARM_ST))));
            dto.setSolar_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_SOLAR_ST))));
            dto.setBookmark_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_BOOKMARK_ST))));
        }

        return dto;
    }


}
