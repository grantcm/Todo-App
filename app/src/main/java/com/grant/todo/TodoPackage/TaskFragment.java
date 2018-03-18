package com.grant.todo.TodoPackage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.grant.todo.InspectPackage.InspectTaskActivity;
import com.grant.todo.R;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Grant on 3/13/18.
 */

public class TaskFragment extends FragmentSuper {
    private TodoArrayAdapter<Task> arrayAdapter;
    private ArrayList<Task> testData;

    private final static String TITLE_KEYWORD = "TITLE";
    private final static String LIST_DATA_KEYWORD = "LIST DATA";

    public static TaskFragment newInstance(String title, ArrayList<Task> data) {
        TaskFragment taskFragment = new TaskFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_KEYWORD, title);
        bundle.putParcelableArrayList(LIST_DATA_KEYWORD, data);
        taskFragment.setArguments(bundle);
        return taskFragment;
    }

    @Override
    protected void parseArguments() {
        super.parseArguments();
        testData = arguments.getParcelableArrayList(LIST_DATA_KEYWORD);
        setupProgressListView(R.layout.progress_row);
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode,responseCode,data);

        if (responseCode == RESULT_OK) {
            String title = data.getStringExtra(InspectTaskActivity.TITLE);
            ArrayList<TodoItem> taskData = data
                    .getParcelableArrayListExtra(InspectTaskActivity.STEPS);
            updateData(title, taskData);
            arrayAdapter.notifyDataSetChanged();
            completedCount.setText(String.format(Locale.US,"%d/%d",
                    arrayAdapter.getTotalCompleted(), arrayAdapter.getCount()));
        }
    }

    private void setupProgressListView(int layout) {
        listView.setDivider(new ColorDrawable(Color.BLACK));
        listView.setDividerHeight(2);
        arrayAdapter = new TodoArrayAdapter<>(getContext(), layout);
        arrayAdapter.add(testData);
        listView.setAdapter(arrayAdapter);
        completedCount.setText(String.format(Locale.US,"%d/%d",
                arrayAdapter.getTotalCompleted(), arrayAdapter.getCount()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = arrayAdapter.getItem(i);
                launchInspectActivity(task);

            }
        });
    }

    private void launchInspectActivity(Task task) {
        Intent intent = new Intent(this.getActivity(), InspectTaskActivity.class);
        intent.putExtra(InspectTaskActivity.TITLE, task.getTitle());
        intent.putExtra(InspectTaskActivity.STEPS, task.getSteps());
        startActivityForResult(intent, 1);
    }

    public void updateData(String taskName, ArrayList<TodoItem> data) {
        for (Task task : testData) {
            if (task.getTitle().equals(taskName)) {
                testData.remove(task);
                task.setSteps(data);
                testData.add(task);
                break;
            }
        }
    }
}
