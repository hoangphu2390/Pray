package com.kantek.pray.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.kantek.pray.define.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kiet Nguyen on 12-Dec-16.
 */

public class Utils {
    public static String formatTimeTo24hours(String time) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        Date date;
        try {
            date = parseFormat.parse(time);
            return displayFormat.format(date);
        } catch (Exception ex) {
            Log.d("error", ex.getMessage());
        }
        return null;
    }

    public static String formatBitToDate(String date) {
        String result = "";
        if (Character.getNumericValue(date.charAt(0)) == Constants.TRUE) {
            result += Constants.EVERYDAY;
        } else {
            for (int i = 1; i < date.length(); i++) {
                if (Character.getNumericValue(date.charAt(i)) == Constants.TRUE) {
                    if (i == 1) {
                        result += Constants.MON + ", ";
                    } else if (i == 2) {
                        result += Constants.TUE + ", ";
                    } else if (i == 3) {
                        result += Constants.WED + ", ";
                    } else if (i == 4) {
                        result += Constants.THU + ", ";
                    } else if (i == 5) {
                        result += Constants.FRI + ", ";
                    } else if (i == 6) {
                        result += Constants.SAT + ", ";
                    } else {
                        result += Constants.SUN + ", ";
                    }
                }
            }
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    public static void hideSoftKeyboard(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



    public static void setStringPreference(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.
                getSharedPreferences(Constants.PRE_ENVIRONMENT, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringPreference(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PRE_ENVIRONMENT,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public static void setIntegerPreference(Context context, String key, int value) {
        SharedPreferences.Editor editor = context.
                getSharedPreferences(Constants.PRE_ENVIRONMENT, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getIntegerPreference(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PRE_ENVIRONMENT,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    public static void deleteKeyPreference(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PRE_ENVIRONMENT,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).apply();
    }

    public static void clearIntent(Intent intent) {
        if (intent != null) {
            intent.setData(null);
            intent.setAction(null);
            intent.setType(null);
            if (intent.getExtras() != null) {
                for (String extra : intent.getExtras().keySet()) {
                    intent.removeExtra(extra);
                }
            }
        }
    }




}
