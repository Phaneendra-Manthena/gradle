package com.bhargo.user.controls.standard;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.ImproveHelper;

import static com.bhargo.user.utils.ImproveHelper.showSoftKeyBoard;

public class AutoNumber {

    private static final String TAG = "AutoNumber";
    private final int autoNumberTAG = 0;
    Context context;
    CustomEditText customEditText;
    ControlObject controlObject;
    ImproveHelper improveHelper;
    private LinearLayout linearLayout;
    private CustomTextView tv_tapTextType;
    private CustomEditText ce_TextType;
    private CustomTextView tv_displayName, tv_hint;
    private ImageView iv_mandatory;

    public AutoNumber(Context context, ControlObject controlObject) {
        this.context = context;
        this.controlObject = controlObject;
        improveHelper = new ImproveHelper(context);
        initView();

    }

    public void initView() {
        try {
            linearLayout = new LinearLayout(context);
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            addAutoNUmberStrip(context);
//        TextView textView = new TextView(context);
//        textView.setText("AutoNumber");
//        customEditText = new CustomEditText(context);
//        customEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
//        customEditText.setHint("Enter Number");
//        //        textView.setText(controlObject.getDisplayName());
//        //        customEditText.setTextSize(Float.parseFloat(controlObject.getTextSize()));

//        linearLayout.addView(textView);
//        linearLayout.addView(customEditText);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getAutoNumber() {

        return linearLayout;
    }
    View view;
    public void addAutoNUmberStrip(final Context context) {
        try {
            final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = lInflater.inflate(R.layout.control_text_input, null);
            view.setTag(autoNumberTAG);


            final LinearLayout ll_tap_text = view.findViewById(R.id.ll_tap_text);
            tv_tapTextType = view.findViewById(R.id.tv_tapTextType);
            ce_TextType = view.findViewById(R.id.ce_TextType);
            tv_tapTextType.setText("Tap here to enter Auto Number");
            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);

            ce_TextType.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

            ImageView iv_textTypeImage = view.findViewById(R.id.iv_textTypeImage);
            iv_textTypeImage.setVisibility(View.GONE);
//        iv_textTypeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.icons_percentage_drawable));
            ce_TextType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus) {
                        if (ce_TextType.getText().toString().isEmpty()) {
                            ll_tap_text.setVisibility(View.VISIBLE);
                            ce_TextType.setVisibility(View.GONE);
                        }
                    }
                }
            });


            ll_tap_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_tap_text.setVisibility(View.GONE);
                    ce_TextType.setVisibility(View.VISIBLE);
                    ce_TextType.requestFocus();
                    showSoftKeyBoard(context, ce_TextType);
                }
            });
//        setControlValues();
            linearLayout.addView(view);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "addAutoNUmberStrip", e);
        }
    }

    private void setControlValues() {
        if (controlObject.getDisplayName() != null) {
            tv_displayName.setText(controlObject.getDisplayName());
        }
        if (controlObject.getHint() != null) {
            tv_hint.setText(controlObject.getHint());
        }
        if (controlObject.isNullAllowed()) {
            iv_mandatory.setVisibility(View.VISIBLE);
        }
    }
    public ControlObject getControlObject() {
        return controlObject;
    }


}
