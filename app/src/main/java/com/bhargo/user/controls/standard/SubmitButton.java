package com.bhargo.user.controls.standard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.TextVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.SessionManager;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettingsButton;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import org.json.JSONObject;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;

public class SubmitButton implements UIVariables, TextVariables {
    private static final String TAG = "SubmitButton";
    private final int ButtonTAG = 0;
    Context context;
    CustomButton customButton;
    ControlObject controlObject;
    ImageView iv_main;
    ImproveHelper improveHelper;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName, appName;
    SessionManager sessionManager;
    private LinearLayout linearLayout, ll_button, ll_btnMain;
    private CustomTextView tv_displayName, tv_hint,ct_showText;
    private ImageView iv_mandatory;
    private View view;
    private CustomButton btn_main;
    String SITE_KEY = "6LeVKPUdAAAAANEq7Rh3-Lnh0zfA2UO-5GzfX4za";
    String SECRET_KEY = "6LeVKPUdAAAAAL2PmzRJTwMZextiNkDGtoN0gLQt";
boolean isCaptchaRequired;
    public SubmitButton(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName, String appName,boolean isCaptchaRequired) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        this.isCaptchaRequired = isCaptchaRequired;
        this.appName = appName.replaceAll(" ", "_");
        improveHelper = new ImproveHelper(context);
        initView();

    }

    public void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayout.setLayoutParams(params);
            addButtonStrip(context);
//setParamsMatchParent(linearLayout);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getButton() {

        return linearLayout;
    }

    public View getButtonView() {

        return view;
    }

    public View getll_button() {

        return ll_button;
    }

    public View getbtn_main() {

        return btn_main;
    }

    public View getll_btnMain() {

        return ll_btnMain;
    }

    public View getiv_main() {

        return iv_main;
    }

    public void addButtonStrip(final Context context) {
        try {
            sessionManager = new SessionManager(context);

            final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = lInflater.inflate(R.layout.control_submitbutton, null);
            view.setTag(ButtonTAG);
//        setParamsMatchParent(view);
            btn_main = view.findViewById(R.id.btn_main);
            ll_button = view.findViewById(R.id.ll_button);
            iv_main = view.findViewById(R.id.iv_main);
            ll_btnMain = view.findViewById(R.id.ll_btnMain);
//        setParamsMatchParent(iv_main);
//        tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            ct_showText = view.findViewById(R.id.ct_showText);

            btn_main.setTag(controlObject.getControlName());
            ll_button.setTag(controlObject.getControlName());

            btn_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isCaptchaRequired){verifyCaptcha();}else{((MainActivity) context).mCreateControlsFormAPI(view);}


                /*if (controlObject.isOnClickEventExists()&&!AppConstants.Initialize_Flag) {
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.EventFrom_subformOrNot=SubformFlag;
                        if(SubformFlag){
                            AppConstants.SF_Container_position = SubformPos;
                            AppConstants.Current_ClickorChangeTagName=SubformName;
                        }
                        AppConstants.GlobalObjects.setCurrent_GPS("");
                        ((MainActivity) context).ClickEvent(view);
                    }
                }*/
                }
            });

            ll_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isCaptchaRequired){verifyCaptcha();}else{((MainActivity) context).mCreateControlsFormAPI(view);}

                /*if (controlObject.isOnClickEventExists()&&!AppConstants.Initialize_Flag) {
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.EventFrom_subformOrNot=SubformFlag;
                        if(SubformFlag){
                            AppConstants.SF_Container_position = SubformPos;
                            AppConstants.Current_ClickorChangeTagName=SubformName;

                        }
                        AppConstants.GlobalObjects.setCurrent_GPS("");
                        ((MainActivity) context).ClickEvent(view);
                    }
                }*/
                }
            });

