package com.yapp.raina.memory;

import android.content.Intent;
import android.opengl.GLES20;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.yapp.raina.db.DBManager;
import com.yapp.raina.dto.AnniversaryDto;
import com.yapp.raina.list.MyListAdapter;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity implements View.OnClickListener {
    //drawer menu
    private LinearLayout menuHome, menuCategory, menuFavorites, menuSetting;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle dtToggle;

    private Toolbar toolbar;
    private LinearLayout toolbarMenuButton;

    //list
    private MyListAdapter adapter;
    private ArrayList<AnniversaryDto> myList;
    private ListView list;
    private ImageButton btn_unfavorite;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        list = (ListView) findViewById(R.id.listview_favorites);
        btn_unfavorite = (ImageButton) findViewById(R.id.btn_favorites);

        dbManager = DBManager.getInstance(this);
        myList = dbManager.anniversaryDao.selectFavoriteList();
        adapter = new MyListAdapter(this, R.layout.list_favorites, myList);

        toolbarInit();
        drawerMenuInit();
        list.setAdapter(adapter);
//        list.setOnItemClickListener(itemListener);
        int[] maxTextureSize = new int[1];
        GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, maxTextureSize, 0);
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
