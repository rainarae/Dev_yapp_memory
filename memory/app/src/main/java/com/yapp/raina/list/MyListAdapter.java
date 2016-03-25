package com.yapp.raina.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
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
        ImageButton btn_favorites = (ImageButton) convertView.findViewById(R.id.btn_favorites);

        txt_date.setText(myData.get(pos).getDate_ymd());
        txt_title.setText(myData.get(pos).getTitle());

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
