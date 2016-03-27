package com.yapp.raina.memory;

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
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.yapp.raina.db.DBManager;
import com.yapp.raina.dto.AnniversaryDto;
import com.yapp.raina.shared.AlarmAllocation;
import com.yapp.raina.shared.SharedData;

import java.util.ArrayList;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    //drawer menu
    private LinearLayout menuHome, menuCategory, menuFavorites, menuSetting;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle dtToggle;

    private Toolbar toolbar;
    private LinearLayout toolbarMenuButton;

    //all_alarm_on/off
    private Switch btn_all_alarm;
    //알람설정을위한 전체 리스트업
    private ArrayList<AnniversaryDto> alllist;

    //partial alarm setting
    private ImageButton btn_natinal;
    private ImageButton btn_historical;
    private ImageButton btn_cherish;

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
        context = this;
        dbManager = DBManager.getInstance(context);

        btn_natinal = (ImageButton)findViewById(R.id.btn_alarm_natinal);
        btn_historical = (ImageButton)findViewById(R.id.btn_alarm_historical);
        btn_cherish = (ImageButton)findViewById(R.id.btn_alarm_cherish);

        if(SharedData.alarm_national){
            btn_natinal.setImageResource(R.mipmap.checkbox_02);
        }
        else{
            btn_natinal.setImageResource(R.mipmap.checkbox_01);
        }
        if(SharedData.alarm_historical){
            btn_historical.setImageResource(R.mipmap.checkbox_02);
        }
        else{
            btn_historical.setImageResource(R.mipmap.checkbox_01);
        }
        if(SharedData.alarm_cherish){
            btn_cherish.setImageResource(R.mipmap.checkbox_02);
        }
        else{
            btn_cherish.setImageResource(R.mipmap.checkbox_01);
        }

        btn_all_alarm = (Switch)findViewById(R.id.btn_switch_all_alarm);
        if(SharedData.all_alarm){
            btn_all_alarm.setChecked(true);
        }
        else{
            btn_all_alarm.setChecked(false);
        }


        //전체알람설정 | 해제
        btn_all_alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedData.all_alarm = isChecked;
                alllist = dbManager.anniversaryDao.updateAllAlarm(isChecked);

                //알람을 전체설정할때!
                if(isChecked){
                    for(AnniversaryDto list : alllist){
                        AlarmAllocation.registerAlarm(AlarmActivity.this, list);
                    }
                    Log.d("switch: ", "ischecked! now !");
                }
                //알람을 전체끌때!
                else{
                    for(AnniversaryDto list : alllist){
                        AlarmAllocation.releaseAlarm(AlarmActivity.this, list);
                    }
                    Log.d("switch: ", "unchecked! now !");
                }

            }
        });

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
            Intent i = new Intent(this, CategoryActivity.class);
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

//    //all_alarm_setting
//    public void mOnClick(View v){
//        btn_all_alarm = (Switch)findViewById(R.id.btn_switch_all_alarm);
//
//        switch (v.getId()){
//            case R.id.btn_switch_all_alarm:
//                if (btn_all_alarm.isChecked()){
//
//                }
//                break;
//        }
//    }

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
