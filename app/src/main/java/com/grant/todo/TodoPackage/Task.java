package com.grant.todo.TodoPackage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Grant on 3/12/18.
 */

public class Task extends ListObject<TodoItem> {

    private String parent;

    public Task(String title, ArrayList<TodoItem> steps) {
        this.title = title;
        this.steps = steps;
    }

    protected Task(Parcel in) {
        title = in.readString();
        steps = new ArrayList<>();
        in.readTypedList(steps, TodoItem.CREATOR);
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public boolean isCompleted(){
        return getCompleted() == steps.size();
    }

    @Override
    public int getCompleted() {
        int completed = 0;
        for (TodoItem item : steps) {
            if (item.isChecked()) {
                completed++;
            }
        }
        this.completed = completed;
        return completed;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeTypedList(steps);
    }

    @Override
    public boolean equals(Object other) {
        return getTitle().equals(((Task) other).getTitle());
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
