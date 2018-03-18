package com.grant.todo.InspectPackage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.grant.todo.R;
import com.grant.todo.TodoPackage.TodoItem;

import java.util.ArrayList;

/**
 * Created by Grant on 3/12/18.
 */

public class InspectTaskActivity extends AppCompatActivity implements OnItemClickedListener {
    private FragmentManager fragmentManager;
    private final String INSPECT_TASK_FRAGMENT = "INSPECT";
    private String title;
    private Fragment currentVisible;
    private ArrayList<TodoItem> data;

    public static final String TITLE = "TITLE";
    public static final String STEPS = "STEPS";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ListTaskFragment recipePreviewFragment = ListTaskFragment.newInstance(title, data);
        currentVisible = recipePreviewFragment;
        fragmentTransaction.add(R.id.main_container, recipePreviewFragment, "Inspect_fragment");
        fragmentTransaction.commit();
    }

    private void parseIntent() {
        Intent intent = getIntent();
        title = intent.getStringExtra(TITLE);
        data = intent.getParcelableArrayListExtra(STEPS);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (currentVisible instanceof ListTaskFragment) {
            ListTaskFragment listTaskFragment = (ListTaskFragment) currentVisible;
            if (!listTaskFragment.handleBackPressed()) {
                Intent intent = new Intent();
                intent.putExtra(TITLE,title);
                intent.putExtra(STEPS,data);
                setResult(RESULT_OK, intent);
                finish();
            }
        } else if (currentVisible instanceof TimerFragment){
            super.onBackPressed();
            currentVisible = fragmentManager.findFragmentByTag("Inspect_fragment");
            ListTaskFragment listTaskFragment = (ListTaskFragment) currentVisible;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                listTaskFragment.updateProgress(1);
            }
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Launches new clock activity with parameters time and step
     * @param item TodoItem clicked
     */
    public void launchClock(TodoItem item){
        TimerFragment timerFragment = TimerFragment.newInstance(item);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .addToBackStack("Inspect");
        fragmentTransaction.replace(R.id.main_container, timerFragment);
        fragmentTransaction.commit();
        currentVisible = timerFragment;
    }

    public void onClickAddNewRow(View v) {
        ListTaskFragment fragment = (ListTaskFragment) fragmentManager
                .findFragmentByTag(INSPECT_TASK_FRAGMENT);
        fragment.addNewRow();
    }

    @Override
    public void onItemClicked(TodoItem item) {
        launchClock(item);
    }

    /**
     * Sets logic for the timer button
     * Alternates between Start, Stop, and Return when finished
     * @param view
     */
    public void onClickClockButton(View view){
        TimerFragment timer = (TimerFragment) currentVisible;
        if(!timer.isStarted() && !timer.isFinished()){
            timer.startCountdown();
        } else if (timer.isStarted() && !timer.isFinished()){
            timer.cancelCountdown();
        } else {
            this.onBackPressed();

        }
    }
}
