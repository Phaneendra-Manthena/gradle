package com.bhargo.user.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.bhargo.user.R;
import com.bhargo.user.fragments.DataSyncOfflineFormsFragment;
import com.bhargo.user.fragments.DataSyncSaveRequestFragment;
import com.bhargo.user.fragments.ProfileFragment;
import com.bhargo.user.utils.BaseActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class OfflineSyncActivity extends BaseActivity {

    String[] templates = {"Offline Forms", "Save Request"};
    TabLayout tabLayout;
    ViewPager viewPager;
    DataSyncOfflineFormsFragment dataSyncOfflineFormsFragment;
    DataSyncSaveRequestFragment dataSyncSaveRequestFragment;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_sync);
        findViews();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data Sync");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);


        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setTabIconText(tabLayout);

    }

    private void setTabIconText(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setText(templates[0]);
        tabLayout.getTabAt(1).setText(templates[1]);
    }

    private void setUpViewPager(ViewPager viewPager) {
        dataSyncOfflineFormsFragment = new DataSyncOfflineFormsFragment();
        dataSyncSaveRequestFragment = new DataSyncSaveRequestFragment();

        ResumeviewpagerAdapter adapter = new ResumeviewpagerAdapter(getSupportFragmentManager());
        adapter.addFrag(dataSyncOfflineFormsFragment, templates[0]);
        adapter.addFrag(dataSyncSaveRequestFragment, templates[1]);

        viewPager.setAdapter(adapter);
    }

    class ResumeviewpagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> fragTitleList = new ArrayList<>();

        public ResumeviewpagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dataSyncOfflineFormsFragment.updateOfflineDataExistFlag();
    }
}