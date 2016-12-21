package com.kantek.pray.ui.detail_setting;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kantek.pray.R;
import com.kantek.pray.base.BaseActivity;
import com.kantek.pray.data.database.T_Koran;
import com.kantek.pray.define.Constants;
import com.kantek.pray.services.AlarmReceiver;
import com.kantek.pray.utils.Navigator;
import com.kantek.pray.utils.Utils;
import com.kantek.pray.utils.WakeLocker;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Kiet Nguyen on 12-Dec-16.
 */

public class ShowDetailAlarmActivity extends BaseActivity {

    @Bind(R.id.tv_view)
    TextView tv_view;
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_blessed)
    TextView tv_blessed;

    private T_Koran koran;

    @Override
    protected void initComponent() {
        getData();
    }

    private void getData() {
        try {
            if (getIntent().getExtras().containsKey(Constants.KORAN_ENTITY)) {
                koran = (T_Koran) getIntent().getExtras().getSerializable(Constants.KORAN_ENTITY);
                if (koran == null) return;
                if (koran.time != null && Utils.formatTimeTo24hours(koran.time) != null)
                    tv_time.setText(Utils.formatTimeTo24hours(koran.time));
                if (koran.title != null)
                    tv_title.setText(koran.title);
                tv_blessed.setText(koran.tap_count > 0 ? getString(R.string.text_state_blessed) : getString(R.string.text_state_not_bless));
            }
        }catch (Exception ex){}

        String text = tv_view.getText().toString();
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        tv_view.setText(content);
    }

    @OnClick(R.id.tv_view)
    public void onClickViewDetail() {
        setUpMute();
        Navigator.openDetailKoranActivity(this, koran);
    }



    @OnClick(R.id.tv_mute)
    public void onClickMute() {
        setUpMute();
    }

    private void setUpMute(){
        WakeLocker.release();

        Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Uri uri = null;
        myIntent.putExtra("extra", "no");
        myIntent.putExtra(Constants.KORAN_ENTITY, koran);
        myIntent.putExtra(Constants.URI, uri);
        PendingIntent pending_intent = PendingIntent.getBroadcast(ShowDetailAlarmActivity.this, 0,
                myIntent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 3, pending_intent);
    }

    @OnClick(R.id.btn_close)
    public void onClickClose(){
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_detail_setting_alarm;
    }

}
