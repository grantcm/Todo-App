package com.grant.todo.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.grant.todo.TodoPackage.Todo;

import java.util.Date;
import java.util.List;

/**
 * Created by Grant on 3/16/18.
 */

@Dao
public interface TodoDao {

    @Query("SELECT * FROM tododata")
    List<TodoData> listAll();

    @Query("SELECT * FROM tododata WHERE date IN(:dates)")
    List<TodoData> selectFromDates(Date[] dates);

    @Query("SELECT * FROM tododata WHERE uid IN(:ids)")
    List<TodoData> selectFromIds(int[] ids);

    @Query("SELECT * FROM tododata WHERE title=:title")
    TodoData findByTitle(String title);

    @Insert
    void insert(TodoData... data);

    @Update
    void update(TodoData... data);

    @Delete
    void delete (TodoData data);
}
