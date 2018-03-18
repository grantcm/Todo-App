package com.grant.todo.Data;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by Grant on 3/16/18.
 */

public class TodoDatabaseSingleton {
    private static TodoDatabase database;

    public TodoDatabaseSingleton(Context context){
        if (database == null) {
            database = Room.databaseBuilder(context,
                    TodoDatabase.class, "todo-database").allowMainThreadQueries().build();
        }
    }

    public static TodoDatabase getDatabase() {
        return database;
    }
}
