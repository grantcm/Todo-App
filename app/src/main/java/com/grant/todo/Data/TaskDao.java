package com.grant.todo.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Grant on 3/17/18.
 */

@Dao
public interface TaskDao {
    @Insert
    void insert(TaskData... data);

    @Delete
    void delete(TaskData... data);

    @Update
    void update(TaskData... data);

    @Query("SELECT * FROM taskdata WHERE uid=:id")
    TaskData findById(int id);

    @Query(("SELECT * FROM taskdata WHERE todoId=:id"))
    List<TaskData> selectFromParentId(int id);

    @Query("SELECT * FROM taskdata WHERE uid IN(:ids)")
    List<TaskData> selectFromIds(int[] ids);

    @Query("SELECT * FROM taskdata WHERE title=:title")
    TaskData findByTitle(String title);
}
