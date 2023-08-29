package com.bhargo.user.wizard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomRadioButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.BaseActivity;

import java.util.ArrayList;

import nk.mobileapps.spinnerlib.SearchableSpinner;

public class PaymentGatewayOne extends BaseActivity {

    Context context;
    CustomRadioButton rbDebitCreditCards,rbNetBanking,rbUPI;
    LinearLayout ll_insideCards,ll_netBankingSpinner,ll_dc_cards;
    SearchableSpinner searchableSpinner_main;
    CustomEditText ce_upi_id;
    CustomButton btn_pay_now;
    CustomTextView cnHint,chnHint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway_one);
        context = PaymentGatewayOne.this;
        initializeActionBar();
        enableBackNavigation(true);
        iv_circle_appIcon.setVisibility(View.GONE);
        title.setText("Payment");

        rbDebitCreditCards = findViewById(R.id.rbDebitCreditCards);
        rbNetBanking = findViewById(R.id.rbNetBanking);
        rbUPI = findViewById(R.id.rbUPI);
        ll_insideCards = findViewById(R.id.ll_insideCards);
        ll_netBankingSpinner = findViewById(R.id.ll_netBankingSpinner);
        searchableSpinner_main = findViewById(R.id.searchableSpinner_main);
        ce_upi_id = findViewById(R.id.ce_upi_id);
        btn_pay_now = findViewById(R.id.btn_pay_now);
        cnHint = findViewById(R.id.cnHint);
        chnHint = findViewById(R.id.chnHint);
        ll_dc_cards = findViewById(R.id.ll_dc_cards);
        cnHint.setVisibility(View.GONE);
        chnHint.setVisibility(View.GONE);
        ll_dc_cards.setVisibility(View.GONE);

        searchableSpinner_main.setItems(new ArrayList<>());
        selectors(0);

        rbDebitCreditCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectors(0);
            }
        });
        rbNetBanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectors(1);
            }
        });
        rbUPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectors(2);
            }
        });

        btn_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentGatewayOne.this, PaymentGatewayTwo.class);
                startActivity(intent);
            }
        });

    }

    private void selectors(int i) {
        if(i==0){
            ll_insideCards.setVisibility(View.VISIBLE);
            ll_netBankingSpinner.setVisibility(View.GONE);
            searchableSpinner_main.setVisibility(View.GONE);
            ce_upi_id.setVisibility(View.GONE);
            rbNetBanking.setChecked(false);
            rbUPI.setChecked(false);
        }else if(i==1){
            ll_insideCards.setVisibility(View.GONE);
            ll_netBankingSpinner.setVisibility(View.VISIBLE);
            searchableSpinner_main.setVisibility(View.VISIBLE);
            ce_upi_id.setVisibility(View.GONE);
            rbDebitCreditCards.setChecked(false);
            rbUPI.setChecked(false);
        }else if(i==2){
            ll_insideCards.setVisibility(View.GONE);
            ll_netBankingSpinner.setVisibility(View.GONE);
            searchableSpinner_main.setVisibility(View.GONE);
            ce_upi_id.setVisibility(View.VISIBLE);
            rbDebitCreditCards.setChecked(false);
            rbNetBanking.setChecked(false);
        }
    }
}