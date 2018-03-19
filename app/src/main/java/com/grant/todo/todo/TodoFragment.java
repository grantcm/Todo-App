package com.grant.todo.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;

import com.grant.todo.data.TodoData;
import com.grant.todo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Grant on 3/11/18.
 */

public class TodoFragment extends FragmentSuper {
    private static FragmentManager fragmentManager;
    private TodoArrayAdapter<TodoData> arrayAdapter;
    private String date;
    private List<TodoData> data;
    private ViewPager viewPager;

    public final static String DATE = "DATE";

    public static TodoFragment newInstance(String date, FragmentManager fragManager) {
        TodoFragment todoFragment = new TodoFragment();
        fragmentManager = fragManager;
        Bundle bundle = new Bundle();
        bundle.putString(DATE, date);
        todoFragment.setArguments(bundle);
        return todoFragment;
    }

    @Override
    protected void setViewId(View view){
        super.setViewId(view);
    }

    @Override
    protected void parseArguments() {
        super.parseArguments();
        date = getArguments().getString(DATE);
        data = database.selectDataFromDate(date);
        data.addAll(database.selectDataFromDate(TodoData.EVERYDAY));
        setTitleText();
        setupOvalListView();
    }

    public void setTitleText() {
        if (date.equals(new SimpleDateFormat("E, MMMM dd").format(new Date()))) {
            titleMessage = "Today".concat(" - ").concat(date);
            titleView.setText(titleMessage);
        } else {
            titleMessage = date;
            titleView.setText(titleMessage);
        }
    }

    public void setCompletedCountText() {
        completedCount.setText(String.format(Locale.US, "%d/%d",
                arrayAdapter.getTotalCompleted(), arrayAdapter.getCount()));
    }

    private void setupOvalListView() {
        arrayAdapter = new TodoArrayAdapter<>(getContext(), R.layout.oval_row);
        arrayAdapter.add(data);
        listView.setAdapter(arrayAdapter);
        setCompletedCountText();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TodoData todo = arrayAdapter.getItem(i);
                Intent intent = new Intent(getContext(), TaskActivity.class);
                intent.putExtra(TaskActivity.DATA_ID, todo.getUid());
                startActivity(intent);
            }
        });
    }
}
