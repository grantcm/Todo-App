package com.grant.todo.data;

import android.content.Context;

import java.util.List;

/**
 * Created by Grant on 3/17/18.
 */

public class Database {
    private static ItemDao itemDao;
    private static TaskDao taskDao;
    private static TodoDao todoDao;

    public Database(Context context) {
        TodoDatabase database = TodoDatabase.getDatabase(context);
        todoDao = database.todoDao();
        taskDao = database.taskDao();
        itemDao = database.itemDao();
    }

    public int[] selectIdsFromDate(String date) {
        return todoDao.selectIdsFromDate(date);
    }

    public List<TodoData> selectDataFromDate(String date) {
        return todoDao.selectDataFromDate(date);
    }

    public int todoItemCountForTaskId(int id) {
        return itemDao.countItemsForId(id);
    }

    public int completedTodoItemCountForTaskId(int id) {
        return itemDao.countCompletedItemsForId(id);
    }

    public int uncompletedItemCount(TodoData data) {
        return 0;
    }

    public int uncompletedItemCount(TaskData data) {
        return itemDao.countUncompletedItemsForId(data.getUid());
    }

    public int[] listAllTodoId() {
        return todoDao.listAllTodoId();
    }

    public List<TodoData> listAllTodo() {
        return todoDao.listAll();
    }

    public TodoItemData findTodoItemByName(String name) {
        return itemDao.findByTitle(name);
    }

    public TaskData findTaskByName(String name) {
        return taskDao.findByTitle(name);
    }

    public TodoData findTodoByName(String name) {
        return todoDao.findByTitle(name);
    }

    public List<TodoData> getTodoForIds(int[] ids) {
        return todoDao.selectFromIds(ids);
    }

    public TodoItemData findTodoItemById(int id) {
        return itemDao.findById(id);
    }

    public TaskData findTaskById(int id) {
        return taskDao.findById(id);
    }

    public TodoData findTodoById(int id) {
        return todoDao.findById(id);
    }

    public List<TodoItemData> getTodoItemForTask(int id) {
        return itemDao.selectFromParentId(id);
    }

    public List<TaskData> getTaskForTodo(int id) {
        return taskDao.selectFromParentId(id);
    }

    public void updateTodoItem(TodoItemData... items) {
        itemDao.update(items);
    }

    public void updateTodo(TodoData... items) {
        todoDao.update(items);
    }

    public void updateTask(TaskData... items) {
        taskDao.update(items);
    }

    public void addTodo(TodoData... items) {
        todoDao.insert(items);
    }

    public void addTask(TaskData... items) {
        taskDao.insert(items);
    }

    public void addTaskToTodo(TodoData todo, TaskData... items) {
        TodoData parent = todoDao.findByTitle(todo.getTitle());
        if (parent != null) {
            for(TaskData item: items) {
                item.setTodoId(parent.getUid());
                taskDao.insert(item);
            }
        }
    }

    public void addTodoItemToTask(TaskData task, TodoItemData... items) {
        TaskData parent = taskDao.findByTitle(task.getTitle());
        if (parent != null) {
            for(TodoItemData item: items) {
                item.setTaskId(parent.getUid());
                itemDao.insert(item);
            }
        }
    }

    public void addTodoItem(TodoItemData... items) {
        itemDao.insert(items);
    }

    public void deleteTodo(TodoData item){
        todoDao.delete(item);
    }

    public void deleteTask(TaskData item){
        taskDao.delete(item);
    }

    public void deleteTodoItem(TodoItemData item){
        itemDao.delete(item);
    }
}
