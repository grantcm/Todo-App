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
public interface ItemDao {
    @Insert
    void insert(TodoItemData... data);

    @Delete
    void delete(TodoItemData... data);

    @Update
    void update(TodoItemData... data);

    @Query("SELECT * FROM todoitemdata WHERE taskId=:id")
    List<TodoItemData> selectFromParentId(int id);

    @Query("SELECT * FROM todoitemdata WHERE uid IN(:ids)")
    List<TodoItemData> selectFromIds(int[] ids);

    @Query("SELECT * FROM todoitemdata WHERE title=:title")
    TodoItemData findByTitle(String title);
}
