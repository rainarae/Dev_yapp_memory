package com.yapp.raina.memory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yapp.raina.db.DBManager;
import com.yapp.raina.dto.AnniversaryDto;
import com.yapp.raina.list.MainAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DBManager dbManager;

    //drawer menu
    private LinearLayout menuHome, menuCategory, menuFavorites, menuSetting;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle dtToggle;

    private Toolbar toolbar;
    private LinearLayout toolbarMenuButton;

    //upcoming event
    private ArrayList<AnniversaryDto> upcoming_list;

    //date
    private Calendar cal;
    private TextView txt_today;
    private int month;
    private int date;
    private Calendar endDate;
    private ArrayList<Integer> listUpcomingDate;

    //list
    private ListView list;
    private MainAdapter adapter;
    private ArrayList<AnniversaryDto> myList;
    private int pos;

    //dialog
    private TextView txt_dialong_date;
    private TextView txt_dialog_title;
    private TextView txt_dialog_contents;
    private ImageButton btn_dialog_favorite;
    private ImageView btn_detail;

    //viewpager
    private ViewPager mPager;
    private TextView txt_main_month;
    private TextView txt_main_remain;
    private TextView txt_main_date;
    private TextView txt_main_eventname;
    private ImageView img_sliding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);


//        String[] date = upcoming_list.get(0).getDate_ymd().split("-");

        init();
    }

    public void init() {

        dbManager = DBManager.getInstance(this);


        //today's date setting
        txt_today = (TextView) findViewById(R.id.txt_today_date);
        cal = Calendar.getInstance();
        month = cal.get(Calendar.MONTH) + 1;
        date = cal.get(Calendar.DATE);
//        month = 3;
//        date = 1;
        String today = month + "월 ";
        today += date + "일";

        txt_today.setText(today);

        String day;

        if (month < 10) {
            if (date < 10)
                day = "0" + month + "-0" + date;
            else
                day = "0" + month + "-" + date;
        } else {
            if (date < 10)
                day = month + "-0" + date;
            else
                day = month + "-" + date;
        }
        upcoming_list = dbManager.anniversaryDao.selectUpcomingEvent(day);
        listUpcomingDate = new ArrayList<Integer>();
        for (int i = 0; i < upcoming_list.size(); i++) {

            Calendar startDate = Calendar.getInstance();
            endDate = Calendar.getInstance();
            int year = startDate.get(Calendar.YEAR);
            int m = startDate.get(Calendar.MONTH) + 1;
            int d = startDate.get(Calendar.DATE);
//            Log.d("year", year + "");
//            Log.d("m", m + "");
//            Log.d("d", d + "");
            String[] arraydate = upcoming_list.get(i).getDate_ymd().split("-");
//            Log.d("aryear", arraydate[0] + "");
//            Log.d("arm", arraydate[1] + "");
//            Log.d("ard", arraydate[2] + "");
//            startDate.set(Integer.parseInt("2016"), Integer.parseInt("2")-1, Integer.parseInt("26"));
//            endDate.set(Integer.parseInt("2016"), Integer.parseInt("2")-1, Integer.parseInt("28"));

            startDate.set(year, m - 1, d);
            if (m > Integer.parseInt(arraydate[1]))
                endDate.set(startDate.get(Calendar.YEAR) + 1, Integer.parseInt(arraydate[1]) - 1, Integer.parseInt(arraydate[2]));
            else
                endDate.set(startDate.get(Calendar.YEAR), Integer.parseInt(arraydate[1]) - 1, Integer.parseInt(arraydate[2]));

            //시작날짜와 종료날짜 차 만큼 날짜 배경색상 변경할 칸수를 담아두는 변수를 선언하고.
            long a = 0;

            //종료날짜와 시작날짜의 차를 구합니다.
            //두날짜간의 차를 얻으려면 getTimeMills()를 이용하여 천분의 1초 단위로 변환하여야 합니다.
            long b = (endDate.getTimeInMillis() - startDate.getTimeInMillis()) / 1000;
            a = b / (60 * 60 * 24);
            Log.i("INFO", "종료날짜와 시작날짜의 차 : " + a);

            String gap = a + "";
            listUpcomingDate.add(Integer.parseInt(gap));
//            Log.i("gap::", listUpcomingDate.get(i) + "");
        }

        mPager = (ViewPager) findViewById(R.id.pager);
        img_sliding = (ImageView) findViewById(R.id.img_sliding);
//        txt_main_remain.setText("3일");
        PagerAdapterClass madapter = new PagerAdapterClass(getApplicationContext());

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.i("!!yrd","hgfd");
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    txt_main_month = (TextView) findViewById(R.id.txt_main_month1);
                    txt_main_remain = (TextView) findViewById(R.id.txt_main_remain_title1);
                    txt_main_date = (TextView) findViewById(R.id.btn_main_firstevent);
                    txt_main_eventname = (TextView) findViewById(R.id.txt_main_firstevent);


                    txt_main_remain.setText(upcoming_list.get(0).getTitle() + " 까지 " + listUpcomingDate.get(0) + "일 남았습니다.");

                    String[] date = upcoming_list.get(position).getDate_ymd().split("-");
                    if (Integer.parseInt(date[1]) < 10)
                        txt_main_month.setText(date[1].charAt(1) + "월");
                    else
                        txt_main_month.setText(date[1] + "월");
                    txt_main_date.setText(date[2]);
                    txt_main_eventname.setText(upcoming_list.get(position).getTitle());

                    img_sliding.setImageResource(R.drawable.pagemark_01);
                } else if (position == 1) {
                    txt_main_month = (TextView) findViewById(R.id.txt_main_month2);
                    txt_main_remain = (TextView) findViewById(R.id.txt_main_remain_title2);
                    txt_main_date = (TextView) findViewById(R.id.btn_main_secondevent);
                    txt_main_eventname = (TextView) findViewById(R.id.txt_main_secondevent);

                    txt_main_remain.setText(upcoming_list.get(1).getTitle() + " 까지 " + listUpcomingDate.get(1) + "일 남았습니다.");

                    String[] date = upcoming_list.get(position).getDate_ymd().split("-");
                    if (Integer.parseInt(date[1]) < 10)
                        txt_main_month.setText(date[1].charAt(1) + "월");
                    else
                        txt_main_month.setText(date[1] + "월");
                    txt_main_date.setText(date[2]);
                    txt_main_eventname.setText(upcoming_list.get(position).getTitle());

                    img_sliding.setImageResource(R.drawable.pagemark_02);
                } else if (position == 2) {
                    txt_main_month = (TextView) findViewById(R.id.txt_main_month3);
                    txt_main_remain = (TextView) findViewById(R.id.txt_main_remain_title3);
                    txt_main_date = (TextView) findViewById(R.id.btn_main_thirdevent);
                    txt_main_eventname = (TextView) findViewById(R.id.txt_main_thirdevent);

                    txt_main_remain.setText(upcoming_list.get(2).getTitle() + " 까지 " + listUpcomingDate.get(2) + "일 남았습니다.");

                    String[] date = upcoming_list.get(position).getDate_ymd().split("-");
                    if (Integer.parseInt(date[1]) < 10)
                        txt_main_month.setText(date[1].charAt(1) + "월");
                    else
                        txt_main_month.setText(date[1] + "월");
                    txt_main_date.setText(date[2]);
                    txt_main_eventname.setText(upcoming_list.get(position).getTitle());

                    img_sliding.setImageResource(R.drawable.pagemark_03);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPager.setAdapter(madapter);


        list = (ListView) findViewById(R.id.listview_main);


        myList = dbManager.anniversaryDao.selectByMonth(cal.get(Calendar.MONTH) + 1);
//        myList = dbManager.anniversaryDao.selectByMonth(3);
        adapter = new MainAdapter(this, R.layout.list_main, myList);


        toolbarInit();
        drawerMenuInit();
        list.setOnItemClickListener(listener);
        list.setAdapter(adapter);
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

        toolbar.setTitle("memory");
        toolbar.getMenu().clear();

        toolbarMenuButton = (LinearLayout) findViewById(R.id.layout_menu_btn);
        toolbarMenuButton.setOnClickListener(this);

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

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            pos = position;
            final AnniversaryDto data = myList.get(position);
            final LinearLayout linear = (LinearLayout) View.inflate(MainActivity.this, R.layout.dialog_abstract, null
            );
            txt_dialong_date = (TextView) linear.findViewById(R.id.txt_abstract_date);
            txt_dialog_title = (TextView) linear.findViewById(R.id.txt_abstract_title);
            txt_dialog_contents = (TextView) linear.findViewById(R.id.txt_abstract_contents);
            btn_detail = (ImageView)linear.findViewById(R.id.btn_detail_toward);
            btn_dialog_favorite = (ImageButton) linear.findViewById(R.id.btn_dialog_favorites);
            btn_dialog_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.getBookmark_st()) {
                        dbManager.anniversaryDao.updateBookmark(data);
                        btn_dialog_favorite.setImageResource(R.drawable.bookmark_btn_unclick);
                        data.setBookmark_st(false);
                    } else {
                        dbManager.anniversaryDao.updateBookmark(data);
                        data.setBookmark_st(true);
                        btn_dialog_favorite.setImageResource(R.drawable.bookmark_btn_click);
                        Toast.makeText(MainActivity.this, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    }


                }
            });

            String[] date = data.getDate_ymd().split("-");
            txt_dialong_date.setText(date[1] + "월 " + date[2] + "일");
            txt_dialog_title.setText(data.getTitle());
            txt_dialog_contents.setText(data.getAbstract_());

            if (data.getBookmark_st())
                btn_dialog_favorite.setImageResource(R.drawable.bookmark_btn_click);
            else
                btn_dialog_favorite.setImageResource(R.drawable.bookmark_btn_unclick);


            final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setView(linear);
            btn_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, DetailContentsActivity.class);
                    i.putExtra("Datadto", myList.get(pos));
                    startActivity(i);
                }
            });
            dialog.show();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();


