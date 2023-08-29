package com.bhargo.user.screens;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.ImproveHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiComment_FullView extends AppCompatActivity {

    private static final String TAG = "MultiComment_FullView";
    HashMap<String, List<String>> OutputData;
    String Header = "", SubHeader = "", displayName = "";
    LinearLayout ll_Content;
    ImageView iv_back;
    CustomTextView tv_displayName;
    List<String> Header_list = new ArrayList<String>();
    List<String> SubHeader_list = new ArrayList<String>();
    List<String> Right_list = new ArrayList<String>();
    ImproveHelper improveHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_comment__full_view);

        improveHelper = new ImproveHelper(this);

        ll_Content = findViewById(R.id.ll_Content);
        iv_back = findViewById(R.id.iv_back);
        tv_displayName = findViewById(R.id.tv_displayName);

        Bundle Data = getIntent().getBundleExtra("Data");
        Header_list = (List<String>) Data.getSerializable("Header_list");
        SubHeader_list = (List<String>) Data.getSerializable("SubHeader_list");
        Right_list = (List<String>) Data.getSerializable("Right_list");
        Header = getIntent().getStringExtra("Header");
        SubHeader = getIntent().getStringExtra("SubHeader");
        displayName = getIntent().getStringExtra("DisplayName");

        tv_displayName.setText(displayName);
        SetValues();

        /*if (OutputData.size() != 0) {
            if (OutputData != null) {
                List<String> HeaderValues = OutputData.get(Header);
                List<String> SubHeaderValues = OutputData.get(SubHeader);

                for (int i = 0; i <HeaderValues.size() ; i++) {
                    LayoutInflater linflater1 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = linflater1.inflate(R.layout.item_multicomment_row, null);

                    CustomTextView tv_Header=view.findViewById(R.id.tv_Header);
                    CustomTextView tv_Subhead=view.findViewById(R.id.tv_Subhead);
                    CustomTextView tv_Transaction=view.findViewById(R.id.tv_Transaction);
                    tv_Header.setText(HeaderValues.get(i));
                    tv_Header.setText(HeaderValues.get(i));
                    tv_Subhead.setText(SubHeaderValues.get(i));
                    tv_Transaction.setText(ImproveHelper.gettodaydate());

                    ll_Content.addView(view);
                }

            }
        }*/

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void SetValues() {

        try {
            for (int i = 0; i < Header_list.size(); i++) {
                LayoutInflater linflater1 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = linflater1.inflate(R.layout.item_multicomment_row, null);

                CustomTextView tv_Header = view.findViewById(R.id.tv_Header);
                CustomTextView tv_Subhead = view.findViewById(R.id.tv_Subhead);
                CustomTextView tv_Transaction = view.findViewById(R.id.tv_Transaction);
                tv_Header.setText(Header_list.get(i));
                tv_Subhead.setText(SubHeader_list.get(i));
                tv_Transaction.setText(Right_list.get(i));

                ll_Content.addView(view);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "SetValues", e);
        }

    }

}