//        btn_main.setBackgroundResource(R.drawable.button_shape_rectangle);
            setControlValues();

            if (controlObject.getShape() != null) {
                if (controlObject.getShape().equalsIgnoreCase("Round")) {
                    btn_main.setBackgroundResource(R.drawable.button_shape_round);
                    iv_main.setBackgroundResource(R.drawable.button_shape_round);
                } else if (controlObject.getShape().equalsIgnoreCase("Rectangle")) {
                    btn_main.setBackgroundResource(R.drawable.button_shape_rectangle);
                    iv_main.setBackgroundResource(R.drawable.button_shape_rectangle);
                } else if (controlObject.getShape().equalsIgnoreCase("Rounded Rectangle")) {
                    btn_main.setBackgroundResource(R.drawable.button_shape_rounded_rectangle);
                    iv_main.setBackgroundResource(R.drawable.button_shape_rounded_rectangle);
                } else if (controlObject.getShape().equalsIgnoreCase("Square")) {
                    btn_main.setBackgroundResource(R.drawable.button_shape_square);
                    iv_main.setBackgroundResource(R.drawable.button_shape_square);
                }


//        btn_main.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(controlObject.getButtonHexColor())));

                if (controlObject.getTypeOfButton().equalsIgnoreCase("Button with Icon")) {
                    String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession()
                            + "/" + appName + "/" + "ButtonIcons/";

                    String[] strsplit = controlObject.getIconPath().split("/");
                    String strFileName = strsplit[strsplit.length - 1];
                    getFromSdcard(btn_main, iv_main, strFileName.replaceAll(" ", "_"), 1);
                    btn_main.setVisibility(View.VISIBLE);
                    iv_main.setVisibility(View.GONE);

                } else if (controlObject.getTypeOfButton().equalsIgnoreCase("Icon")) {
                    String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession()
                            + "/" + appName + "/" + "ButtonIcons/";

                    String[] strsplit = controlObject.getIconPath().split("/");
                    String strFileName = strsplit[strsplit.length - 1];
                    getFromSdcard(btn_main, iv_main, strFileName.replaceAll(" ", "_"), 2);
                    btn_main.setText("");
                    setTextValue("");
                    btn_main.setVisibility(View.GONE);
                    iv_main.setVisibility(View.VISIBLE);
                }

            }
            linearLayout.addView(view);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "addButtonStrip", e);
        }
    }

    private void setControlValues() {
        try {
            if (controlObject.getDisplayName() != null) {
                btn_main.setText(controlObject.getDisplayName());
                setTextValue(controlObject.getDisplayName());

            }
            if (controlObject.isDisableClick()) {
                setViewDisable(view, false);
                Log.d("ButtonIsDisableClick", String.valueOf(controlObject.isDisableClick()));
            }

            setDisplaySettingsButton(context, btn_main, controlObject);
            setButtonColor(controlObject.getButtonHexColor());
            setTextHexColorBG(controlObject.getTextHexColorBG());
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }


    public void getFromSdcard(CustomButton icon, ImageView iv_icon, String filename, int index) {
        try {
            String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "ButtonIcons/";
            File cDir = context.getExternalFilesDir(strSDCardUrl);
            File file = new File(cDir.getAbsolutePath(), filename);
            if (cDir.isDirectory()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//            icon.setImageBitmap(myBitmap);

//            Drawable top = context.getResources().getDrawable(R.drawable.image);
                Drawable top = new BitmapDrawable(context.getResources(), myBitmap);
                if (index == 1) {
                    icon.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                } else {
                    iv_icon.setImageBitmap(myBitmap);
                }

            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getFromSdcard", e);
        }
    }

    public void setParamsMatchParent(View rView) {
        try {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rView.setLayoutParams(layoutParams);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setParamsMatchParent", e);
        }
    }


    /*VerifyCaptcha*/
    public void verifyCaptcha() {

        SafetyNet.getClient((Activity) context).verifyWithRecaptcha(SITE_KEY)
                .addOnSuccessListener((Activity) context, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                        if (!response.getTokenResult().isEmpty()) {
                            handleSiteVerify(response.getTokenResult());
                        }
                    }
                })
                .addOnFailureListener((Activity) context, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            Log.d(TAG, "Error message: " +
                                    CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                        } else {
                            Log.d(TAG, "Unknown type of error: " + e.getMessage());
                        }
                    }
                });
    }



    /*VerifyCaptcha*/

    protected  void handleSiteVerify(final String responseToken){
        RequestQueue queue = Volley.newRequestQueue(context);
        //it is google recaptcha siteverify server
        //you can place your server url
        String url = "https://www.google.com/recaptcha/api/siteverify";
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("success")) {
                        //code logic when captcha returns true Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.getBoolean("success")),Toast.LENGTH_LONG).show();
                        ((MainActivity) context).mCreateControlsFormAPI(view);
                    } else {
                        Toast.makeText(context, String.valueOf(jsonObject.getString("error-codes")), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Log.d(TAG, "JSON exception: " + ex.getMessage());

                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error message: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("secret", SECRET_KEY);
                params.put("response", responseToken);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }


    @Override
    public String getTextValue() {
        return controlObject.getText();
    }

    @Override
    public void setTextValue(String value) {
        btn_main.setText(value);
        controlObject.setText(value);
    }

    @Override
    public boolean getVisibility() {
        return controlObject.isInvisible();
    }

    @Override
    public void setVisibility(boolean visibility) {
        if (visibility) {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        } else {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }

    @Override
    public boolean isEnabled() {

        return !controlObject.isDisable();
    }

    @Override
    public void setEnabled(boolean enabled) {
//        setViewDisable(view, !enabled);
        controlObject.setDisable(!enabled);
        setViewDisableOrEnableDefault(context,view, enabled);
    }

    @Override
    public void clean() {
        setTextValue("");
    }

    @Override
    public void enableHTMLEditor(boolean enabled) {

    }

    @Override
    public void enableHTMLViewer(boolean enabled) {

    }

    @Override
    public boolean isHTMLViewerEnabled() {
        return false;
    }

    @Override
    public boolean isHTMLEditorEnabled() {
        return false;
    }

    @Override
    public String getTextSize() {
        return controlObject.getTextSize();
    }

    @Override
    public void setTextSize(String size) {
        if (size != null) {
            controlObject.setTextSize(size);
            btn_main.setTextSize(Float.parseFloat(size));
        }

    }
    @Override
    public String getTextStyle() {
        return controlObject.getTextStyle();
    }

    @Override
    public void setTextStyle(String style) {
        if (style != null && style.equalsIgnoreCase("Bold")) {
            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
            btn_main.setTypeface(typeface_bold);
            controlObject.setTextStyle(style);
        } else if (style != null && style.equalsIgnoreCase("Italic")) {
            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
            btn_main.setTypeface(typeface_italic);
            controlObject.setTextStyle(style);
        }
    }

    public String getButtonColor() {
        return controlObject.getButtonHexColor();
    }


    public void setButtonColor(String btcolor) {
        if (btcolor != null && !btcolor.equalsIgnoreCase("")) {
            controlObject.setButtonHexColor(btcolor);
            controlObject.setButtonColor(btcolor);
            btn_main.setBackgroundColor(Color.parseColor(btcolor));
        }
    }

    public String getTextHexColorBG() {
        return controlObject.getTextHexColorBG();
    }


    @SuppressLint("NewApi")
    public void setTextHexColorBG(String txtbtcolor) {
        if (controlObject.isEnableChangeButtonColorBG() && txtbtcolor != null && !txtbtcolor.equalsIgnoreCase("")) {
            controlObject.setTextHexColorBG(txtbtcolor);
            btn_main.setBackgroundColor(Color.parseColor(txtbtcolor));
        } else {
//            btn_main.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.success_green)));
            btn_main.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.success_green)));

        }
    }

    @Override
    public String getTextColor() {
        return controlObject.getTextHexColor();
    }

    @Override
    public void setTextColor(String color) {
        if (color != null && !color.equalsIgnoreCase("")) {
            controlObject.setTextHexColor(color);
            controlObject.setTextColor(Color.parseColor(color));
            btn_main.setTextColor(Color.parseColor(color));
        }
    }
    @Override
    public void showMessageBelowControl(String msg) {
        if(msg != null && !msg.isEmpty()) {
            ct_showText.setVisibility(View.VISIBLE);
            ct_showText.setText(msg);
        }else{
            ct_showText.setVisibility(View.GONE);
        }
    }

}

