package com.grant.todo.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.grant.todo.todo.TodoFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Grant on 3/18/18.
 */

public class TodoFragmentPagerAdapter extends FragmentStatePagerAdapter {

    Date date;
    private static FragmentManager fragmentManager;

    public TodoFragmentPagerAdapter(FragmentManager fm){
        super(fm);
        fragmentManager = fm;
        date = new Date();
    }

    @Override
    public int getCount() {
        return 30;
    }

    @Override
    public Fragment getItem(int position) {
        Date date = getDate(position);
        String stringdate = new SimpleDateFormat("E, MMMM dd").format(date);

        TodoFragment todoFragment = TodoFragment.newInstance(stringdate, fragmentManager);
        return todoFragment;
    }

    private Date getDate(int position) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, position);
        return cal.getTime();
    }
}
