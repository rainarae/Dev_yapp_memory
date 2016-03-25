package com.yapp.raina.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yapp.raina.db.DBManager;
import com.yapp.raina.dto.AnniversaryDto;
import com.yapp.raina.list.ListAdapter;
import com.yapp.raina.list.ListData;
import com.yapp.raina.list.MainAdapter;
import com.yapp.raina.memory.R;

import java.util.ArrayList;


public class MainTabFragment3 extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainTabFragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainTabFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static MainTabFragment3 newInstance(String param1, String param2) {
        MainTabFragment3 fragment = new MainTabFragment3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    ArrayList<ListData> listItem;
    ListView MyList;
    ListAdapter MyAdapter;


    private ArrayList<AnniversaryDto> list;
    private DBManager dbManager;
    private MainAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbManager = DBManager.getInstance(getActivity());


        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_main_tab_fragment3, container, false);

        list = dbManager.anniversaryDao.selectListByCategory("CHERISH");
        adapter = new MainAdapter(getActivity(), R.layout.list_main, list);

//        listItem = new ArrayList<ListData>();
//
//        listItem.add(new ListData("0000-00-00", "Updating....", "0-0"));
//        listItem.add(new ListData("0000-00-00", "Updating..", "0-0"));
//        MyAdapter =  new ListAdapter(getActivity(), R.layout.list_item, listItem);
        MyList = (ListView) convertView.findViewById(R.id.cherishlist);
        MyList.setAdapter(adapter);

        return convertView;
    }
}
