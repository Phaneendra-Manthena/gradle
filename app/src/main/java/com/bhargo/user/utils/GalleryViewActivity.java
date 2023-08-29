package com.bhargo.user.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.viewpager.widget.ViewPager;

import com.bhargo.user.R;
import com.bhargo.user.adapters.GalleryViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GalleryViewActivity extends BaseActivity {

    private static final String TAG = "GalleryViewActivity";
    String displayName, strListImagess;
    List<String> strListImages;
    Context context;
    ImproveHelper improveHelper;
    private ViewPager viewPager;
    private ImageButton leftNav, rightNav;
    private List<String> stringListImages;
    private ImageButton left_nav, right_nav;
    GalleryViewPagerAdapter galleryViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view);
        context = this;
        initializeActionBar();
        title.setText(displayName);
        iv_circle_appIcon.setVisibility(View.GONE);
        enableBackNavigation(true);
        improveHelper = new ImproveHelper();
        try {
            Intent intent = getIntent();
            if (intent != null) {
                strListImages = new ArrayList<String>();
                displayName = intent.getStringExtra("displayName");
//                strListImages = intent.getStringExtra("stringListImages");
                strListImages = intent.getStringArrayListExtra("stringListImages");
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getIntentValues", e);
        }
        initView();


    }

    private void initView() {
        try {
//            stringListImages = new ArrayList<>();
//            if (strListImages != null) {
//                String[] strSplitMultipleImages = strListImages.split(",");
//                if (strSplitMultipleImages.length != 0) {
//                    for (int i = 0; i < strSplitMultipleImages.length; i++) {
//                        if (!strSplitMultipleImages[i].isEmpty()) {
//                            String strReplaceSpaces = strSplitMultipleImages[i].replaceAll(" ", "%20");
//                            String strRemovedDollars = strReplaceSpaces.replaceAll("\\$", "");
//                            String substringMain = strRemovedDollars;
//                            if (strRemovedDollars.startsWith("%20")) {
//                                substringMain = strRemovedDollars.substring(3);
//                            }
//                            stringListImages.add(substringMain);
//                        }
////                        stringListImages.add(strSplitMultipleImages[i]);
//                    }
//                }
//            }

            viewPager = findViewById(R.id.viewpager);
            left_nav = findViewById(R.id.left_nav);
            right_nav = findViewById(R.id.right_nav);
//            viewPager.setAdapter(new GalleryViewPagerAdapter(context, stringListImages));
            if(strListImages != null && strListImages.size()>0) {
                Log.d("strListImages_size",""+strListImages.size());
                left_nav.setVisibility(View.GONE);
                galleryViewPagerAdapter = new GalleryViewPagerAdapter(context, strListImages);
                viewPager.setAdapter(galleryViewPagerAdapter);
// Images left navigation
                left_nav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tab = viewPager.getCurrentItem();
                        if (tab > 0) {
                            tab--;
                            viewPager.setCurrentItem(tab);
                        } else if (tab == 0) {
                            viewPager.setCurrentItem(tab);
                        }
                    }
                });

                // Images right navigatin
                right_nav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tab = viewPager.getCurrentItem();
                        tab++;
                        viewPager.setCurrentItem(tab);
                    }
                });


                viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
//                        Log.d(TAG, "onPageSelectedGV: " + position + " - " + (viewPager.getAdapter().getCount() - 1));
                        if (position == 0) {
                            left_nav.setVisibility(View.GONE);
                        } else {
                            left_nav.setVisibility(View.VISIBLE);
                        }
                        if (position == (strListImages.size() - 1)) {
                            rightNav.setVisibility(View.GONE);
                        } else {
                            rightNav.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        }catch (Exception e){
            Log.getStackTraceString(e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}