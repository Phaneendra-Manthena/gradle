package com.bhargo.user.interfaces;

import android.content.Context;
import android.view.View;

public interface ItemClickListenerAdvanced {

    void onCustomClick(Context context, View view, int position, String orgId,String status);
}
