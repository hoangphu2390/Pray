package com.kantek.pray.data.database;

/**
 * Created by Kiet Nguyen on 05-Dec-16.
 */

import android.net.Uri;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Table(name = "T_Koran")
public class T_Koran extends Model implements Serializable, Comparable<T_Koran> {

    @Column(name = "koran_id")
    public String koran_id;

    @Column(name = "title")
    public String title;

    @Column(name = "content")
    public String content;

    @Column(name = "tap_count")
    public int tap_count;

    @Column(name = "tap_total")
    public int tap_total;

    @Column(name = "date")
    public String date;

    @Column(name = "time")
    public String time;

    @Column(name = "sound")
    public String sound;

    @Column(name = "description")
    public String description;

    @Column(name = "is_repeat")
    public int is_repeat;

    @Column(name = "is_enable")
    public int is_enable;

    @Column(name = "path_sound")
    public String path_sound;

    // Make sure to have a default constructor for every ActiveAndroid model
    public T_Koran() {
        super();
    }

    public static void deleteAll() {
        new Delete().from(T_Koran.class).execute();
    }

    public static T_Koran getById(String koran_id) {
        return new Select().from(T_Koran.class).where("koran_id=?", koran_id).executeSingle();
    }

    public static void updateTapCount(String koran_id, int tap_count) {
        new Update(T_Koran.class).set("tap_count=?", tap_count).where("koran_id=?", koran_id).execute();
    }

    public static void updateEnable(String koran_id, int is_enable) {
        new Update(T_Koran.class).set("is_enable=?", is_enable).where("koran_id=?", koran_id).execute();
    }

    public static void updateTitle(String titleOld, String titleNew) {
        new Update(T_Koran.class).set("title=?", titleNew).where("title=?", titleOld).execute();
    }

    public static void updateTitleAndContent(String titleOld, String titleNew, String contentOld, String contentNew) {
        String updateSet = " title = ? ," + " content = ?";
        String whereSet =  " title = ? ," + " content = ?";
        new Update(T_Koran.class).set(updateSet, titleNew, contentNew).where(whereSet, titleOld, contentOld).execute();
    }

    public static T_Koran getContentFromTitle(String title) {
        return new Select().from(T_Koran.class).where("title=?", title).executeSingle();
    }


    public static void updateDescription(String koran_id, String description) {
        new Update(T_Koran.class).set("description=?", description).where("koran_id =?", koran_id).execute();
    }

    public static void updateAll(T_Koran t_koran) {
        String updateSet = " time = ? ," + " is_repeat = ? ," + " title = ? ," + " sound = ? ," + " is_enable = ? ,"
                + " path_sound = ?";

        new Update(T_Koran.class).set(updateSet, t_koran.time, t_koran.is_repeat, t_koran.title,
                t_koran.sound, t_koran.is_enable, t_koran.path_sound).where("koran_id=?", t_koran.koran_id).execute();
    }

    public static List<T_Koran> getAll() {
        return new Select().from(T_Koran.class).execute();
    }

    @Override
    public int compareTo(T_Koran t_koran) {
        if (this.time == null || t_koran.time == null) {
            return 0;
        }
        return this.time.compareTo(t_koran.time);
    }
}

