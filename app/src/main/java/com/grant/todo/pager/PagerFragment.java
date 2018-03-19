package com.grant.todo.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grant.todo.R;

/**
 * Created by Grant on 3/18/18.
 */

public class PagerFragment extends Fragment {

    private ViewPager viewPager;
    private TodoFragmentPagerAdapter pagerAdapter;

    public static PagerFragment newInstance() {
        return new PagerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.pager_fragment, container, false);
        viewPager = view.findViewById(R.id.view_pager);
        pagerAdapter = new TodoFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        return view;
    }
}
