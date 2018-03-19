package com.grant.todo.inspect;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.grant.todo.data.Database;
import com.grant.todo.data.TaskData;
import com.grant.todo.data.TodoItemData;
import com.grant.todo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grant on 3/12/18.
 */

public class ListTaskFragment extends Fragment {
    private String titleMessage;
    private ViewSwitcher titleViewSwitcher;
    private TextView title;
    private EditText editTitle;
    private ListView tasks;
    private int uid;
    private TaskData data;
    private List<TodoItemData> taskList;
    private InspectArrayAdapter inspectArrayAdapter;
    private ProgressBar progress;
    private Button addRowButton;
    private TodoItemData lastEdited = null;
    private int lastEditedPosition = -1;
    private boolean inEditView = false;
    private OnItemClickedListener activityCallback;
    private Database database;

    public static final String DATA_ID = "DATA_ID";

    public ListTaskFragment() {
        taskList = new ArrayList<>();
    }

    public static ListTaskFragment newInstance(int id) {
        ListTaskFragment newFragment = new ListTaskFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DATA_ID, id);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new Database(this.getContext());
        Bundle arguments = getArguments();
        uid = arguments.getInt(DATA_ID);
        data = database.findTaskById(uid);
        titleMessage = data.getTitle();
        taskList = database.getTodoItemForTask(uid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.steps_fragment, container, false);
        tasks = view.findViewById(R.id.steps);
        progress = view.findViewById(R.id.progress);
        addRowButton = view.findViewById(R.id.add_new_row_button);
        titleViewSwitcher = view.findViewById(R.id.title_view_switcher);
        title = titleViewSwitcher.findViewById(R.id.title);
        editTitle = titleViewSwitcher.findViewById(R.id.edit_recipe_title_box);
        title.setText(titleMessage);
        setupTitleOnLongClick();
        inspectArrayAdapter = new InspectArrayAdapter(this.getContext(),
                android.R.layout.simple_list_item_checked, taskList, this);
        tasks.setAdapter(inspectArrayAdapter);
        updateProgressBar(getCompletedCount());
        return view;
    }

    public InputMethodManager getInputMethodManager() {
        return (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /**
     * If in edit mode and no items in edit mode then update to normal mode
     * If There's an edited item, then save its state and return the item to normal display
     * @return boolean indicating if the backpress was handled by the fragment
     */
    public boolean handleBackPressed(){
        if (inEditView && lastEdited==null) {
            updateMainViewToEdit();
            return true;
        } else if (inEditView && lastEdited.isEditClicked()) {
            lastEdited.setEditClicked(false);
            if (lastEditedPosition != -1) {
                View view = tasks.getChildAt(lastEditedPosition);
                if (view!= null) {
                    EditText editText = view.findViewById(R.id.edit_recipe_text);
                    EditText timeText = view.findViewById(R.id.time_value);
                    lastEdited.setTitle(editText.getText().toString());
                    String text = timeText.getText().toString();
                    if (!text.startsWith("0")) {
                        text = "0".concat(text);
                    }
                    long time = (long) (Double.parseDouble(text) * 60.0);
                    lastEdited.setTime(time);
                    lastEdited.setTimeRemaining(time);
                    database.updateTodoItem(lastEdited);
                }
            }
            inspectArrayAdapter.notifyDataSetChanged();
            return true;
        } else if (inEditView) {
            updateMainViewToEdit();
            return true;
        }
        saveState();
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityCallback = (OnItemClickedListener) context;
    }

    public void updateData(){
        //TODO: Improve this
        data = database.findTaskById(uid);
        inspectArrayAdapter.clear();
        taskList = database.getTodoItemForTask(uid);
        inspectArrayAdapter.addAll(taskList);
    }

    public void saveState() {
        TodoItemData[] itemData = new TodoItemData[taskList.size()];
        database.updateTodoItem(taskList.toArray(itemData));
        database.updateTask(data);
    }

    @Override
    public void onResume(){
        updateData();
        super.onResume();
    }

    @Override
    public void onPause(){
        saveState();
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateProgress(int change) {
        progress.setProgress(progress.getProgress()+change, true);
    }

    /**
     * On long title click enter edit mode
     * On item click enter item edit mode
     * On item long click delete
     */
    private void setupTitleOnLongClick() {
        title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                updateMainViewToEdit();
                return true;
            }
        });

        tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TodoItemData item = inspectArrayAdapter.getItem(position);
                if(inEditView) {
                    if(lastEdited != null) {
                        lastEdited.setEditClicked(false);
                    }
                    lastEdited = item;
                    lastEditedPosition = position;
                    item.setEditClicked(true);
                    inspectArrayAdapter.notifyDataSetChanged();
                }
            }
        });

        tasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(inEditView) {
                    database.deleteTodoItem(taskList.get(position));
                    taskList.remove(position);
                    inspectArrayAdapter.clear();
                    inspectArrayAdapter.addAll(taskList);
                    inspectArrayAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
    }

    private int getCompletedCount(){
        int count = 0;
        for (TodoItemData todo: taskList) {
            if(todo.isChecked())
                count++;
        }
        return count;
    }

    /**
     * updates the progress bar when max size changes
     * @param completed number of tasks checked off
     */
    private void updateProgressBar(int completed) {
        progress.setMax(taskList.size());
        progress.setProgress(completed);
    }

    /**
     * Launches new clock activity with parameters time and step
     * @param item is the data for the clicked list item
     */
    public void launchClock(TodoItemData item){
        activityCallback.onItemClicked(item);
    }

    public boolean getInEditView() {
        return inEditView;
    }

    /**
     * If not in edit view:
     * Changes the view of the fragment to the Edit View by revealing editText fields for title
     * and updating item listeners to change to their edit views upon pressing
     *
     * If in edit view:
     * Returns to non-editable view of data. Saves the updates to each property.
     */
    public void updateMainViewToEdit() {
        if (inEditView) {
            addRowButton.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
            inEditView = false;
            title.setText(editTitle.getText().toString());
            data.setTitle(title.getText().toString());
            titleViewSwitcher.setDisplayedChild(0);
            progress.setMax(taskList.size());
            for (TodoItemData r : taskList) {
                r.setEditClicked(false);
            }
            inspectArrayAdapter.notifyDataSetChanged();
        } else {
            editTitle.setText(title.getText().toString());
            progress.setVisibility(View.GONE);
            titleViewSwitcher.setDisplayedChild(1);
            addRowButton.setVisibility(View.VISIBLE);
            inEditView = true;
            tasks.setVerticalScrollBarEnabled(true);
            inspectArrayAdapter.notifyDataSetChanged();
        }
    }

    public void addNewRow() {
        if (inEditView) {
            TodoItemData newItem = new TodoItemData("New", 0, uid);
//            if (lastEdited != null) {
//                lastEdited.setEditClicked(false);
//            }
//            lastEdited = newItem;
//            lastEditedPosition = taskList.size();
            //TODO: Fix this
            database.addTodoItem(newItem);
            taskList.add(newItem);
            inspectArrayAdapter.add(newItem);
        }
    }
}
