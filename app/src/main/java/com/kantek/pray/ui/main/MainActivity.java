package com.kantek.pray.ui.main;

import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Handler;
import android.widget.Toast;

import com.kantek.pray.R;
import com.kantek.pray.base.BaseActivity;
import com.kantek.pray.data.database.DataMapper;
import com.kantek.pray.data.database.T_Koran;
import com.kantek.pray.define.Constants;
import com.kantek.pray.utils.Navigator;
import com.kantek.pray.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;

/**
 * Created by Kiet Nguyen on 12-Dec-16.
 */

public class MainActivity extends BaseActivity{

    private boolean doubleBackToExitPressedOnce;
    public static List<String> listRingTone = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent() {
        createDataDummy();
        getListRingTone();
    }

    private void createDataDummy() {
        T_Koran koran = new T_Koran();
        koran.koran_id = "1";
        koran.title = "1. Lorem ipsum dolor sit amet";
        koran.content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur feugiat erat felis, a rhoncus elit accumsan non. Suspendisse auctor tellus at suscipit viverra. In rhoncus tellus et sem porta fermentum. Quisque ut quam a purus scelerisque sodales. ";
        koran.tap_count = 0;
        koran.tap_total = 0;
        koran.date = "";
        koran.time = "Not yet";
        koran.is_repeat = 0;
        koran.sound = "Not yet";
        koran.is_enable = 0;
        koran.description = "";
        koran.path_sound = "";
        DataMapper.saveInfo_Koran(koran);

        koran = new T_Koran();
        koran.koran_id = "2";
        koran.title = "2. Lorem ipsum dolor sit amet";
        koran.content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur feugiat erat felis, a rhoncus elit accumsan non. Suspendisse auctor tellus at suscipit viverra. In rhoncus tellus et sem porta fermentum. Quisque ut quam a purus scelerisque sodales. ";
        koran.tap_count = 0;
        koran.tap_total = 0;
        koran.date = "";
        koran.time = "Not yet";
        koran.is_repeat = 0;
        koran.sound = "Not yet";
        koran.is_enable = 0;
        koran.description = "";
        koran.path_sound = "";
        DataMapper.saveInfo_Koran(koran);

        koran = new T_Koran();
        koran.koran_id = "3";
        koran.title = "3. Lorem ipsum dolor sit amet";
        koran.content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur feugiat erat felis, a rhoncus elit accumsan non. Suspendisse auctor tellus at suscipit viverra. In rhoncus tellus et sem porta fermentum. Quisque ut quam a purus scelerisque sodales. ";
        koran.tap_count = 0;
        koran.tap_total = 0;
        koran.date = "";
        koran.time = "Not yet";
        koran.is_repeat = 0;
        koran.sound = "Not yet";
        koran.is_enable = 0;
        koran.description = "";
        koran.path_sound = "";
        DataMapper.saveInfo_Koran(koran);
    }

    public void getListRingTone() {
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_RINGTONE | RingtoneManager.TYPE_NOTIFICATION);
        Cursor cursor = manager.getCursor();

        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            listRingTone.add(notificationTitle);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @OnClick(R.id.menu_item_list)
    public void onClickItemList(){
        Navigator.openListPrayActivity(MainActivity.this);
    }

    @OnClick(R.id.menu_item_alarm)
    public void onClickItemAlarm(){
        Navigator.openListAlarmActivity(MainActivity.this);
    }
}
