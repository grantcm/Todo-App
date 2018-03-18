package com.grant.todo.TodoPackage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.grant.todo.Data.TaskData;
import com.grant.todo.Data.TodoItemData;
import com.grant.todo.InspectPackage.InspectTaskActivity;
import com.grant.todo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Grant on 3/13/18.
 */

public class TaskFragment extends FragmentSuper {
    private TodoArrayAdapter<TaskData> arrayAdapter;
    private List<TaskData> data;
    private int parentId;

    private final static String TITLE_KEYWORD = "TITLE";

    public static TaskFragment newInstance(String title, int ids) {
        TaskFragment taskFragment = new TaskFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_KEYWORD, title);
        bundle.putInt(DATA_ID, ids);
        taskFragment.setArguments(bundle);
        return taskFragment;
    }

    @Override
    protected void parseArguments() {
        super.parseArguments();
        parentId = getArguments().getInt(DATA_ID);
        data = database.getTaskForTodo(parentId);
        setupProgressListView(R.layout.progress_row);
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode,responseCode,data);
        if (responseCode == RESULT_OK) {
            String title = data.getStringExtra(InspectTaskActivity.TITLE);
//            ArrayList<TodoItemData> taskData = data
//                    .getParcelableArrayListExtra(InspectTaskActivity.STEPS);
//            updateData(title, taskData);
            arrayAdapter.notifyDataSetChanged();
            completedCount.setText(String.format(Locale.US,"%d/%d",
                    arrayAdapter.getTotalCompleted(), arrayAdapter.getCount()));
        }
    }

    private void setupProgressListView(int layout) {
        listView.setDivider(new ColorDrawable(Color.BLACK));
        listView.setDividerHeight(2);
        arrayAdapter = new TodoArrayAdapter<>(getContext(), layout);
        arrayAdapter.add(data);
        listView.setAdapter(arrayAdapter);
        completedCount.setText(String.format(Locale.US,"%d/%d",
                arrayAdapter.getTotalCompleted(), arrayAdapter.getCount()));
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

    public void updateData(String taskName, ArrayList<TodoItemData> update) {
        for (TaskData task : data) {
            if (task.getTitle().equals(taskName)) {
                data.remove(task);
                task.setSteps(update);
                data.add(task);
                break;
            }
        }
    }
}
