package com.kantek.pray.ui.list_alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.kantek.pray.R;
import com.kantek.pray.base.BaseActivity;
import com.kantek.pray.data.database.DataMapper;
import com.kantek.pray.data.database.T_Koran;
import com.kantek.pray.define.Constants;
import com.kantek.pray.services.AlarmReceiver;
import com.kantek.pray.ui.detail_setting.DetailAlarmActivity;
import com.kantek.pray.ui.detail_setting.ShowDetailAlarmActivity;
import com.kantek.pray.utils.Navigator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Kiet Nguyen on 12-Dec-16.
 */

public class ListAlarmActivity extends BaseActivity implements ListAlarmAdapter.SelectedItem {

    @Bind(R.id.recycler_setting)
    RecyclerView recycler_setting;

    private ListAlarmAdapter adapter;
    private List<T_Koran> koranList;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_list_setting_alarm;
    }

    @Override
    protected void initComponent() {
        setupRecyclerView();
        showContentUI();
    }

    public void setupRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_setting.setLayoutManager(llm);
        adapter = new ListAlarmAdapter(getLayoutInflater(), this, this);
        recycler_setting.setAdapter(adapter);
    }


    public void showContentUI() {
        koranList = new ArrayList<>();
        List<T_Koran> korans = DataMapper.getList_InfoKoran();
        if (korans == null || korans.size() == 0) return;
        filterTimer(korans);
        sortByTimer();
        adapter.setDataSource(koranList);
        adapter.notifyDataSetChanged();
    }

    private void filterTimer(List<T_Koran> korans) {
        for (T_Koran t_koran : korans) {
            if (!t_koran.time.equals(Constants.NOT_YET)) {
                koranList.add(t_koran);
            }
        }
    }

    private void sortByTimer() {
        Collections.sort(koranList, new Comparator<T_Koran>() {
            @Override
            public int compare(T_Koran t_koran1, T_Koran t_koran2) {
                return t_koran1.time.compareTo(t_koran2.time);
            }
        });
    }

    private void saveAlarmManager(T_Koran koran) {
        Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Uri uri = null;
        myIntent.putExtra("extra", "no");
        myIntent.putExtra(Constants.KORAN_ENTITY, koran);
        myIntent.putExtra(Constants.URI, uri);
        PendingIntent pending_intent = PendingIntent.getBroadcast(ListAlarmActivity.this, Integer.parseInt(koran.koran_id),
                myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        sendBroadcast(myIntent);
        alarmManager.cancel(pending_intent);
    }

    @Override
    public void selectedInfidelity(T_Koran koran) {
        Navigator.openDetailAlarmActivity(ListAlarmActivity.this, koran, Constants.LIST_ALARM_SELECTED);
    }

    @Override
    public void disableAlarmSetting(T_Koran koran) {
        saveAlarmManager(koran);
    }

    @OnClick(R.id.btnBack)
    public void onClickBack() {
        Navigator.openMainActivity(ListAlarmActivity.this);
    }

    @OnClick(R.id.icon_back_setting)
    public void onClickIconBack() {
        Navigator.openMainActivity(ListAlarmActivity.this);
    }


    @OnClick(R.id.btnEdit)
    public void onClickEdit() {

    }

    @OnClick(R.id.icon_setting)
    public void onClickIconEdit() {

    }

    @OnClick(R.id.btn_add_alarm)
    public void onClickAddAlarm() {
        Navigator.openDetailAlarmActivity(ListAlarmActivity.this, null, Constants.LIST_ALARM);
    }

    @Override
    public void onBackPressed() {
        Navigator.openMainActivity(ListAlarmActivity.this);
    }
}
