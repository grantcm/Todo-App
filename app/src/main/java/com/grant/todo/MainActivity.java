package com.grant.todo;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.grant.todo.data.Database;
import com.grant.todo.pager.PagerFragment;

public class MainActivity extends AppCompatActivity {


    private static Database database;
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        database = new Database(this);
//        TodoData[] todoData = {
//                new TodoData("Study", TodoData.EVERYDAY),
//                new TodoData("Cook", TodoData.EVERYDAY),
//                new TodoData("Clean",TodoData.EVERYDAY)};
//
//        TaskData[] taskData = {new TaskData("Task 1"),
//                new TaskData("Task 2"), new TaskData("Task 3")};
//
//        TodoItemData[] itemData = {new TodoItemData("Start this", 5),
//                new TodoItemData("Finish that", 0),
//                new TodoItemData("Finally this", 20)};
//
//        database.addTodo(todoData);
//        database.addTaskToTodo(todoData[0], taskData);
//        database.addTodoItemToTask(taskData[0], itemData);
//        database.addTodoItemToTask(taskData[1], itemData);
//        database.addTodoItemToTask(taskData[2], itemData);

        setContentView(R.layout.activity_main);

        PagerFragment pagerFragment = PagerFragment.newInstance();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, pagerFragment, "Main");
        fragmentTransaction.commit();
    }
}

