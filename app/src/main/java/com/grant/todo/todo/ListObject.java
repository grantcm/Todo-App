package com.grant.todo.todo;

import android.arch.persistence.room.Ignore;

/**
 * Created by Grant on 3/12/18.
 */

public abstract class ListObject<T> {

    @Ignore
    public String title;
    @Ignore
    protected int completed = 0;

    public int incrementCompleted() {
        return ++completed;
    }

    public int decrementCompleted() {
        return --completed;
    }

    public boolean isCompleted() { return false; }

    public int getCompleted() {
        return completed;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        output.append(title).append(",");
        return output.toString();
    }
}