//        System.out.println(date[0]+ ".." + date[1]);
//        txt_main_month.setText(date[1]);
//        txt_main_date.setText(date[2]);
//        txt_main_eventname.setText(upcoming_list.get(0).getTitle());
//        txt_main_remain.setText("3일 남았습니다.");

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


    //view paser
    private void setCurrentInflateItem(int type) {
        if (type == 0) {
            mPager.setCurrentItem(0);
        } else if (type == 1) {
            mPager.setCurrentItem(1);
        } else {
            mPager.setCurrentItem(2);
        }
    }

    /**
     * PagerAdapter
     */
    private class PagerAdapterClass extends PagerAdapter implements ViewPager.OnPageChangeListener {

        private LayoutInflater mInflater;

        public PagerAdapterClass(Context c) {
            super();
            mInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public void finishUpdate(ViewGroup container) {

            super.finishUpdate(container);


        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);

        }


        @Override
        public Object instantiateItem(View pager, int position) {
            View v = null;
            if (position == 0) {
                v = mInflater.inflate(R.layout.inflate_firstevent, null);
                String[] date = upcoming_list.get(0).getDate_ymd().split("-");
                if (Integer.parseInt(date[1]) < 10)
                    ((TextView) v.findViewById(R.id.txt_main_month1)).setText(date[1].charAt(1) + "월");
                else
                    ((TextView) v.findViewById(R.id.txt_main_month1)).setText(date[1] + "월");
                ((TextView) v.findViewById(R.id.btn_main_firstevent)).setText(date[2]);
                ((TextView) v.findViewById(R.id.txt_main_remain_title1)).setText(upcoming_list.get(0).getTitle() + " 까지 " + listUpcomingDate.get(0) + "일 남았습니다.");
                ((TextView) v.findViewById(R.id.txt_main_firstevent)).setText(upcoming_list.get(0).getTitle());
                v.findViewById(R.id.btn_main_firstevent).setOnClickListener(upcomingListener);
                v.findViewById(R.id.iv_one);
            } else if (position == 1) {
                v = mInflater.inflate(R.layout.inflate_secondevent, null);
                v.findViewById(R.id.btn_main_secondevent).setOnClickListener(upcomingListener);
                v.findViewById(R.id.iv_one);
            } else {
                v = mInflater.inflate(R.layout.inflate_thirdevent, null);
                v.findViewById(R.id.btn_main_thirdevent).setOnClickListener(upcomingListener);
                v.findViewById(R.id.iv_one);
            }

            ((ViewPager) pager).addView(v, 0);

            return v;
        }
        int position = 0;
        View.OnClickListener upcomingListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.btn_main_firstevent:
                        position = 0;
                        break;
                    case R.id.btn_main_secondevent:
                        position = 1;
                        break;
                    case R.id.btn_main_thirdevent:
                        position = 2;
                        break;
                }
                final AnniversaryDto data = upcoming_list.get(position);
                final LinearLayout linear = (LinearLayout) View.inflate(MainActivity.this, R.layout.dialog_abstract, null
                );
                txt_dialong_date = (TextView) linear.findViewById(R.id.txt_abstract_date);
                txt_dialog_title = (TextView) linear.findViewById(R.id.txt_abstract_title);
                txt_dialog_contents = (TextView) linear.findViewById(R.id.txt_abstract_contents);
                btn_dialog_favorite = (ImageButton) linear.findViewById(R.id.btn_dialog_favorites);
                btn_detail = (ImageView) linear.findViewById(R.id.btn_detail_toward);
                btn_dialog_favorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (data.getBookmark_st()) {
                            dbManager.anniversaryDao.updateBookmark(data);
                            btn_dialog_favorite.setImageResource(R.drawable.bookmark_btn_unclick);
                            data.setBookmark_st(false);
                        } else {
                            dbManager.anniversaryDao.updateBookmark(data);
                            data.setBookmark_st(true);
                            btn_dialog_favorite.setImageResource(R.drawable.bookmark_btn_click);
                            Toast.makeText(MainActivity.this, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                String[] date = data.getDate_ymd().split("-");
                txt_dialong_date.setText(date[1] + "월 " + date[2] + "일");
                txt_dialog_title.setText(data.getTitle());
                txt_dialog_contents.setText(data.getAbstract_());

                if (data.getBookmark_st())
                    btn_dialog_favorite.setImageResource(R.drawable.bookmark_btn_click);
                else
                    btn_dialog_favorite.setImageResource(R.drawable.bookmark_btn_unclick);


                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setView(linear);

                btn_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, DetailContentsActivity.class);
                        Log.i("logggggg", upcoming_list.get(position).toString());
                        i.putExtra("Datadto", upcoming_list.get(position));
                        startActivity(i);
                    }
                });
                dialog.show();
            }
        };

        @Override
        public void destroyItem(View pager, int position, Object view) {
            ((ViewPager) pager).removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View pager, Object obj) {
            return pager == obj;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
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
