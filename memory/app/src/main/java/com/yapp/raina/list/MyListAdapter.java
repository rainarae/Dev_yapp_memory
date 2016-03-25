package com.yapp.raina.list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yapp.raina.db.DBManager;
import com.yapp.raina.dto.AnniversaryDto;
import com.yapp.raina.memory.R;

import java.util.ArrayList;

/**
 * Created by Raina on 16. 2. 27..
 */
public class MyListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<AnniversaryDto> myData;
    private LayoutInflater inflater;

    private DBManager dbManager;

    public MyListAdapter(Context context, int layout, ArrayList<AnniversaryDto> myData) {
        this.context = context;
        this.layout = layout;
        this.myData = myData;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myData.size();
    }

    @Override
    public Object getItem(int i) {
        return myData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final int pos = position;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_favorites, viewGroup, false);
        }

        TextView txt_date = (TextView) convertView.findViewById(R.id.txt_favorites_date);
        TextView txt_title = (TextView) convertView.findViewById(R.id.txt_favorites_title);
        Button btn_favorites = (Button) convertView.findViewById(R.id.btn_favorites);

        txt_date.setText(myData.get(pos).getDate_ymd());
        txt_title.setText(myData.get(pos).getTitle());

        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.ly_favorites_list);

        if (myData.get(pos).getCategory().equals("HISTORICAL")) {
            linearLayout.setBackgroundResource(R.mipmap.list_bg_unclick_green);
            Log.i("category", myData.get(pos).getCategory());
        } else if (myData.get(pos).getCategory().equals("NATIONAL")) {


            linearLayout.setBackgroundResource(R.mipmap.list_bg_unclick_red);
            Log.i("category", myData.get(pos).getCategory());

        } else if (myData.get(pos).getCategory().equals("CHERISH")) {
            linearLayout.setBackgroundResource(R.mipmap.list_bg_unclick_ylw);
            Log.i("category", myData.get(pos).getCategory());
        }

        btn_favorites.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dbManager = DBManager.getInstance(context);
                dbManager.anniversaryDao.updateBookmark(myData.get(pos));
                myData.remove(pos);
                Toast.makeText(context, "즐겨찾기가 해제되었습니다.", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();

            }
        });

        return convertView;
    }
}
