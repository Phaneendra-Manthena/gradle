package com.bhargo.user.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bhargo.user.R;
import com.bhargo.user.adapters.ViewPagerAdapter;
import com.bhargo.user.screens.BottomNavigationActivity;
import com.bhargo.user.screens.CreateNewTaskActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import static com.bhargo.user.screens.BottomNavigationActivity.iv_tasksFilter;
import static com.bhargo.user.utils.AppConstants.editTaskAppDataModelsList;
import static com.bhargo.user.utils.AppConstants.editTaskContentDataModelsList;
import static com.bhargo.user.utils.AppConstants.editTaskGroupList;
import static com.bhargo.user.utils.AppConstants.editTaskIndividualList;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment implements BottomNavigationActivity.OnBackPressedListener {

    private static final String TAG = "TasksFragment";
    public String taskStatus = "0";
    FloatingActionButton fab_createTask;
    String refreshData;
    ImproveHelper improveHelper;
    private View rootView;
    private ViewGroup viewGroup;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private ViewPagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;
    private Fragment currentFragment;

    /**/
    public TasksFragment(String taskStatus, String refreshData) {
        // Required empty public constructor
        this.taskStatus = taskStatus;
        this.refreshData = refreshData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppConstants.IS_FORM_THEME =  true;
        setBhargoTheme(getActivity(),AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
//        getContext().getTheme().applyStyle(setBhargoThemeNew(AppConstants.THEME,AppConstants.IS_FORM_THEME,AppConstants.FORM_THEME), true);
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
        viewGroup = container;
        improveHelper = new ImproveHelper(getActivity());
//        BottomNavigationActivity.ct_FragmentTitle.setText(getActivity().getResources().getString(R.string.tasks));
        String[] TabsData=AppConstants.WINDOWS_AVAILABLE.split("\\|");
        BottomNavigationActivity.ct_FragmentTitle.setText(TabsData[3].split("\\^")[2]);

        ((BottomNavigationActivity) getActivity()).setOnBackPressedListener(this);
        mViewPager = rootView.findViewById(R.id.viewpager);
        mTabLayout = rootView.findViewById(R.id.tabs);
        fab_createTask = rootView.findViewById(R.id.fab_createTask);

        setViewPager(2);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            //noinspection ConstantConditions
            TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            Typeface typeface_bold = Typeface.createFromAsset(getActivity().getAssets(), getActivity().getResources().getString(R.string.font_satoshi_bold));
            tv.setTypeface(typeface_bold);
            mTabLayout.getTabAt(i).setCustomView(tv);
        }

        fab_createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstants.TASK_ATTEMPTS_BY = "0";
                clearAllLists();
                startActivity(new Intent(getActivity(), CreateNewTaskActivity.class));

            }
        });


        return rootView;
    }

    @Override
    public void onFragmentBackPress() {
        ImproveHelper.replaceFragment(rootView, new AppsListFragment(), "AppsListFragment");
    }

    private void setViewPager(int noOfPagers) {
        try {
            mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), getActivity(), noOfPagers, taskStatus, refreshData);
            mViewPager.setAdapter(mViewPagerAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
            mViewPager.setCurrentItem(0);
            if (AppConstants.TASK_REFRESH.equalsIgnoreCase("InTaskRefresh")) {
                mViewPager.setCurrentItem(0);
            } else if (AppConstants.TASK_REFRESH.equalsIgnoreCase("OutTaskRefresh")) {
                mViewPager.setCurrentItem(1);

            }

            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    Log.d(TAG, "addOnPageChangeListener: " + position + "," + taskStatus);
                    if (position == 0) {

                        AppConstants.TASK_REFRESH = "InTaskRefresh";

                        iv_tasksFilter.setVisibility(View.VISIBLE);

                    } else {

                        AppConstants.TASK_REFRESH = "OutTaskRefresh";

                        iv_tasksFilter.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "setViewPager", e);
        }
    }


    public void clearAllLists() {
        try {
            if (editTaskAppDataModelsList != null) {
                editTaskAppDataModelsList.clear();
            }
            if (editTaskContentDataModelsList != null) {
                editTaskContentDataModelsList.clear();
            }
            if (editTaskGroupList != null) {
                editTaskGroupList.clear();
            }

            if (editTaskIndividualList != null) {
                editTaskIndividualList.clear();
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "clearAllLists", e);
        }
    }
}
