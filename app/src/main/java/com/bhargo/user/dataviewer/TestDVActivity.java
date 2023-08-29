package com.bhargo.user.dataviewer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;

public class TestDVActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dv_timeline);

        RecyclerView rv_tl = findViewById(R.id.rv_timeline);
        rv_tl.setLayoutManager(new LinearLayoutManager(this));
        TimeLineAdapter adapter = new TimeLineAdapter(this);
        rv_tl.setAdapter(adapter);
    }
}
