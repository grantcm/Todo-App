package com.grant.todo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.grant.todo.Data.Database;
import com.grant.todo.Data.TaskData;
import com.grant.todo.Data.TodoDao;
import com.grant.todo.Data.TodoData;
import com.grant.todo.Data.TodoDatabase;
import com.grant.todo.Data.TodoDatabaseSingleton;
import com.grant.todo.Data.TodoItemData;
import com.grant.todo.TodoPackage.Task;
import com.grant.todo.TodoPackage.Todo;
import com.grant.todo.TodoPackage.TodoFragment;
import com.grant.todo.TodoPackage.TodoItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment CURRENTLY_VISIBLE;
    private static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        setContentView(R.layout.activity_main);

        database = new Database(this);
        TodoData[] todoData = {new TodoData("Study"),
                new TodoData("Cook"), new TodoData("Clean")};

        TaskData[] taskData = {new TaskData("Task 1"),
                new TaskData("Task 2"), new TaskData("Task 3")};

        TodoItemData[] itemData = {new TodoItemData("Start this", 5),
                new TodoItemData("Finish that", 0),
                new TodoItemData("Finally this", 20)};

//        database.addTodo(todoData);
//        database.addTaskToTodo(todoData[0], taskData);
//        database.addTodoItemToTask(taskData[2], itemData);

        TodoFragment todoFragment = TodoFragment.newInstance(database.listAllTodoId());
        fragmentTransaction.add(R.id.main_container, todoFragment);
        CURRENTLY_VISIBLE = todoFragment;
        fragmentTransaction.commit();
    }
}

