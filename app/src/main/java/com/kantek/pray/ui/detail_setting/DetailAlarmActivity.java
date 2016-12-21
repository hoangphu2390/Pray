package com.kantek.pray.ui.detail_setting;


import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kantek.pray.R;
import com.kantek.pray.data.database.DataMapper;
import com.kantek.pray.data.database.T_Koran;
import com.kantek.pray.define.Constants;
import com.kantek.pray.listener.SettingTitleListener;
import com.kantek.pray.services.AlarmReceiver;
import com.kantek.pray.ui.detail_setting.dialog.SettingTitleDialog;
import com.kantek.pray.utils.Navigator;
import com.kantek.pray.utils.Utils;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kiet Nguyen on 13-Dec-16.
 */

public class DetailAlarmActivity extends FragmentActivity implements SettingTitleListener {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    private T_Koran koran;
    private TextView tv_hour, tv_minute, tv_title, tv_sound;
    private SwitchCompat switchRepeat;
    private final int RINGTONEPICKER = 1;
    private int isRepeat;
    private boolean isCreateAlarm;
    private String tag_name;
    private Uri intent_uri;
    private LinearLayout btnSaveAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_setting_time);
        ButterKnife.bind(this);
        tv_hour = (TextView) findViewById(R.id.tv_hour);
        tv_minute = (TextView) findViewById(R.id.tv_minute);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_sound = (TextView) findViewById(R.id.tv_sound);
        switchRepeat = (SwitchCompat) findViewById(R.id.switchRepeat);
        btnSaveAlarm = (LinearLayout) findViewById(R.id.btnSaveAlarm);

        isCreateAlarm = false;
        isRepeat = 0;
        intent_uri = null;
        getData();

        switchRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRepeat == 1)
                    isRepeat = 0;
                else
                    isRepeat = 1;
            }
        });
    }

    private void getData() {
        if (getIntent().getExtras().containsKey(Constants.TAG_NAME_CLASS)) {
            tag_name = getIntent().getExtras().getString(Constants.TAG_NAME_CLASS);
        }
        if (getIntent().getExtras().containsKey(Constants.KORAN_ENTITY)) {
            koran = (T_Koran) getIntent().getExtras().getSerializable(Constants.KORAN_ENTITY);
            if (koran == null) {
                switchRepeat.setChecked(false);
                tv_title.setText(Constants.NOT_YET);
                tv_sound.setText(Constants.NOT_YET);
                isCreateAlarm = true;

                getCurrentTime();
                return;
            }
            if (koran.time == null) return;
            if (koran.time.equals(Constants.NOT_YET)) {
                tv_title.setText(koran.title);
                tv_sound.setText(koran.sound);
                getCurrentTime();
            } else if (Utils.formatTimeTo24hours(koran.time) != null) {
                String[] time = Utils.formatTimeTo24hours(koran.time).substring(0, 5).split(":");
                tv_hour.setText(time[0]);
                tv_minute.setText(time[1]);
            }

            if (koran.is_repeat == 1) {
                switchRepeat.setChecked(true);
                isRepeat = 1;
            } else {
                switchRepeat.setChecked(false);
                isRepeat = 0;
            }
            if (!koran.description.equals(""))
                tv_title.setText(koran.description);
            else
                tv_title.setText(koran.title);
            tv_sound.setText(koran.sound);
        }
    }

    public void showDialog(Fragment newFragment) {
        final boolean show = newFragment != null;
        final int id = R.id.content_dialog;
        final View v = findViewById(id);
        v.setOnClickListener(!show ? null : new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        v.setVisibility(show ? View.VISIBLE : View.GONE);
        fragmentReplace(id, show ? newFragment : new Fragment());
    }

    private void fragmentReplace(int viewId, Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(viewId, newFragment);
        transaction.commit();
    }

    @OnClick(R.id.btnSaveAlarm)
    public void onClickSaveAlarm() {
        String type;
        String title = tv_title.getText().toString();
        String sound = tv_sound.getText().toString();

        if(title.isEmpty() || title.equals(Constants.NOT_YET)){
            Toast.makeText(this, R.string.toast_msg_enter_title, Toast.LENGTH_SHORT).show();
            return;
        }
        if(sound.isEmpty() || sound.equals(Constants.NOT_YET)){
            Toast.makeText(this, R.string.toast_msg_enter_sound, Toast.LENGTH_SHORT).show();
            return;
        }

        if (isCreateAlarm) {
            koran = new T_Koran();
            koran.description = "";
            if (T_Koran.getContentFromTitle(tv_title.getText().toString()) != null)
                koran.content = T_Koran.getContentFromTitle(tv_title.getText().toString()).content;
        }

        // Set timer
        int hour = Integer.parseInt(tv_hour.getText().toString());
        if (hour <= 12) {
            type = " AM";
        } else {
            hour -= 12;
            type = " PM";
        }
        if (hour < 10)
            koran.time = "0" + String.valueOf(hour) + ":" + tv_minute.getText().toString() + type;
        else
            koran.time = String.valueOf(hour) + ":" + tv_minute.getText().toString() + type;
        koran.is_repeat = isRepeat;

        // Set title
        if (tag_name.equals(Constants.LIST_KORAN_ADAPTER))
            koran.description = tv_title.getText().toString();
        else
            koran.title = title;

        // Set sound
        koran.sound = sound;

        // Set enable
        koran.is_enable = 1;

        //Set uri
        if (intent_uri != null)
            koran.path_sound = intent_uri.toString();
        else
            intent_uri = Uri.parse(koran.path_sound);

        // Update or Save data to local
        if (!isCreateAlarm)
            DataMapper.updateAll(koran);
        else {
            koran.koran_id = String.valueOf(Utils.getIntegerPreference(this, Constants.INDEX_INCREMENT) + 1);
            Utils.setIntegerPreference(DetailAlarmActivity.this, Constants.INDEX_INCREMENT, Integer.parseInt(koran.koran_id));
            DataMapper.saveInfo_Koran(koran);
        }

        // Save Alarm
        saveAlarmManager();
        btnSaveAlarm.setEnabled(false);
    }

    private void saveAlarmManager() {
        final Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        int hour = Integer.parseInt(tv_hour.getText().toString());
        int minute = Integer.parseInt(tv_minute.getText().toString());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        Log.e("alarm time", "" + calendar.getTimeInMillis());
        Log.e("alarm time", "" + hour + ":" + minute);
        Toast.makeText(getApplicationContext(), R.string.toast_msg_saved_set_alarm, Toast.LENGTH_SHORT).show();

        myIntent.putExtra("extra", "yes");
        myIntent.putExtra(Constants.KORAN_ENTITY, koran);
        myIntent.putExtra(Constants.URI, intent_uri);

        pendingIntent = PendingIntent.getBroadcast(DetailAlarmActivity.this, Integer.parseInt(koran.koran_id),
                myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (isRepeat == 0)
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        else
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), Constants.TIME_INTERVAL, pendingIntent);
    }

    @OnClick(R.id.frame_hour)
    public void onClickSettingHour() {
        onSetTime();
    }

    @OnClick(R.id.frame_minute)
    public void onClickSettingMinute() {
        onSetTime();
    }


    @OnClick(R.id.tv_title)
    public void onClickName() {
        if (tag_name.equals(Constants.LIST_ALARM))
            showDialog(new SettingTitleDialog());
        else
            showDialogChangeName();
    }

    @OnClick(R.id.tv_sound)
    public void onClickSound() {
        pickRingtone();
    }

    private void onSetTime() {
        int hour = Integer.parseInt(tv_hour.getText().toString());
        int minute = Integer.parseInt(tv_minute.getText().toString());
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(DetailAlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String temp_hour, temp_minute;
                temp_hour = String.valueOf(selectedHour);
                temp_minute = String.valueOf(selectedMinute);
                if (temp_hour.length() == 1)
                    temp_hour = "0" + temp_hour;
                if (temp_minute.length() == 1)
                    temp_minute = "0" + temp_minute;
                tv_hour.setText(temp_hour);
                tv_minute.setText(temp_minute);
            }
        }, hour, minute, true);
        //Yes 24 hour time
        mTimePicker.setTitle("");
        mTimePicker.show();
    }

    private void pickRingtone() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,
                RingtoneManager.TYPE_NOTIFICATION
                        | RingtoneManager.TYPE_RINGTONE);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Ringtone");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI,
                RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        startActivityForResult(intent, RINGTONEPICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RINGTONEPICKER && resultCode == RESULT_OK) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            intent_uri = uri;
            Ringtone ringTone = RingtoneManager.getRingtone(getApplicationContext(), uri);
            tv_sound.setText(ringTone.getTitle(this));
        }
    }

    private void showDialogChangeName() {
        final Dialog dialog = new Dialog(DetailAlarmActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_setting_name);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        final EditText edt_change_title = (EditText) dialog.findViewById(R.id.edt_change_title);
        final TextView tv_accept = (TextView) dialog.findViewById(R.id.tv_accept);
        final TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        tv_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = edt_change_title.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(dialog.getContext(), R.string.toast_msg_enter_description, Toast.LENGTH_SHORT).show();
                    return;
                }
                DataMapper.updateDescription(koran.koran_id, description);
                tv_title.setText(description);
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    ///////////////////////////////interface ////////////////////////////////////////////////////
    @Override
    public void onAcceptListener(String name) {
        tv_title.setText(name);
    }


    /////////////////////////////////Back ////////////////////////////////////////////////////////

    @OnClick(R.id.btnBack)
    public void onClickBack() {
        setCaseOnBackPress();
    }

    @OnClick(R.id.icon_back_setting)
    public void onClickIconBack() {
        setCaseOnBackPress();
    }

    @Override
    public void onBackPressed() {
        setCaseOnBackPress();
    }

    private void setCaseOnBackPress() {
        if (tag_name.equals(Constants.LIST_KORAN) || tag_name.equals(Constants.LIST_KORAN_ADAPTER))
            Navigator.openListPrayActivity(DetailAlarmActivity.this);
        else if (tag_name.equals(Constants.DETAIL_KORAN))
            finish();
        else if (tag_name.equals(Constants.LIST_ALARM) || tag_name.equals(Constants.LIST_ALARM_SELECTED))
            Navigator.openListAlarmActivity(DetailAlarmActivity.this);
    }

    private String getNameRingToneFromUri(Uri uri) {
        Ringtone ringTone = RingtoneManager.getRingtone(getApplicationContext(), uri);
        return ringTone.getTitle(this);
    }

    private void getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        tv_hour.setText(String.valueOf(hour));
        tv_minute.setText(String.valueOf(minute));
    }
}
