package com.bhargo.user.screens;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.OrganisationsAdapter;
import com.bhargo.user.interfaces.ItemClickListener;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.SessionManager;

public class OrgnisationsListActivity extends AppCompatActivity implements ItemClickListener {

    ImproveDataBase improveDataBase;
    private RecyclerView rv_OrganisationList;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orgnisations_list);

        sessionManager = new SessionManager(this);

        improveDataBase = new ImproveDataBase(this);
        rv_OrganisationList = findViewById(R.id.rv_OrganisationList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_OrganisationList.setLayoutManager(linearLayoutManager);

        improveDataBase.getDataFromOrganisationTable(sessionManager.getUserDataFromSession().getUserID());

        OrganisationsAdapter organisationsAdapter = new OrganisationsAdapter(this, improveDataBase.getDataFromOrganisationTable(sessionManager.getUserDataFromSession().getUserID()));
        rv_OrganisationList.setAdapter(organisationsAdapter);
        organisationsAdapter.setCustomClickListener(this);

    }


    @Override
    public void onCustomClick(Context context, View view, int position, String orgId) {

//        Intent intent = new Intent(OrgnisationsListActivity.this, AppsListActivity.class);
//
//        startActivity(intent);
//
//        finish();
    }
}
