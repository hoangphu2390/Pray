package com.kantek.pray.utils;

import android.app.Activity;
import android.content.Intent;

import com.kantek.pray.R;
import com.kantek.pray.data.database.T_Koran;
import com.kantek.pray.define.Constants;
import com.kantek.pray.ui.detail_koran.DetailKoranActivity;
import com.kantek.pray.ui.detail_setting.DetailAlarmActivity;
import com.kantek.pray.ui.detail_setting.ShowDetailAlarmActivity;
import com.kantek.pray.ui.list_koran.ListKoranActivity;
import com.kantek.pray.ui.list_alarm.ListAlarmActivity;
import com.kantek.pray.ui.main.MainActivity;

/**
 * Created by Kiet Nguyen on 12-Dec-16.
 */

public abstract class Navigator {
    public static void openListPrayActivity(Activity activity){
        Intent intent = new Intent(activity, ListKoranActivity.class);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        activity.finish();
        activity.startActivity(intent);
    }

    public static void openListAlarmActivity(Activity activity){
        Intent intent = new Intent(activity, ListAlarmActivity.class);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        activity.finish();
        activity.startActivity(intent);
    }

    public static void openDetailAlarmActivity(Activity activity, T_Koran koran, String tag_name){
        Intent intent = new Intent(activity, DetailAlarmActivity.class);
        intent.putExtra(Constants.KORAN_ENTITY, koran);
        intent.putExtra(Constants.TAG_NAME_CLASS, tag_name);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        activity.startActivity(intent);
    }

    public static void openShowDetailAlarmActivity(Activity activity, T_Koran koran){
        Intent intent = new Intent(activity, ShowDetailAlarmActivity.class);
        intent.putExtra(Constants.KORAN_ENTITY, koran);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        activity.startActivity(intent);
    }

    public static void openDetailKoranActivity(Activity activity, T_Koran t_koran){
        Intent intent = new Intent(activity, DetailKoranActivity.class);
        intent.putExtra(Constants.KORAN_ENTITY, t_koran);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        activity.startActivity(intent);
    }

    public static void openMainActivity(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        activity.finish();
        activity.startActivity(intent);
    }

}
