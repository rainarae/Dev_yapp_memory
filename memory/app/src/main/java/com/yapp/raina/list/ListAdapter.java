package com.yapp.raina.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yapp.raina.memory.R;

import java.util.ArrayList;

/**
 * Created by SAMSUNG-PC on 2016-02-27.
 */
public class ListAdapter extends BaseAdapter {
   Context mainContext;
    ArrayList<ListData> myList;
    LayoutInflater inflater;
    int layout;

    public ListAdapter(Context mContext, int alayout, ArrayList<ListData> listitem) {
        mainContext = mContext;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myList = listitem;
        layout = alayout;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position).date;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;

        if(convertView == null) {
            convertView = inflater.inflate(layout,parent,false);
        }

        TextView dateTxt = (TextView)convertView.findViewById(R.id.date);
        dateTxt.setText(myList.get(position).date);

        TextView eventTxt = (TextView)convertView.findViewById(R.id.event);
        eventTxt.setText(myList.get(position).event);

        TextView lunarTxt = (TextView)convertView.findViewById(R.id.lunar);
        lunarTxt.setText(myList.get(position).lunar);

        return convertView;
    }
}