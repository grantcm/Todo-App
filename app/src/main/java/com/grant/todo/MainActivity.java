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
    private ArrayList<Todo> testData = new ArrayList<>();
    private ArrayList<Task> testTask = new ArrayList<>();
    private ArrayList<TodoItem> testItem = new ArrayList<>();
    private static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        setContentView(R.layout.activity_main);

        testItem.add(new TodoItem("Start this",5));
        testItem.add(new TodoItem("finish that", 0));
        testItem.add(new TodoItem("done", 0));

        testTask.add(new Task("Task 1", testItem));
        testTask.add(new Task("Task 2", testItem));

//        List<TodoData> data = new ArrayList<>();
//        data.add(new TodoData(0, new Todo("Study", testTask)));
//        data.add(new TodoData(1, new Todo("Cook", testTask)));
//        data.add(new TodoData(1, new Todo("Clean", testTask)));

        testData.add(new Todo("Study", testTask));
        testData.add(new Todo("Cook", testTask));
        testData.add(new Todo("Clean", testTask));



        database = new Database(this);
        TodoData[] todoData = {new TodoData("Study"),
                new TodoData("Cook"), new TodoData("Clean")};

        TaskData[] taskData = {new TaskData("Task 1"),
                new TaskData("Task 2"), new TaskData("Task 3")};

        TodoItemData[] itemData = {new TodoItemData("Start this", 5),
                new TodoItemData("Finish that", 0),
                new TodoItemData("Finally this", 20)};

        database.addTodo(todoData);
        database.addTaskToTodo(todoData[0], taskData);
        database.addTodoItemToTask(taskData[0], itemData);

        TodoFragment todoFragment = TodoFragment.newInstance("Today",testData);
        //TodoFragment todoFragment = TodoFragment.newInstanceData("Today", data);
        fragmentTransaction.add(R.id.main_container, todoFragment);
        CURRENTLY_VISIBLE = todoFragment;
        fragmentTransaction.commit();
    }
}

