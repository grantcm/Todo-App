package com.grant.todo.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Grant on 3/17/18.
 */

@Entity(foreignKeys = @ForeignKey(entity = TodoData.class,
                                            parentColumns = "uid",
                                            childColumns = "todoId",
                                            onDelete = CASCADE,
                                            onUpdate = CASCADE))
public class TaskData {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "todoId")
    private int todoId;

    public TaskData(String title) {
        this.title = title;
    }

    @Ignore
    public TaskData(String title, int todoId) {
        this(title);
        this.todoId = todoId;
    }

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
