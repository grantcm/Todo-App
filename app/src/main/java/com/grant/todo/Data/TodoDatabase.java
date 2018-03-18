package com.grant.todo.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * Created by Grant on 3/16/18.
 */

@Database(entities = {TodoData.class, TaskData.class, TodoItemData.class}, version = 1)
@TypeConverters(DateConverter.class)

public abstract class TodoDatabase extends RoomDatabase {
    public abstract TodoDao todoDao();
    public abstract TaskDao taskDao();
    public abstract ItemDao itemDao();
}
