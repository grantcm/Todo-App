package com.grant.todo.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Grant on 3/17/18.
 */

@Entity(foreignKeys = @ForeignKey(entity = TaskData.class,
        parentColumns = "uid",
        childColumns = "taskId",
        onDelete = CASCADE,
        onUpdate = CASCADE))
public class TodoItemData {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "time")
    private long time;

    @ColumnInfo(name = "timeRemaining")
    private long timeRemaining;

    @ColumnInfo(name = "checked")
    private boolean checked;

    @ColumnInfo(name = "editclicked")
    private boolean editClicked;

    @ColumnInfo(name = "taskId")
    private int taskId;

    public TodoItemData(String title, long time) {
        this.title = title;
        this.time = time;
        this.checked = false;
        this.editClicked = false;
        this.timeRemaining = time;
    }

    @Ignore
    public TodoItemData(String title, long time, int taskId) {
        this(title, time);
        this.taskId = taskId;
    }

    @Ignore
    public TodoItemData(String title, long time,
                        boolean checked, boolean editClicked, int taskId) {
        this(title, time, taskId);
        this.checked = checked;
        this.editClicked = editClicked;
        this.taskId = taskId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isEditClicked() {
        return editClicked;
    }

    public void setEditClicked(boolean editClicked) {
        this.editClicked = editClicked;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public boolean requiresClock() {
        return time != 0;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
}
