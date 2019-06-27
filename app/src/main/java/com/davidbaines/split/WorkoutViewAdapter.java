package com.davidbaines.split;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidbaines.split.dbmanager.DbInterface;

public class WorkoutViewAdapter extends Fragment {
    WorkoutViewPagerAdapter workoutAdapter;
    ViewPager pager;

    public WorkoutViewAdapter() {
        // Required empty public constructor
    }

    public static WorkoutViewFragment newInstance() {
        WorkoutViewFragment fragment = new WorkoutViewFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        /*workoutAdapter = new WorkoutViewPagerAdapter(getChildFragmentManager());
        pager = view.findViewById(R.id.view_pager);
        pager.setAdapter(workoutAdapter);*/

        TabLayout tabLayout = view.findViewById(R.id.tab_id);
        tabLayout.setupWithViewPager(pager);
    }
}

class WorkoutViewPagerAdapter extends FragmentStatePagerAdapter {

    private Object[] days;

    public WorkoutViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public WorkoutViewPagerAdapter(FragmentManager fm, Object[] workouts) {
        super(fm);

        days = workouts;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new WorkoutViewFragment();
        Bundle args = new Bundle();
        args.putString(WorkoutViewFragment.ARG_DAY, (String) days[i]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return days.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (CharSequence) days[position];
    }
}