package com.grant.todo.TodoPackage;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.grant.todo.Data.TodoData;
import com.grant.todo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Grant on 3/11/18.
 */

public class TodoFragment extends FragmentSuper {
    private FragmentManager fragmentManager;
    private TodoArrayAdapter<Todo> arrayAdapter;
    private ArrayList<Todo> testData;

    public static TodoFragment newInstance(String title, ArrayList<Todo> data) {
        TodoFragment todoFragment = new TodoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_KEYWORD, title);
        bundle.putParcelableArrayList(LIST_DATA_KEYWORD, data);
        todoFragment.setArguments(bundle);
        return todoFragment;
    }

    public static TodoFragment newInstanceData(String title, List<TodoData> data) {
        TodoFragment todoFragment = new TodoFragment();
        ArrayList<Todo> todoData = new ArrayList<>();
        for (TodoData temp : data) {
            //todoData.add(temp.getTodo());
        }
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_KEYWORD, title);
        bundle.putParcelableArrayList(LIST_DATA_KEYWORD, todoData);
        todoFragment.setArguments(bundle);
        return todoFragment;
    }

    @Override
    protected void parseArguments() {
        super.parseArguments();
        testData = arguments.getParcelableArrayList(LIST_DATA_KEYWORD);
        setupOvalListView();
    }

    public void setCompletedCountText() {
        completedCount.setText(String.format(Locale.US, "%d/%d",
                arrayAdapter.getTotalCompleted(), arrayAdapter.getCount()));
    }

    private void setupOvalListView() {
        arrayAdapter = new TodoArrayAdapter<>(getContext(), R.layout.oval_row);
        arrayAdapter.add(testData);
        listView.setAdapter(arrayAdapter);
        setCompletedCountText();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Todo todo = arrayAdapter.getItem(i);
                TaskFragment taskFragment = TaskFragment.newInstance(todo.getTitle(),todo.getSteps());
                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                        .addToBackStack("Main");
                fragmentTransaction.replace(R.id.main_container, taskFragment);
                fragmentTransaction.commit();
            }
        });
    }
}
