package com.grant.todo.todo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.grant.todo.data.Database;
import com.grant.todo.data.TaskData;
import com.grant.todo.data.TodoData;
import com.grant.todo.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Grant on 3/11/18.
 */

public class TodoArrayAdapter<T extends ListObject> extends BaseAdapter {
    private LayoutInflater mInflater;
    private int mResource;
    private int mCompleted = 0;
    private int mTotal = 0;
    private Context mContext;
    private List<T> objects;
    private Database database;

    public TodoArrayAdapter(@NonNull Context context, int resource) {
        super();
        objects = new ArrayList<>();
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        database = new Database(context);
    }

    public void add(@Nullable T object) {
        objects.add(object);
    }

    public void add(Collection<T> collection){
        for (T item : collection) {
            this.add(item);
        }
    }

    public void clear() {
        objects.clear();
    }

    public void remove(@Nullable T object) {
        objects.remove(object);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public T getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView;
        if (convertView != null) {
            rowView = convertView;
        } else {
            rowView = mInflater.inflate(mResource, parent, false);
        }

        T object = this.getItem(position);
        if (mResource == R.layout.oval_row) {
            Drawable background = ContextCompat.getDrawable(mContext, R.drawable.oval_list_item);
            background.setAlpha(0x40);
            rowView.setBackground(background);
        } else {
            if (object instanceof TaskData) {
                TaskData data = (TaskData) object;
                int completed = database.completedTodoItemCountForTaskId(data.getUid());
                int count = database.todoItemCountForTaskId(data.getUid());
                float percent =  (float) completed / (float) count;
                View finishedBar = rowView.findViewById(R.id.completed_percent);
                View unfinishedBar = rowView.findViewById(R.id.unfinished_percent);
                LinearLayout.LayoutParams bar1params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1-percent);
                finishedBar.setLayoutParams(bar1params);
                finishedBar.setBackgroundColor(0x8000FF00);
                LinearLayout.LayoutParams bar2params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, percent);
                unfinishedBar.setLayoutParams(bar2params);
                unfinishedBar.setBackgroundColor(Color.RED);
            }
        }


        RelativeLayout contentView = rowView.findViewById(R.id.list_item_content_box);
        TextView textView = rowView.findViewById(R.id.content_text);
        TextView completionView = rowView.findViewById(R.id.completion_count);
        if (object instanceof TaskData) {
            completionView.setText(String.valueOf(database.uncompletedItemCount((TaskData) object)));
        } else if (object instanceof TodoData) {
            //TODO
            completionView.setText(String.valueOf(database.uncompletedItemCount((TodoData) object)));
        } else {
            completionView.setText("0");
        }
        textView.setText(object.getTitle());
        contentView.bringToFront();

        return rowView;
    }

    public int getTotalCompleted() {
        int completed = 0;
        for (T item: objects) {
            if (item.isCompleted()){
                completed++;
            }
        }
        mCompleted = completed;
        return mCompleted;
    }
}
