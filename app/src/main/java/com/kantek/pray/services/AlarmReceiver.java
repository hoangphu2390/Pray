package com.kantek.pray.services;

/**
 * Created by Kiet Nguyen on 12-Dec-16.
 */

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.kantek.pray.data.database.T_Koran;
import com.kantek.pray.define.Constants;
import com.kantek.pray.utils.WakeLocker;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String state = intent.getExtras().getString("extra");
        Uri uri = intent.getExtras().getParcelable(Constants.URI);
        T_Koran koran = (T_Koran) intent.getExtras().getSerializable(Constants.KORAN_ENTITY);
        Log.e("MyActivity", "In the receiver with " + state);

        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);
        serviceIntent.putExtra("extra", state);
        serviceIntent.putExtra(Constants.URI, uri);
        serviceIntent.putExtra(Constants.KORAN_ENTITY, koran);

        context.startService(serviceIntent);

        WakeLocker.acquire(context);
    }

}
