package com.grant.todo.todo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.grant.todo.data.TaskData;
import com.grant.todo.data.TodoData;
import com.grant.todo.inspect.InspectTaskActivity;
import com.grant.todo.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by Grant on 3/13/18.
 */

//TODO: Convert this to an activity

public class TaskFragment extends FragmentSuper {
    private Button addTaskButton;

    private TodoArrayAdapter<TaskData> arrayAdapter;
    private List<TaskData> data;
    private TodoData fragmentData;
    private int uid;

    public static TaskFragment newInstance(int ids) {
        TaskFragment taskFragment = new TaskFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DATA_ID, ids);
        taskFragment.setArguments(bundle);
        return taskFragment;
    }

    @Override
    public void onResume() {
        arrayAdapter.clear();
        data = database.getTaskForTodo(uid);
        arrayAdapter.add(data);
        arrayAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    protected void setViewId(View view){
        super.setViewId(view);
        addTaskButton = view.findViewById(R.id.add_new_task_button);
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

    @Override
    protected void parseArguments() {
        super.parseArguments();
        uid = getArguments().getInt(DATA_ID);
        data = database.getTaskForTodo(uid);
        fragmentData = database.findTodoById(uid);
        titleMessage = fragmentData.getTitle();
        titleView.setText(titleMessage);
        setupProgressListView(R.layout.progress_row);
    }


//    //TODO: Rewrite this into the onResume method
//    @Override
//    public void onActivityResult(int requestCode, int responseCode, Intent data) {
//        super.onActivityResult(requestCode,responseCode,data);
//        if (responseCode == RESULT_OK) {
//            String title = data.getStringExtra(InspectTaskActivity.TITLE);
////            ArrayList<TodoItemData> taskData = data
////                    .getParcelableArrayListExtra(InspectTaskActivity.STEPS);
////            updateData(title, taskData);
//            arrayAdapter.notifyDataSetChanged();
//            completedCount.setText(String.format(Locale.US,"%d/%d",
//                    database.completedTodoItemCountForTaskId(uid),
//                    arrayAdapter.getCount()));
//        }
//    }

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
        arrayAdapter = new TodoArrayAdapter<>(getContext(), layout);
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
        Intent intent = new Intent(this.getActivity(), InspectTaskActivity.class);
        intent.putExtra(InspectTaskActivity.ID, task.getUid());
        startActivity(intent);
    }
}
