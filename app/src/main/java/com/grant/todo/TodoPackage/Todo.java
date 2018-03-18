package com.grant.todo.TodoPackage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grant on 3/11/18.
 */

public class Todo extends ListObject<Task> {

    public Todo(String title, ArrayList<Task> steps) {
        this.title = title;
        this.steps = steps;
    }

    @Override
    public boolean isCompleted() {
        return getCompleted() == steps.size();
    }

    @Override
    public int getCompleted() {
        int completed = 0;
        for (Task item : steps) {
            if (item.isCompleted()) {
                completed++;
            }
        }
        this.completed = completed;
        return completed;
    }
}
