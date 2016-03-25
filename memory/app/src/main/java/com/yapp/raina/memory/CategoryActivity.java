package com.yapp.raina.memory;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.yapp.raina.fragment.MainTabFragment1;
import com.yapp.raina.fragment.MainTabFragment2;
import com.yapp.raina.fragment.MainTabFragment3;
import com.yapp.raina.fragment.MainTabFragment4;
import com.yapp.raina.list.TabsAdapter;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    //drawer menu
    private LinearLayout menuHome, menuCategory, menuFavorites, menuSetting;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle dtToggle;

    private Toolbar toolbar;
    private LinearLayout toolbarMenuButton;

    private TabHost tabHost;
    private ViewPager pager;
    private TabsAdapter mAdapter;

    public static final String TAB_INDEX_ON_SAVEDINST = "tabindex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        toolbarInit();
        drawerMenuInit();

        // STEP3> Setup View Pager
        {
            tabHost = (TabHost) findViewById(R.id.tabHost);
            tabHost.setup(); // setup the tabhost.
            pager = (ViewPager) findViewById(R.id.pager);

            mAdapter = new TabsAdapter(this, getSupportFragmentManager(), tabHost, pager);
            addFragmentToPager(savedInstanceState);
        }

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


    private void addFragmentToPager(Bundle savedInstanceState) {
        Resources res = getResources();

        ArrayList<View> tab_Indicator = new ArrayList<>();
        View temp;

        temp = LayoutInflater.from(this).inflate(R.layout.tab_icon, null);
        ((TextView) temp.findViewById(R.id.tab_title)).setTextColor(Color.BLACK);
        ((TextView) temp.findViewById(R.id.tab_title)).setText("공휴일");
        tab_Indicator.add(temp);

        temp = LayoutInflater.from(this).inflate(R.layout.tab_icon, null);
        ((TextView) temp.findViewById(R.id.tab_title)).setTextColor(Color.BLACK);
        ((TextView) temp.findViewById(R.id.tab_title)).setText("우리의 기억");
        tab_Indicator.add(temp);

        temp = LayoutInflater.from(this).inflate(R.layout.tab_icon, null);
        ((TextView) temp.findViewById(R.id.tab_title)).setTextColor(Color.BLACK);
        ((TextView) temp.findViewById(R.id.tab_title)).setText("어떤이의 기억");
        tab_Indicator.add(temp);

        temp = LayoutInflater.from(this).inflate(R.layout.tab_icon, null);
        ((TextView) temp.findViewById(R.id.tab_title)).setTextColor(Color.BLACK);
        ((TextView) temp.findViewById(R.id.tab_title)).setText("내 기억");
        tab_Indicator.add(temp);

        mAdapter.addTab(tabHost.newTabSpec("first").setIndicator(tab_Indicator.get(0)),
                MainTabFragment1.class, null);

        mAdapter.addTab(tabHost.newTabSpec("second").setIndicator(tab_Indicator.get(1)),
                MainTabFragment2.class, null);

        mAdapter.addTab(tabHost.newTabSpec("third").setIndicator(tab_Indicator.get(2)),
                MainTabFragment3.class, null);

        mAdapter.addTab(tabHost.newTabSpec("fourth").setIndicator(tab_Indicator.get(3)),
                MainTabFragment4.class, null);

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);
        }


        if (savedInstanceState != null) {
            tabHost.setCurrentTab(savedInstanceState.getInt(TAB_INDEX_ON_SAVEDINST));
            mAdapter.onRestoreInstanceState(savedInstanceState);
        }
    }
}
