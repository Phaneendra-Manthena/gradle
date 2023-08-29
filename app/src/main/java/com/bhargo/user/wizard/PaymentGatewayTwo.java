package com.bhargo.user.wizard;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.BaseActivity;

import java.util.ArrayList;

import nk.mobileapps.spinnerlib.SearchableSpinner;

public class PaymentGatewayTwo extends BaseActivity {

    Context context;
    CustomButton btnDC, btnInternet, btnUPI,btn_pay_now;
    LinearLayout ll_insideCards,ll_netBankingSpinner,ll_dc_cards;
    CustomTextView ctPaymentWithHint,cnHint,chnHint,expiryHint;
    CustomEditText ce_upi_id,ce_expiry,ce_cvv;
    SearchableSpinner searchableSpinner_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway_two);
        context = PaymentGatewayTwo.this;
        initializeActionBar();
        enableBackNavigation(true);
        iv_circle_appIcon.setVisibility(View.GONE);
        title.setText("Payment");

        btnDC = findViewById(R.id.btnDC);
        btnInternet = findViewById(R.id.btnInternet);
        btnUPI = findViewById(R.id.btnUPI);
        ll_insideCards = findViewById(R.id.ll_insideCards);
        ll_netBankingSpinner = findViewById(R.id.ll_netBankingSpinner);
        searchableSpinner_main = findViewById(R.id.searchableSpinner_main);
        ctPaymentWithHint = findViewById(R.id.ctPaymentWithHint);
        cnHint = findViewById(R.id.cnHint);
        chnHint = findViewById(R.id.chnHint);
        expiryHint = findViewById(R.id.expiryHint);
        ce_upi_id = findViewById(R.id.ce_upi_id);
        ce_expiry = findViewById(R.id.ce_expiry);
        ce_cvv = findViewById(R.id.ce_cvv);
        ll_dc_cards = findViewById(R.id.ll_dc_cards);
        btn_pay_now = findViewById(R.id.btn_pay_now);
        expiryHint.setText("Expiry (MM/ YY)");
        ce_expiry.setHint("Enter expiry");
        ce_cvv.setHint("Enter CVV");

        searchableSpinner_main.setItems(new ArrayList<>());
        selectors(0);
        btnDC.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                selectors(0);

            }
        });

        btnInternet.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                selectors(1);

            }
        });
        btnUPI.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                selectors(2);

            }
        });
        btn_pay_now.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentGatewayTwo.this,PaymentGatWayThree.class);
                startActivity(intent);

            }
        });
    }

    public void selectors(int i) {

        if (i == 0) {// Debit or  CreditCards
            //Rounded Background
            btnDC.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_bg_blue));
            btnInternet.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_bg_white));
            btnUPI.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_bg_white));
            //Text Color
            btnDC.setTextColor(context.getResources().getColor(R.color.white));
            btnInternet.setTextColor(context.getResources().getColor(R.color.text_color));
            btnUPI.setTextColor(context.getResources().getColor(R.color.text_color));
            //DrawableIcon colors
            btnDC.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.white)));
            btnInternet.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.text_color)));
            btnUPI.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.text_color)));
            //Visibilities
            ll_insideCards.setVisibility(View.VISIBLE);
            ll_netBankingSpinner.setVisibility(View.GONE);
            ce_upi_id.setVisibility(View.GONE);
            //PaymentwithHint
            ctPaymentWithHint.setText("Pay with Debit/ Credit Card");
        } else if (i == 1) { // InternetBanking
            //Rounded Background
            btnInternet.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_bg_blue));
            btnDC.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_bg_white));
            btnUPI.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_bg_white));
            //Text Color
            btnInternet.setTextColor(context.getResources().getColor(R.color.white));
            btnDC.setTextColor(context.getResources().getColor(R.color.text_color));
            btnUPI.setTextColor(context.getResources().getColor(R.color.text_color));
            //DrawableIcon colors
            btnInternet.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.white)));
            btnDC.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.text_color)));
            btnUPI.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.text_color)));
            //Visibilities
            ll_insideCards.setVisibility(View.GONE);
            ll_netBankingSpinner.setVisibility(View.VISIBLE);
            ce_upi_id.setVisibility(View.GONE);

            //PaymentwithHint
            ctPaymentWithHint.setText("Pay with NetBanking");
        } else if (i == 2) { // UPI
            //Rounded Background
            btnUPI.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_bg_blue));
            btnInternet.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_bg_white));
            btnDC.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_bg_white));
            //Text Color
            btnUPI.setTextColor(context.getResources().getColor(R.color.white));
            btnInternet.setTextColor(context.getResources().getColor(R.color.text_color));
            btnDC.setTextColor(context.getResources().getColor(R.color.text_color));
            //DrawableIcon colors
            btnUPI.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.white)));
            btnInternet.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.text_color)));
            btnDC.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.text_color)));
            //Visibilities
            ll_insideCards.setVisibility(View.GONE);
            ll_netBankingSpinner.setVisibility(View.GONE);
            ce_upi_id.setVisibility(View.VISIBLE);
            //PaymentwithHint
            ctPaymentWithHint.setText("Pay with UPI ID");
        }
    }

}