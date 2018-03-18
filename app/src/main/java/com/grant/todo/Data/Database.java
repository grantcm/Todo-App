package com.grant.todo.Data;

import android.content.Context;
import android.os.AsyncTask;

import com.grant.todo.TodoPackage.Task;
import com.grant.todo.TodoPackage.Todo;
import com.grant.todo.TodoPackage.TodoItem;

/**
 * Created by Grant on 3/17/18.
 */

public class Database {
    private static TodoDatabase database;
    private static ItemDao itemDao;
    private static TaskDao taskDao;
    private static TodoDao todoDao;

    public Database(Context context) {
        TodoDatabaseSingleton singleton = new TodoDatabaseSingleton(context);
        database = singleton.getDatabase();
        itemDao = database.itemDao();
        taskDao = database.taskDao();
        todoDao = database.todoDao();
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
