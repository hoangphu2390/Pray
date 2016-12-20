package com.kantek.pray.ui.detail_setting.dialog;

import android.widget.Button;
import android.widget.TimePicker;

import com.kantek.pray.R;
import com.kantek.pray.base.BaseFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Kiet Nguyen on 15-Dec-16.
 */

public class SettingTimeDialog extends BaseFragment{

    @Bind(R.id.alarmTimePicker)
    TimePicker alarmTimePicker;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_setting_time;
    }

    @Override
    protected void initComponent() {

    }

    @OnClick(R.id.start_alarm)
    public void onClickSetAlarm(){
        detailAlarmActivity.showDialog(null);
    }
}
