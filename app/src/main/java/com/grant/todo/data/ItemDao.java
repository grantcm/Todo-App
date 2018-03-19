package com.grant.todo.data;

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

    @Query("SELECT COUNT(*) FROM todoitemdata WHERE taskId=:id")
    int countItemsForId(int id);

    @Query("SELECT COUNT(*) FROM todoitemdata WHERE checked = 1 AND taskID=:id")
    int countCompletedItemsForId(int id);

    @Query("SELECT COUNT(*) FROM todoitemdata WHERE checked = 0 AND taskID=:id")
    int countUncompletedItemsForId(int id);

    @Query("SELECT * FROM todoitemdata WHERE uid=:id")
    TodoItemData findById(int id);

    @Query("SELECT * FROM todoitemdata WHERE taskId=:id")
    List<TodoItemData> selectFromParentId(int id);

    @Query("SELECT * FROM todoitemdata WHERE uid IN(:ids)")
    List<TodoItemData> selectFromIds(int[] ids);

    @Query("SELECT * FROM todoitemdata WHERE title=:title")
    TodoItemData findByTitle(String title);
}
