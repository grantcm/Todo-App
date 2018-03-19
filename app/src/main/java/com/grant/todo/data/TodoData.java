package com.grant.todo.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.grant.todo.todo.ListObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Grant on 3/16/18.
 */

@Entity
public class TodoData extends ListObject<TaskData> {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "title")
    private String title;

    @Ignore
    public static final String EVERYDAY="EVERYDAY";
    @Ignore
    public static final String DATE_FORMAT="E, MMMM dd";

    public TodoData(String title) {
        this.title = title;
        date = new SimpleDateFormat(DATE_FORMAT, Locale.US).format(new Date());
    }

    @Ignore
    public TodoData(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(String.valueOf(uid)).append(",")
        .append(title);
        return output.toString();
    }
}
