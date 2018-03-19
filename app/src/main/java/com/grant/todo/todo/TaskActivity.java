package com.grant.todo.todo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.grant.todo.data.Database;
import com.grant.todo.data.TaskData;
import com.grant.todo.data.TodoData;
import com.grant.todo.inspect.InspectTaskActivity;
import com.grant.todo.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by Grant on 3/19/18.
 */

public class TaskActivity extends AppCompatActivity {
    private Button addTaskButton;

    private TodoArrayAdapter<TaskData> arrayAdapter;
    private List<TaskData> data;
    private TodoData fragmentData;
    private int uid;

    private Intent arguments;
    private ListView listView;
    private TextView titleView;
    private TextView completedCount;
    private String titleMessage;
    private Database database;

    public final static String DATA_ID = "DATA_ID";

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new Database(this.getApplicationContext());
        arguments = getIntent();
        setContentView(R.layout.fragment_todo);
        setViewId();
        parseArguments();
    }

    public void onResume() {
        arrayAdapter.clear();
        data = database.getTaskForTodo(uid);
        arrayAdapter.add(data);
        arrayAdapter.notifyDataSetChanged();
        completedCount.setText(getCountProgress());
        super.onResume();
    }

    private void setViewId(){
        titleView = findViewById(R.id.todo_fragment_title);
        completedCount = findViewById(R.id.progress_fragment_title);
        listView = findViewById(R.id.todo_fragment_main_list);
        addTaskButton = findViewById(R.id.add_new_task_button);
        addTaskButton.setVisibility(View.VISIBLE);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.addTask(new TaskData("New Task", uid));
                arrayAdapter.add(database.findTaskByName("New Task"));
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    private void parseArguments() {
        uid = arguments.getIntExtra(DATA_ID, 0);
        data = database.getTaskForTodo(uid);
        fragmentData = database.findTodoById(uid);
        titleMessage = fragmentData.getTitle();
        titleView.setText(titleMessage);
        setupProgressListView(R.layout.progress_row);
    }

    private String getCountProgress() {
        int count = 0;
        for (TaskData item: data) {
            if (database.uncompletedItemCount(item) == 0)
                count++;
        }
        return String.format(Locale.US,"%d/%d",
                count,
                arrayAdapter.getCount());
    }

    private void setupProgressListView(int layout) {
        listView.setDivider(new ColorDrawable(Color.BLACK));
        listView.setDividerHeight(2);
        arrayAdapter = new TodoArrayAdapter<>(this.getApplicationContext(), layout);
        arrayAdapter.add(data);
        listView.setAdapter(arrayAdapter);
        completedCount.setText(getCountProgress());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TaskData task = arrayAdapter.getItem(i);
                launchInspectActivity(task);

            }
        });
    }

    private void launchInspectActivity(TaskData task) {
        Intent intent = new Intent(this, InspectTaskActivity.class);
        intent.putExtra(InspectTaskActivity.ID, task.getUid());
        startActivity(intent);
    }
}
