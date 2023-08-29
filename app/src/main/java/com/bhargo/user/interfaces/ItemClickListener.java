package com.bhargo.user.interfaces;

import android.content.Context;
import android.view.View;

public interface ItemClickListener {

    void onCustomClick(Context context,View view, int position, String orgId);

}
