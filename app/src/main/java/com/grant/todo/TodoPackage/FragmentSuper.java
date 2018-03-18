package com.grant.todo.TodoPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.grant.todo.R;

import java.util.ArrayList;

/**
 * Created by Grant on 3/13/18.
 */

public class FragmentSuper extends Fragment {

    protected Bundle arguments;
    protected ListView listView;
    protected TextView titleView;
    protected TextView completedCount;
    protected String titleMessage;

    protected final static String TITLE_KEYWORD = "TITLE";
    protected final static String LIST_DATA_KEYWORD = "LIST DATA";

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arguments = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        setViewId(view);
        parseArguments();
        return view;
    }

    protected void parseArguments() {
        titleMessage = arguments.getString(TITLE_KEYWORD);
        titleView.setText(titleMessage);
    }

    protected void setViewId(View view){
        titleView = view.findViewById(R.id.todo_fragment_title);
        completedCount = view.findViewById(R.id.progress_fragment_title);
        listView = view.findViewById(R.id.todo_fragment_main_list);
    }
}
