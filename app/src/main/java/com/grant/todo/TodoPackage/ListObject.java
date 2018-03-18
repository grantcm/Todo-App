package com.grant.todo.TodoPackage;

import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Grant on 3/12/18.
 */

public abstract class ListObject<T> implements Parcelable {

    public String title;
    protected ArrayList<T> steps;
    protected int completed = 0;

    public int incrementCompleted() {
        return ++completed;
    }

    public int decrementCompleted() {
        return --completed;
    }

    public boolean isCompleted() { return false; }

    public float getFloatCompleted() {
        return ((float) getCompleted()) / ((float) steps.size());
    }

    public int getCompleted() {
        return completed;
    }

    public int getStepsLeft() {
        return steps.size() - getCompleted();
    }

    public int getStepsSize() {
        return steps.size();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<T> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<T> steps) {
        this.steps = steps;
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        output.append(title).append(",");
        for (T item : steps) {
            output.append(item.toString()).append(",");
        }
        return output.toString();
    }
}
