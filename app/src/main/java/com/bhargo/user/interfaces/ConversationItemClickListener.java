package com.bhargo.user.interfaces;

import android.content.Context;

public interface ConversationItemClickListener {

     void onItemIdClick(Context context, String group_icon, String group_id, String group_name, String session_id, String session_name);
}
