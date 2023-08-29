package com.bhargo.user.wizard;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.utils.BaseActivity;

import java.util.ArrayList;

import nk.mobileapps.spinnerlib.SearchableSpinner;

public class PaymentGatWayThree extends BaseActivity {

    Context context;
    LinearLayout ll_three_dc,ll_three_netBanking,ll_three_upi,ll_insideCards,ll_netBankingSpinner;
    CustomEditText ce_upi_id;
    ImageView iv_dc_open,iv_ib_open,iv_upi_open;
    SearchableSpinner searchableSpinner_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gate_way_three);
        context = PaymentGatWayThree.this;
        initializeActionBar();
        enableBackNavigation(true);
        iv_circle_appIcon.setVisibility(View.GONE);
        title.setText("Payment");

        ll_three_dc = findViewById(R.id.ll_three_dc);
        ll_three_netBanking = findViewById(R.id.ll_three_netBanking);
        ll_three_upi = findViewById(R.id.ll_three_upi);
        ll_insideCards = findViewById(R.id.ll_insideCards);
        ll_netBankingSpinner = findViewById(R.id.ll_netBankingSpinner);
        searchableSpinner_main = findViewById(R.id.searchableSpinner_main);
        ce_upi_id = findViewById(R.id.ce_upi_id);
        iv_dc_open = findViewById(R.id.iv_dc_open);
        iv_ib_open = findViewById(R.id.iv_ib_open);
        iv_upi_open = findViewById(R.id.iv_upi_open);

        searchableSpinner_main.setItems(new ArrayList<>());

        ll_three_dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectors(0);

            }
        });
        ll_three_netBanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectors(1);

            }
        });
        ll_three_upi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectors(2);

            }
        });

    }

    public void selectors(int i){
        if(i==0){
            //Visibilities
            ll_insideCards.setVisibility(View.VISIBLE);
            ll_netBankingSpinner.setVisibility(View.GONE);
            ce_upi_id.setVisibility(View.GONE);
            // Arrows
            iv_dc_open.setImageResource(R.drawable.ic_up_arrow_default);
            iv_ib_open.setImageResource(R.drawable.ic_down_arrow_default);
            iv_upi_open.setImageResource(R.drawable.ic_down_arrow_default);

        }else if(i==1){
            //Visibilities
            ll_insideCards.setVisibility(View.GONE);
            ll_netBankingSpinner.setVisibility(View.VISIBLE);
            ce_upi_id.setVisibility(View.GONE);
            // Arrows
            iv_dc_open.setImageResource(R.drawable.ic_down_arrow_default);
            iv_ib_open.setImageResource(R.drawable.ic_up_arrow_default);
            iv_upi_open.setImageResource(R.drawable.ic_down_arrow_default);

        }else if(i==2){
            //Visibilities
            ll_insideCards.setVisibility(View.GONE);
            ll_netBankingSpinner.setVisibility(View.GONE);
            ce_upi_id.setVisibility(View.VISIBLE);
            // Arrows
            iv_dc_open.setImageResource(R.drawable.ic_down_arrow_default);
            iv_ib_open.setImageResource(R.drawable.ic_down_arrow_default);
            iv_upi_open.setImageResource(R.drawable.ic_up_arrow_default);

        }
    }

}