package com.grant.todo.Data;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Grant on 3/17/18.
 */

public class DateConverter {
    @TypeConverter
    public static Date toDate(long time) {
        return new Date(time);
    }

    @TypeConverter
    public static long fromDate(Date date) {
        return date.getTime();
    }
}
