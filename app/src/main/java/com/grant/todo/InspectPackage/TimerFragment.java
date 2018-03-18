package com.grant.todo.InspectPackage;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grant.todo.R;
import com.grant.todo.TodoPackage.TodoItem;

/**
 * Created by Grant on 3/13/18.
 */

public class TimerFragment extends Fragment {
    private TextView clock;
    private TextView instructions;
    private long length;
    private long remainingTime = 0;
    private final long conversion = 1000;
    private final long minutesConversion = 60;
    private boolean started = false;
    private boolean finished = false;
    private String message;
    private CountDownTimer countDownTimer;
    private Button button;
    private TodoItem referenceObject;
    private View progressBar;
    private View unfinishedProgressBar;
    private RelativeLayout timerContainer;

    public final static String ITEM = "ITEM";

    public static TimerFragment newInstance(TodoItem item) {
        TimerFragment timerFragment = new TimerFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ITEM, item);
        timerFragment.setArguments(arguments);
        return timerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle argument = getArguments();
        referenceObject = argument.getParcelable(ITEM);
        length = referenceObject.getTime();
        remainingTime = length;
        message = referenceObject.getText();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clock_layout, container, false);
        clock = view.findViewById(R.id.textClock);
        instructions = view.findViewById(R.id.instructions);
        button = view.findViewById(R.id.startButton);
        progressBar = view.findViewById(R.id.progress_bar_timer);
        unfinishedProgressBar = view.findViewById(R.id.progress_bar_unfinished);
        timerContainer = view.findViewById(R.id.timer_container);
        instructions.setText(message);
        clock.setText(convertToMinuteTimer(length));
        return view;
    }

    private String convertToMinuteTimer(long time) {
        String minutes, seconds;
        if (time < 60) {
            minutes = "";
        } else {
            minutes = Long.toString(time / minutesConversion);
        }
        seconds = Long.toString(time % minutesConversion);
        if (seconds.equals("0")) {
            seconds = "00";
        }
        return minutes.concat(":").concat(seconds);
    }

    /**
     * Initiates countdown timer object and sets up logic for finish
     */
    public void startCountdown() {
        button.setText("Stop");
        started = true;
        countDownTimer = new CountDownTimer(remainingTime * conversion, conversion) {
            public void onTick(long timeLeft) {
                clock.setText(convertToMinuteTimer(timeLeft / conversion));
                remainingTime = timeLeft / conversion;
                float percent = (float) remainingTime / (float) length;
                LinearLayout.LayoutParams bar1params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, percent);
                LinearLayout.LayoutParams bar2params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 1 - percent);
                progressBar.setLayoutParams(bar1params);
                unfinishedProgressBar.setLayoutParams(bar2params);
                timerContainer.bringToFront();
            }

            public void onFinish() {
                clock.setText("Done");
                clock.setTextSize(100);
                finished = true;
                button.setText("Return");
                referenceObject.setChecked(true);
            }
        };

        countDownTimer.start();
    }

    public void cancelCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            button.setText("Start");
            started = false;
        }
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isFinished() {
        return finished;
    }
}
