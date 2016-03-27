package com.yapp.raina.shared;

import com.yapp.raina.dto.AnniversaryDto;

import java.util.ArrayList;

/**
 * Created by Raina on 16. 3. 25..
 */
public class SharedData {
    public static ArrayList<AnniversaryDto> monthList;
    public static ArrayList<AnniversaryDto> UpcomingList;
    public static ArrayList<Integer> upComingListDateCalculate;
    public static int month = 0;
    public static String today = "";

    public static boolean all_alarm = false;
    public static boolean alarm_national = false;
    public static boolean alarm_historical = false;
    public static boolean alarm_cherish = false;
}