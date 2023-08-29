package com.bhargo.user.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.ListAdapterSample;
import com.bhargo.user.pojos.SampleListModel;

import java.util.ArrayList;

public class SampleListActivity extends AppCompatActivity {
    ListView list;
    ListAdapterSample adapter;
    public SampleListActivity CustomListView = null;
    public ArrayList<SampleListModel> CustomListViewValuesArr = new ArrayList<SampleListModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_list);

        CustomListView = this;

        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        setListData();

        Resources res = getResources();
        list = (ListView) findViewById(R.id.list);  // List defined in XML ( See Below )

        /**************** Create Custom Adapter *********/
        adapter = new ListAdapterSample(CustomListView, CustomListViewValuesArr, res);
        list.setAdapter(adapter);
    }

    public void setListData() {

        for (int i = 0; i < 11; i++) {

            final SampleListModel sched = new SampleListModel();

            /******* Firstly take data in model object ******/
            sched.setHeading("Company " + i);


            /******** Take Model Object in ArrayList **********/
            CustomListViewValuesArr.add(sched);
        }

    }
}