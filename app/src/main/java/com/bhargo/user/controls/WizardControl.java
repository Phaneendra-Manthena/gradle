package com.bhargo.user.controls;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomRadioButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.ImproveHelper;

import java.util.ArrayList;

import nk.mobileapps.spinnerlib.SearchableSpinner;

public class WizardControl {

    private static final String TAG = "TextInput";
    Activity context;
    ControlObject controlObject;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    ImproveHelper improveHelper;
    private View rView;
    // One
    CustomRadioButton rbDebitCreditCards, rbNetBanking, rbUPI;
    LinearLayout ll_insideCards, ll_netBankingSpinner, ll_dc_cards;
    SearchableSpinner searchableSpinner_main;
    CustomEditText ce_upi_id;
    CustomButton btn_pay_now;
    CustomTextView cnHint, chnHint;
    // Two
    CustomButton btnDC, btnInternet, btnUPI;
    CustomTextView ctPaymentWithHint, expiryHint;
    CustomEditText ce_expiry, ce_cvv;
    private LinearLayout linearLayout;
    // Three
    LinearLayout ll_three_dc,ll_three_netBanking,ll_three_upi;
    ImageView iv_dc_open,iv_ib_open,iv_upi_open;


    public WizardControl(Activity context, ControlObject controlObject, boolean subformFlag, int subformPos, String subformName) {
        this.context = context;
        this.controlObject = controlObject;
        SubformFlag = subformFlag;
        SubformPos = subformPos;
        SubformName = subformName;
        improveHelper = new ImproveHelper(context);
        initViews();
    }

    public WizardControl() {
        initViews();
    }

    private void initViews() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rView = lInflater.inflate(R.layout.activity_payment_gateway_one, null);
//            rView = lInflater.inflate(R.layout.activity_payment_gateway_two, null);
//            rView = lInflater.inflate(R.layout.activity_payment_gate_way_three, null);

            // PaymentGateWayOne Initialisations
            rbDebitCreditCards = rView.findViewById(R.id.rbDebitCreditCards);
            rbNetBanking = rView.findViewById(R.id.rbNetBanking);
            rbUPI = rView.findViewById(R.id.rbUPI);
            ll_insideCards = rView.findViewById(R.id.ll_insideCards);
            ll_netBankingSpinner = rView.findViewById(R.id.ll_netBankingSpinner);
            searchableSpinner_main = rView.findViewById(R.id.searchableSpinner_main);
            ce_upi_id = rView.findViewById(R.id.ce_upi_id);
            btn_pay_now = rView.findViewById(R.id.btn_pay_now);
            cnHint = rView.findViewById(R.id.cnHint);
            chnHint = rView.findViewById(R.id.chnHint);
            ll_dc_cards = rView.findViewById(R.id.ll_dc_cards);

            searchableSpinner_main.setItems(new ArrayList<>());

            // PaymentGateWayTwo  Initialisations
            btnDC = rView.findViewById(R.id.btnDC);
            btnInternet = rView.findViewById(R.id.btnInternet);
            btnUPI = rView.findViewById(R.id.btnUPI);
            ctPaymentWithHint = rView.findViewById(R.id.ctPaymentWithHint);
            expiryHint = rView.findViewById(R.id.expiryHint);
            ce_expiry = rView.findViewById(R.id.ce_expiry);
            ce_cvv = rView.findViewById(R.id.ce_cvv);

            // PaymentGateWayThree  Initialisations
            ll_three_dc = rView.findViewById(R.id.ll_three_dc);
            ll_three_netBanking = rView.findViewById(R.id.ll_three_netBanking);
            ll_three_upi = rView.findViewById(R.id.ll_three_upi);
            iv_dc_open = rView.findViewById(R.id.iv_dc_open);
            iv_ib_open = rView.findViewById(R.id.iv_ib_open);
            iv_upi_open = rView.findViewById(R.id.iv_upi_open);


            //Default loadings
            selectorsOne(0);
//            selectorsTwo(0);
//            selectorsThree(0);


            // PaymentGateWayOne Clicks
            rbDebitCreditCards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectorsOne(0);
                }
            });
            rbNetBanking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectorsOne(1);
                }
            });
            rbUPI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectorsOne(2);
                }
            });


            // PaymentGateWayTwo Clicks
            btnDC.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    selectorsTwo(0);
                }
            });
            btnInternet.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    selectorsTwo(1);
                }
            });
            btnUPI.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    selectorsTwo(2);
                }
            });

            // PaymentGateWayThere Clicks
            ll_three_dc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectorsThree(0);

                }
            });
            ll_three_netBanking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectorsThree(1);

                }
            });
            ll_three_upi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectorsThree(2);

                }
            });

            btn_pay_now.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(PaymentGatewayTwo.this, PaymentGatWayThree.class);
//                    startActivity(intent);

                }
            });


            linearLayout.addView(rView);

        } catch (Exception e) {
            Log.d(TAG, "initViews: " + e);
        }
    }

    private void selectorsOne(int i) {
        cnHint.setVisibility(View.GONE);
        chnHint.setVisibility(View.GONE);
        ll_dc_cards.setVisibility(View.GONE);
        if (i == 0) {
            ll_insideCards.setVisibility(View.VISIBLE);
            ll_netBankingSpinner.setVisibility(View.GONE);
            searchableSpinner_main.setVisibility(View.GONE);
            ce_upi_id.setVisibility(View.GONE);
            rbNetBanking.setChecked(false);
            rbUPI.setChecked(false);
        } else if (i == 1) {
            ll_insideCards.setVisibility(View.GONE);
            ll_netBankingSpinner.setVisibility(View.VISIBLE);
            searchableSpinner_main.setVisibility(View.VISIBLE);
            ce_upi_id.setVisibility(View.GONE);
            rbDebitCreditCards.setChecked(false);
            rbUPI.setChecked(false);
        } else if (i == 2) {
            ll_insideCards.setVisibility(View.GONE);
            ll_netBankingSpinner.setVisibility(View.GONE);
            searchableSpinner_main.setVisibility(View.GONE);
            ce_upi_id.setVisibility(View.VISIBLE);
            rbDebitCreditCards.setChecked(false);
            rbNetBanking.setChecked(false);
        }
    }

    public void selectorsTwo(int i) {
        expiryHint.setText("Expiry (MM/ YY)");
        ce_expiry.setHint("Enter expiry");
        ce_cvv.setHint("Enter CVV");
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
            btnDC.setCompoundDrawableTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
            btnInternet.setCompoundDrawableTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.text_color)));
            btnUPI.setCompoundDrawableTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.text_color)));
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
            btnInternet.setCompoundDrawableTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
            btnDC.setCompoundDrawableTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.text_color)));
            btnUPI.setCompoundDrawableTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.text_color)));
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
            btnUPI.setCompoundDrawableTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
            btnInternet.setCompoundDrawableTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.text_color)));
            btnDC.setCompoundDrawableTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.text_color)));
            //Visibilities
            ll_insideCards.setVisibility(View.GONE);
            ll_netBankingSpinner.setVisibility(View.GONE);
            ce_upi_id.setVisibility(View.VISIBLE);
            //PaymentwithHint
            ctPaymentWithHint.setText("Pay with UPI ID");
        }
    }

    public void selectorsThree(int i){
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

    public LinearLayout getWizardLayout() {
        return linearLayout;
    }

}
