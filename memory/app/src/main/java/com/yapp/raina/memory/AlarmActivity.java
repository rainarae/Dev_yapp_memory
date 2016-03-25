package com.yapp.raina.memory;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yapp.raina.db.DBManager;
import com.yapp.raina.dto.AnniversaryDto;
import com.yapp.raina.service.AlarmService;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    //drawer menu
    private LinearLayout menuHome, menuCategory, menuFavorites, menuSetting;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle dtToggle;

    private Toolbar toolbar;
    private LinearLayout toolbarMenuButton;

    //all_alarm_on/off
    private ImageButton btn_all_alarm;
    private ArrayList<AnniversaryDto> listAlarmSet;

    private Context context;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        toolbarInit();
        drawerMenuInit();

        init();
    }

    public void init() {
        btn_all_alarm = (ImageButton) findViewById(R.id.btn_all_alarm);
        listAlarmSet = new ArrayList<AnniversaryDto>();
        context = this;
        dbManager = DBManager.getInstance(context);
    }

    private void toolbarInit() {
        toolbar = (Toolbar) findViewById(R.id.start_tool_bar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        dtToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                toolbarMenuButton.setEnabled(true);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                toolbarMenuButton.setEnabled(false);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        drawerLayout.setDrawerListener(dtToggle);
        dtToggle.syncState();

        setSupportActionBar(toolbar);

        toolbar.setTitle("");
        toolbar.getMenu().clear();

        toolbarMenuButton = (LinearLayout) findViewById(R.id.layout_menu_btn);
        toolbarMenuButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view == menuHome) {
            Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            drawerLayout.closeDrawers();
            startActivity(i);
            finish();
        } else if (view == menuCategory) {
            Toast.makeText(this, "Category", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, SettingActivity.class);
            drawerLayout.closeDrawers();
            startActivity(i);
            finish();
        } else if (view == menuFavorites) {
            Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, FavoritesActivity.class);
            drawerLayout.closeDrawers();
            startActivity(i);
            finish();
        } else if (view == menuSetting) {
            Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, SettingActivity.class);
            drawerLayout.closeDrawers();
            startActivity(i);
            finish();
        }
        if (view == toolbarMenuButton) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawers();
            else
                drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void mOnClick(View v) {

        switch (v.getId()) {
            case R.id.btn_all_alarm:
                Log.i("click:", v.getId() + "");
                listAlarmSet = dbManager.anniversaryDao.updateAllAlarm(false);
                for (int i = 0; i < listAlarmSet.size(); i++) {
                    registerAlarm(listAlarmSet.get(i).getId_pk(), 04, 46, "오늘은 " + listAlarmSet.get(i).getTitle() + " 입니다. \n확인해보세요!");
                    Log.i("register", "등록");
                }


        }
    }

    //alarm
    private void releaseAlarm(int m_pid) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent Intent = new Intent(this, AlarmService.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, m_pid, Intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pIntent);
        pIntent.cancel();

    }

    //  p_id 는 알람 설정 취소 할때 필요 , anniversary_tb에서 ID_PK를 사용하면 될듯
    private void registerAlarm(int P_id, int s_Hour, int s_Minute, String s_StoryName) {

        long triggerTime = 0;
//        long intervalTime = 0;
        long intervalTime = 24 * 60 * 60 * 1000 * 365;// 24시간*365 1년단위

        AlarmManager am = (AlarmManager)this.context.getSystemService(Context.ALARM_SERVICE);


        Intent intent = new Intent(this.context, AlarmService.class);

        Bundle bundle = new Bundle();
        //  데이터 넣는거
//        bundle.putInt("ProjectID", P_id);
//        bundle.putString("AlertText", s_StoryName);
//        bundle.putString("ProjectDAY", set_Days);
//        bundle.putInt("ProjectRingSet", set_rington);
//        bundle.putInt("ProjectRingVibe", set_RingVibe);
        intent.putExtras(bundle);
        PendingIntent sender = PendingIntent.getBroadcast(this.context, P_id, intent, 0);
//
        triggerTime = setTriggerTime(s_Hour, s_Minute);
//        am.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, intervalTime, sender);
        am.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(),AlarmManager.INTERVAL_DAY,sender);


    }

    private long setTriggerTime(int hour, int minutes) {
        // current Time
        long atime = System.currentTimeMillis();
        // timepicker
        Calendar curTime = Calendar.getInstance();
        curTime.set(Calendar.HOUR_OF_DAY, 5);
        curTime.set(Calendar.MINUTE, 1);
        curTime.set(Calendar.SECOND, 0);
        curTime.set(Calendar.MILLISECOND, 0);
        long btime = curTime.getTimeInMillis();
        long triggerTime = btime;
        if (atime > btime)
            triggerTime += 1000 * 60 * 60 * 24;

        return triggerTime;
    }

    //drawer menu
    private void drawerMenuInit() {
        menuHome = (LinearLayout) findViewById(R.id.menu_home_layout);
        menuHome.setOnClickListener(this);
        menuCategory = (LinearLayout) findViewById(R.id.menu_category_layout);
        menuCategory.setOnClickListener(this);
        menuFavorites = (LinearLayout) findViewById(R.id.menu_favorite_layout);
        menuFavorites.setOnClickListener(this);
        menuSetting = (LinearLayout) findViewById(R.id.menu_setting_layout);
        menuSetting.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuHome = null;
        menuCategory = null;
        menuFavorites = null;
        menuSetting = null;
        drawerLayout = null;
        dtToggle = null;

        toolbarMenuButton = null;
        toolbar = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
