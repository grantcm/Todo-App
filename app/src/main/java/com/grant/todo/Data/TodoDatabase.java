package com.grant.todo.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * Created by Grant on 3/16/18.
 */

@Database(entities = {TodoData.class, TaskData.class, TodoItemData.class}, version = 1)
@TypeConverters(DateConverter.class)

public abstract class TodoDatabase extends RoomDatabase {
    public abstract TodoDao todoDao();
    public abstract TaskDao taskDao();
    public abstract ItemDao itemDao();

    private static TodoDatabase INSTANCE;

    public static TodoDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    TodoDatabase.class, "todo-database").allowMainThreadQueries().build();
        }

        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
