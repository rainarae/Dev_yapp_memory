package com.yapp.raina.list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yapp.raina.db.DBManager;
import com.yapp.raina.dto.AnniversaryDto;
import com.yapp.raina.memory.R;

import java.util.ArrayList;

/**
 * Created by Raina on 16. 2. 27..
 */
public class MainAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<AnniversaryDto> myData;
    private LayoutInflater inflater;

    private DBManager dbManager;

    public MainAdapter(Context context, int layout, ArrayList<AnniversaryDto> myData) {
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
            convertView = inflater.inflate(R.layout.list_main, viewGroup, false);
        }

        TextView txt_date = (TextView) convertView.findViewById(R.id.txt_main_date);
        TextView txt_title = (TextView) convertView.findViewById(R.id.txt_main_title);

        String[] date = myData.get(pos).getDate_ymd().split("-");
        txt_date.setText(date[1] + "월 " + date[2] + "일");
        txt_title.setText(myData.get(pos).getTitle());

        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.list_main_contents);

        if (myData.get(pos).getCategory().equals("HISTORICAL")) {
            linearLayout.setBackgroundResource(R.drawable.card_unclick_bg_green);
            Log.i("category", myData.get(pos).getCategory());
        } else if (myData.get(pos).getCategory().equals("NATIONAL")) {


            linearLayout.setBackgroundResource(R.drawable.card_unclick_bg_red);
            Log.i("category", myData.get(pos).getCategory());

        } else if (myData.get(pos).getCategory().equals("CHERISH")) {
            linearLayout.setBackgroundResource(R.drawable.card_unclick_bg_ylw);
            Log.i("category", myData.get(pos).getCategory());
        }

        return convertView;
    }
}
