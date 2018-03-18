package com.grant.todo.InspectPackage;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.grant.todo.Data.TodoItemData;
import com.grant.todo.R;
import com.grant.todo.TodoPackage.TodoItem;

import java.util.List;
import java.util.Locale;


/**
 * Created by Grant on 12/28/17.
 */

public class InspectArrayAdapter extends ArrayAdapter<TodoItemData> {

    private LayoutInflater mInflater;
    private int mResource;
    private ListTaskFragment parentClass;

    public InspectArrayAdapter(Context context, int resource, List<TodoItemData> objects,
                               ListTaskFragment parentClass) {
        super(context, R.layout.task_row, objects);
        mInflater = LayoutInflater.from(context);
        mResource = R.layout.task_row;
        this.parentClass = parentClass;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return this.createViewFromResource(mInflater, position, convertView, parent, mResource);
    }

    /**
     * @return View with textview and checkbox for each task item
     */
    private View createViewFromResource(final LayoutInflater inflater, int position,
                                        View convertView, final ViewGroup parent, int resource) {
        final TodoItemData item = getItem(position);

        if (item.isEditClicked()) {
            //Item Edit Mode
            convertView = inflater.inflate(R.layout.edit_task_row, parent, false);

            final EditText editText = convertView.findViewById(R.id.edit_recipe_text);
            TextView timerPrompt = convertView.findViewById(R.id.timer_prompt);
            final RadioButton timerButton = convertView.findViewById(R.id.timer_button);
            EditText timerText = convertView.findViewById(R.id.time_value);

            editText.setText(item.getTitle());
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        item.setTitle(editText.getText().toString());
                    }
                }
            });

            setupTimerRadioButton(timerButton, item, timerText, timerPrompt);

            if (item.requiresClock()) {
                timerButton.setChecked(true);
                timerText.setVisibility(View.VISIBLE);
                timerText.setText(Double.toString(item.getTime() / 60.0));
                timerPrompt.setVisibility(View.GONE);
            }
        } else {
            //Normal Display
            convertView = inflater.inflate(R.layout.task_row, parent, false);
            TextView text = convertView.findViewById(R.id.textView);
            CheckBox check = convertView.findViewById(R.id.check_Box);
            Button button = convertView.findViewById(R.id.start_timer_button);
            setupCheckBox(check, item);
            setupTimerStartButton(button, item);
            setupTextView(text, item, position);

            if (parentClass.getInEditView()) {
                //Hide the check if top view is in edit mode
                check.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                text.setText(item.getTitle());
            } else {
                if(item.isChecked() && item.requiresClock()){
                    button.setVisibility(View.GONE);
                    check.setVisibility(View.VISIBLE);
                } else if (!item.isChecked() && item.requiresClock()) {
                    button.setVisibility(View.VISIBLE);
                    long time = item.getTime();
                    String timeText = createTimeString(time);
                    button.setText(timeText);
                    check.setVisibility(View.GONE);
                } else {
                    check.setVisibility(View.VISIBLE);
                }
            }
        }
        return convertView;
    }

    private String createTimeString(long time) {
        String seconds;
        if (time%60 < 10) {
            seconds = "0" + time%60;
        } else {
            seconds = String.valueOf(time%60);
        }
        return String.format(Locale.US,"%d:%s", time / 60, seconds);
    }

    /**
     * Creates timer button for launching the clock fragment if the item requires a clock
     * @param button
     * @param item
     */
    private void setupTimerStartButton(final Button button, final TodoItemData item) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.requiresClock() && !item.isChecked() && !parentClass.getInEditView()) {
                    //Launch clock activity
                    parentClass.launchClock(item);
                }
            }
        });
    }

    /**
     *  Set on click listener for the requires timer radio button. When clicked it will reveal the
     *  edit text field for adding a time
     *  Creates a focus change listener that saves the value in the edit text
     * @param radioButton
     * @param item
     * @param editTimerText
     * @param timerPrompt
     */
    private void setupTimerRadioButton(final RadioButton radioButton, final TodoItemData item,
                                       final EditText editTimerText, final TextView timerPrompt) {
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerPrompt.getVisibility() == View.VISIBLE) {
                    editTimerText.setVisibility(View.VISIBLE);
                    timerPrompt.setVisibility(View.GONE);
                } else {
                    radioButton.setChecked(false);
                    editTimerText.setVisibility(View.GONE);
                    timerPrompt.setVisibility(View.VISIBLE);
                }
            }
        });

        editTimerText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && radioButton.isChecked() && !editTimerText.getText().toString().isEmpty()) {
                    //Convert from decimal to long
                    String text = editTimerText.getText().toString();
                    if (!text.startsWith("0")) {
                        text = "0".concat(text);
                    }
                    item.setTime((long) (Double.parseDouble(text) * 60.0));
                }
            }
        });
    }

    /**
     * Initializes the checkbox with an onclick listener to connect to the progress bar
     *
     * @param checkBox checkbox to be set up
     * @param item     Recipe Item containing instruction and timer information
     * @return Initialized checkbox
     */
    private void setupCheckBox(final CheckBox checkBox, final TodoItemData item) {
        checkBox.setChecked(item.isChecked());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    parentClass.updateProgress(1);
                    item.setChecked(true);
                } else {
                    parentClass.updateProgress(-1);
                    item.setChecked(false);
                }
            }
        });
    }

    /**
     * Initializes the textview with the position and step of the item
     */
    private void setupTextView(TextView textView, final TodoItemData item, int position) {
        String message = item.getTitle();
        textView.setText(String.format(Locale.US, "%d. %s", position+1, message));
    }
}
