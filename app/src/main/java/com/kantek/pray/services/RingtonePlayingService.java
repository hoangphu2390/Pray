package com.kantek.pray.services;

/**
 * Created by Kiet Nguyen on 12-Dec-16.
 */

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.kantek.pray.R;
import com.kantek.pray.data.database.T_Koran;
import com.kantek.pray.define.Constants;
import com.kantek.pray.ui.detail_setting.ShowDetailAlarmActivity;

import java.util.List;

public class RingtonePlayingService extends Service {

    private boolean isRunning;
    private Context context;
    MediaPlayer mMediaPlayer;
    private int startId;
    private List<String> listRingtoneName;
    private List<String> listRingtonePath;

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MyActivity", "In the Richard service");
        return null;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        String state = intent.getExtras().getString("extra");
        Uri uri = intent.getExtras().getParcelable(Constants.URI);
        T_Koran koran = (T_Koran) intent.getExtras().getSerializable(Constants.KORAN_ENTITY);

        Intent intent1 = new Intent(this.getApplicationContext(), ShowDetailAlarmActivity.class);
        intent1.putExtra(Constants.KORAN_ENTITY, koran);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, Integer.parseInt(koran.koran_id), intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        String content_notification = "";
        if (!koran.description.equals(""))
            content_notification = koran.description;
        else
            content_notification = koran.title;

        Notification mNotify = new Notification.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(content_notification)
                .setSmallIcon(R.drawable.icon_app)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        Log.e("what is going on here  ", state);

        assert state != null;
        switch (state) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }

        if (!this.isRunning && startId == 1) {
            Log.e("if there was not sound ", " and you want start");
            if(uri != null) {
                try {
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setDataSource(this.getApplicationContext(), uri);
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
                    mMediaPlayer.setLooping(true);
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            mNM.notify(0, mNotify);

            this.isRunning = true;
            this.startId = 0;

        } else if (!this.isRunning && startId == 0) {
            Log.e("if there was not sound ", " and you want end");

            this.isRunning = false;
            this.startId = 0;

        } else if (this.isRunning && startId == 1) {
            Log.e("if there is sound ", " and you want start");

            this.isRunning = true;
            this.startId = 0;

        } else {
            Log.e("if there is sound ", " and you want end");
            try {
                mMediaPlayer.stop();
                mMediaPlayer.reset();

                this.isRunning = false;
                this.startId = 0;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();

        this.isRunning = false;
    }

//    public List<String> getListRingTone() {
//        listRingtoneName = new ArrayList<>();
//        listRingtonePath = new ArrayList<>();
//        RingtoneManager manager = new RingtoneManager(this);
//        manager.setType(RingtoneManager.TYPE_RINGTONE | RingtoneManager.TYPE_NOTIFICATION);
//        Cursor cursor = manager.getCursor();
//
//        while (cursor.moveToNext()) {
//            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
//            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
//            listRingtoneName.add(notificationTitle);
//            listRingtonePath.add(notificationUri);
//        }
//        return listRingtoneName;
//    }
//
//    private int getPositionRingTone() {
//        getListRingTone();
//        for (int i = 0; i < listRingtoneName.size(); i++) {
//            if (listRingtoneName.get(i).equals(ringtone)) {
//                return i;
//            }
//        }
//        return -1;
//    }
}
