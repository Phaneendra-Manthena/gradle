package com.bhargo.user.navigation;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.utils.BaseActivity;

import java.util.List;

public class MenuAsFormActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_as_form);
        RecyclerView rv_list = findViewById(R.id.rv_list);
        NavMenu navMenu = (NavMenu) getIntent().getSerializableExtra("navMenu");
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        NavMenuAdapter adapter = new NavMenuAdapter(navMenu.getMenuItemList(), this, new NavMenuAdapter.MenuClickListener() {
            @Override
            public void onMenuClick(View view, List<NavMenuItem> menuItemList, int pos) {

            }

            @Override
            public void onSubMenuClick(View view, List<NavMenuItem> menuItemList, int subMenuPos, int menuPos) {

            }
        });

        rv_list.setAdapter(adapter);
        rv_list.setHasFixedSize(true);
    }
}
