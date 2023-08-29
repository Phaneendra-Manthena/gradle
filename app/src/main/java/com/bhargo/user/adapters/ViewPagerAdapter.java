package com.bhargo.user.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bhargo.user.R;
import com.bhargo.user.fragments.InTasksFragment;
import com.bhargo.user.fragments.OutTasksFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static int TAB_COUNT = 2;
    Context context;
    String taskStatus = "0", refreshData;

    public ViewPagerAdapter(FragmentManager fm, Context context, int TAB_COUNT, String taskStatus, String refreshData) {
        super(fm);
        this.context = context;
        ViewPagerAdapter.TAB_COUNT = TAB_COUNT;
        this.taskStatus = taskStatus;
        this.refreshData = refreshData;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                InTasksFragment inTasksFragment = new InTasksFragment(taskStatus,refreshData);
                return inTasksFragment;

            case 1:

                OutTasksFragment outTasksFragment = new OutTasksFragment(refreshData);
                return outTasksFragment;

        }

        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return context.getResources().getString(R.string.in_tasks);
//                return R.string.properties;

            case 1:
                return context.getResources().getString(R.string.out_tasks);
//                return context.getResources().getString(R.string.on_focus_event);


        }

        return super.getPageTitle(position);
    }

//    public Fragment openFragment(Fragment fragment, String strTag) {
//
////        fragManager = getSupportFragmentManager();
//        FragmentTransaction fragTrans = fragManager.beginTransaction();
//        fragTrans.replace(R.id.container, fragment, strTag);
//        fragTrans.commit();
//        return fragment;
//    }


}