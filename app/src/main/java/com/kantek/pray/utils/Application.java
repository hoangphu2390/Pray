package com.kantek.pray.utils;

import android.support.multidex.MultiDexApplication;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.kantek.pray.data.database.T_Koran;

/**
 * Created by Kiet Nguyen on 12-Dec-16.
 */

public class Application extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initActiveAndroid();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    @SuppressWarnings("unchecked")
    private void initActiveAndroid() {
        // Setup ActiveAndroid database model
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClasses(T_Koran.class);
        ActiveAndroid.initialize(configurationBuilder.create());
        ActiveAndroid.setLoggingEnabled(false);
    }
}
