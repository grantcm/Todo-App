package com.grant.todo.inspect;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.grant.todo.data.TodoItemData;
import com.grant.todo.R;

/**
 * Created by Grant on 3/12/18.
 */

public class InspectTaskActivity extends AppCompatActivity implements OnItemClickedListener {
    private FragmentManager fragmentManager;
    private String title;
    private Fragment currentVisible;
    private int Id;

    public static final String INSPECT_TASK_FRAGMENT = "INSPECT";
    public static final String TITLE = "TITLE";
    public static final String ID = "TASK_UID";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ListTaskFragment listTaskFragment = ListTaskFragment.newInstance(Id);
        currentVisible = listTaskFragment;
        fragmentTransaction.add(R.id.main_container, listTaskFragment, INSPECT_TASK_FRAGMENT);
        fragmentTransaction.commit();
    }

    private void parseIntent() {
        Intent intent = getIntent();
        title = intent.getStringExtra(TITLE);
        Id = intent.getIntExtra(ID, 0);
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
                super.onBackPressed();
            }
        } else if (currentVisible instanceof TimerFragment){
            super.onBackPressed();
            if (((TimerFragment) currentVisible).isFinished()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    currentVisible = fragmentManager.findFragmentByTag(INSPECT_TASK_FRAGMENT);
                    ListTaskFragment listTaskFragment = (ListTaskFragment) currentVisible;
                    listTaskFragment.updateProgress(1);
                    listTaskFragment.updateData();
                }
            } else {
                currentVisible = fragmentManager.findFragmentByTag(INSPECT_TASK_FRAGMENT);
                ListTaskFragment listTaskFragment = (ListTaskFragment) currentVisible;
                listTaskFragment.updateData();
            }
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Launches new clock activity with parameters time and step
     * @param item TodoItem clicked
     */
    public void launchClock(TodoItemData item){
        TimerFragment timerFragment = TimerFragment.newInstance(item.getUid());

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .addToBackStack(INSPECT_TASK_FRAGMENT);
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
    public void onItemClicked(TodoItemData item) {
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
