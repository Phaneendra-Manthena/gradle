package com.bhargo.user.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;

import androidx.annotation.RequiresApi;

/** Helper that saves the current window preferences for the Catalog. */
public class WindowPreferencesManager {

    private static final String PREFERENCES_NAME = "window_preferences";
    private static final String KEY_EDGE_TO_EDGE_ENABLED = "edge_to_edge_enabled";
    private static final int EDGE_TO_EDGE_BAR_ALPHA = 128;

    @RequiresApi(VERSION_CODES.LOLLIPOP)
    private static final int EDGE_TO_EDGE_FLAGS =
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

    private final Context context;

    public WindowPreferencesManager(Context context) {
        this.context = context;
    }

    @SuppressWarnings("ApplySharedPref")
    public void toggleEdgeToEdgeEnabled() {
        getSharedPreferences()
                .edit()
                .putBoolean(KEY_EDGE_TO_EDGE_ENABLED, !isEdgeToEdgeEnabled())
                .commit();
    }

    public boolean isEdgeToEdgeEnabled() {
        return getSharedPreferences()
                .getBoolean(KEY_EDGE_TO_EDGE_ENABLED, VERSION.SDK_INT >= VERSION_CODES.Q);
    }




    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}

