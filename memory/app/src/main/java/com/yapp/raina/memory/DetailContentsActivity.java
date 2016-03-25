package com.yapp.raina.memory;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yapp.raina.db.DBManager;
import com.yapp.raina.dto.AnniversaryDto;

public class DetailContentsActivity extends AppCompatActivity implements View.OnClickListener {

    //drawer menu
    private LinearLayout menuHome, menuCategory, menuFavorites, menuSetting;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle dtToggle;

    private Toolbar toolbar;
    private LinearLayout toolbarMenuButton;

    //intent data
    private Intent intent;
    private AnniversaryDto dto;

    //data
    private ImageView img_contents_logo;
    private TextView txt_detail_title;
    private TextView txt_detail_date;
    private TextView txt_detail_abstract;
    private TextView txt_detail_contents;
    private TextView txt_detail_reference;
    private TextView txt_detail_purple_title;
    private Button btn_detail_alarm;

    //DBmanager
    private DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contents);

        intent = getIntent();
        dto = (AnniversaryDto) intent.getSerializableExtra("Datadto");

        init();
        toolbarInit();
        drawerMenuInit();

        Log.d("Intent dto", dto.toString());
    }

    public void init() {

        dbManager = DBManager.getInstance(this);

        img_contents_logo = (ImageView) findViewById(R.id.img_detail_logo);
        txt_detail_title = (TextView)findViewById(R.id.txt_detail_title);
        txt_detail_purple_title = (TextView)findViewById(R.id.txt_detail_purple_title);
        txt_detail_date = (TextView)findViewById(R.id.txt_detail_date);
        txt_detail_abstract = (TextView)findViewById(R.id.txt_detail_abstract);
        txt_detail_contents = (TextView)findViewById(R.id.txt_detail_contents);
        txt_detail_reference = (TextView)findViewById(R.id.txt_detail_reference);
        btn_detail_alarm = (Button)findViewById(R.id.btn_detail_alarm);

        if(dto.getAlarm_st()){
            btn_detail_alarm.setBackgroundResource(R.mipmap.alarm_on);
        }
        else{
            btn_detail_alarm.setBackgroundResource(R.mipmap.alarm_off);
        }

        txt_detail_title.setText(dto.getTitle());
        txt_detail_purple_title.setText(dto.getTitle());
        String[] date = dto.getDate_ymd().split("-");
        if (Integer.parseInt(date[1]) < 10)
            date[1] = date[1].charAt(1) + "";
        if (Integer.parseInt(date[2]) < 10)
            date[2] = date[2].charAt(1) + "";
        txt_detail_date.setText(date[0] + "년 " + date[1] + "월 " + date[2] + "일");
        txt_detail_abstract.setText(dto.getAbstract_());
        txt_detail_contents.setText(dto.getContent());
        txt_detail_reference.setText(dto.getReference());

        if (dto.getCategory().equals("NATIONAL"))
            img_contents_logo.setImageResource(R.mipmap.detail_icon_01);
        else if (dto.getCategory().equals("HISTORICAL"))
            img_contents_logo.setImageResource(R.mipmap.detail_icon02);
        else if (dto.getCategory().equals("CHERISH"))
            img_contents_logo.setImageResource(R.mipmap.detail_icon03);


        btn_detail_alarm.setOnClickListener(alarmListener);
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


    View.OnClickListener alarmListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            dbManager.anniversaryDao.updateAlarmByPid(DetailContentsActivity.this, dto);

            if (dto.getAlarm_st()){
                btn_detail_alarm.setBackgroundResource(R.mipmap.alarm_off);
                dto.setAlarm_st(false);
            }
            else {
                btn_detail_alarm.setBackgroundResource(R.mipmap.alarm_on);
                dto.setAlarm_st(true);
            }

        }
    };

    @Override
    public void onClick(View view) {
        if (view == menuHome) {
            Intent i = new Intent(this, MainActivity.class);
            drawerLayout.closeDrawers();
            startActivity(i);
            finish();
        } else if (view == menuCategory) {
            Intent i = new Intent(this, CategoryActivity.class);
            drawerLayout.closeDrawers();
            startActivity(i);
            finish();
        } else if (view == menuFavorites) {
            Intent i = new Intent(this, FavoritesActivity.class);
            drawerLayout.closeDrawers();
            startActivity(i);
            finish();
        } else if (view == menuSetting) {
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
