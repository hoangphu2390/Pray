package com.kantek.pray.data.database;

import com.activeandroid.ActiveAndroid;
import com.kantek.pray.define.Constants;

import java.util.List;

/**
 * Created by Kiet Nguyen on 05-Dec-16.
 */

public class DataMapper {

    /////////////////////////////////////////////// T_Koran/////////////////////////////////////////

    public static void saveInfo_Koran(T_Koran t_koran) {
        if (t_koran == null) return;
        if (T_Koran.getById(t_koran.koran_id) != null) return;
        ActiveAndroid.beginTransaction();
        t_koran.save();
        ActiveAndroid.setTransactionSuccessful();
        ActiveAndroid.endTransaction();
    }

    public static void updateCountTap(String koran_id, int tap_count) {
        T_Koran.updateTapCount(koran_id, tap_count);
    }

    public static void updateEnable(String koran_id, int is_enable) {
        T_Koran.updateEnable(koran_id, is_enable);
    }

    public static void updateAll(T_Koran t_koran) {
        T_Koran.updateAll(t_koran);
    }

    public static void updateTitle(String titleOld, String titleNew) {
        T_Koran.updateTitle(titleOld, titleNew);
    }

    public static void updateDescription(String koran_id, String description) {
        T_Koran.updateDescription(koran_id, description);
    }

    public static T_Koran getInfo_Koran(String koran_id) {
        T_Koran t_koran = T_Koran.getById(koran_id);
        return t_koran;
    }

    public static List<T_Koran> getList_InfoKoran() {
        List<T_Koran> koranList = T_Koran.getAll();
        return koranList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
}
