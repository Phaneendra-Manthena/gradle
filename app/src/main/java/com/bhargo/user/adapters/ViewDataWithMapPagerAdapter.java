package com.bhargo.user.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bhargo.user.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class ViewDataWithMapPagerAdapter extends FragmentStateAdapter {

    Context context;
    JSONArray jsonArray;
    private ArrayList<Fragment> arrayList = new ArrayList<>();

    public ViewDataWithMapPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = arrayList.get(0);
                break;
            case 1:
                fragment = arrayList.get(1);
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public void addFragment(Fragment fragment) {
        arrayList.add(fragment);
    }



}