package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettingsButton;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;
import static com.bhargo.user.utils.ImproveHelper.showToastAlert;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.TextVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;

import java.io.File;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Button implements TextVariables, UIVariables {
    private static final String TAG = "Button";
    private final int ButtonTAG = 0;
    Context context;
    //CustomButton customButton;
    ControlObject controlObject;
    ImageView iv_main;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName, appName;
    SessionManager sessionManager;
    ImproveHelper improveHelper;
    private LinearLayout linearLayout,ll_tap_text, ll_button, ll_btnMain,ll_main_inside;
    private CustomTextView tv_displayName, tv_hint,ct_showText;
    private ImageView iv_mandatory;
    private View view;
    private CustomButton btn_main;

    public Button(Context context, ControlObject controlObject, boolean SubformFlag,
                  int SubformPos, String SubformName, String appName) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        this.appName = appName.replaceAll(" ", "_");
        improveHelper = new ImproveHelper(context);
        initView();

    }

    public void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
//        linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            addButtonStrip(context);

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getButton() {

        return linearLayout;
    }

    public View getButtonView() {

        return view;
    }




    public String getButtonText() {

        return btn_main.getText().toString();
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
//            view = lInflater.inflate(R.layout.control_button, null);
            view = lInflater.inflate(R.layout.layout_button_default, null);
            view.setTag(ButtonTAG);
//            setParamsMatchParent(view);
            btn_main = view.findViewById(R.id.btn_main);
            ll_button = view.findViewById(R.id.ll_button);
            iv_main = view.findViewById(R.id.iv_main);
            ll_btnMain = view.findViewById(R.id.ll_btnMain);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
//        setParamsMatchParent(iv_main);
//        tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            ct_showText = view.findViewById(R.id.ct_showText);

            btn_main.setTag(controlObject.getControlName());
            ll_button.setTag(controlObject.getControlName());

            btn_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(controlObject.getControlName().equalsIgnoreCase(AppConstants.shareData)){
                        ((MainActivity) context).addActionEvent(AppConstants.shareData);
                    }else if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = SubformFlag;
                            if (SubformFlag) {
                                AppConstants.SF_Container_position = SubformPos;
                                AppConstants.Current_ClickorChangeTagName = SubformName;
                            }
                            if (AppConstants.GlobalObjects != null) {
                                AppConstants.GlobalObjects.setCurrent_GPS("");
                            }
                            if(controlObject.isValidateFormFields()){
                                view.setTag(R.string.form_validated,true);
                            }else{
                                view.setTag(R.string.form_validated,false);
                            }
                            ((MainActivity) context).ClickEvent(view);
                        }
                    }/*else{
                        byte[] iv = new byte[16];
                        SecureRandom random = new SecureRandom();
                        random.nextBytes(iv);
                        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
                        SecretKey secretKey = ImproveHelper.generateCypherKey();
                        String hash = ImproveHelper.encryptUsingCipher("Hello World ????!!!!AAAA",secretKey,ivParameterSpec);
                        showToastAlert(context,hash.toString());
                        showToastAlert(context,ImproveHelper.decryptUsingCipher(secretKey,hash,ivParameterSpec));
                    }*/
                }
            });

            ll_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = SubformFlag;
                            if (SubformFlag) {
                                AppConstants.SF_Container_position = SubformPos;
                                AppConstants.Current_ClickorChangeTagName = SubformName;

                            }
                            AppConstants.GlobalObjects.setCurrent_GPS("");
                            ((MainActivity) context).ClickEvent(view);
                        }
                    }
                }
            });
            btn_main.setBackgroundResource(R.drawable.button_shape_rectangle);
            setControlValues();

            linearLayout.addView(view);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }

    }

    private void setControlValues() {
        try {
            if (controlObject.getDisplayName() != null) {
//                btn_main.setText(controlObject.getDisplayName());
                setTextValue(controlObject.getDisplayName());
//                controlObject.setText(controlObject.getDisplayName());
            }

            if (controlObject.isDisableClick()) {
                setViewDisable(view, false);
                Log.d("ButtonIsDisableClick", String.valueOf(controlObject.isDisableClick()));
            }


            if(controlObject.getTypeOfButton().equalsIgnoreCase("Button with Icon")){
            }

            setDisplaySettingsButton(context, btn_main, controlObject);
            setButtonColor(controlObject.getButtonHexColor());
            setTextHexColorBG(controlObject.getTextHexColorBG());
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());

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


//                btn_main.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(controlObject.getButtonHexColor())));

                if (controlObject.getTypeOfButton().equalsIgnoreCase("Button with Icon")) {
                    String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/";
//                    String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession()
//                            + "/" + appName + "/" + "ButtonIcons/";

                    String[] strsplit = controlObject.getIconPath().split("/");
                    String strFileName = strsplit[strsplit.length - 1];
                    btn_main.setVisibility(View.VISIBLE);
                    iv_main.setVisibility(View.GONE);
                    getFromSdcard(btn_main, iv_main, strFileName.replaceAll(" ", "_"), 1);

                    ///Drawable drawable = new BitmapDrawable(context.getResources(), ImproveHelper.getBitmapFromURL(controlObject.getIconPath()));
                    //btn_main.setBackground(drawable);

                } else if (controlObject.getTypeOfButton().equalsIgnoreCase("Icon")) {
                    String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession()
                            + "/" + appName + "/";
//                    String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession()
//                            + "/" + appName + "/" + "ButtonIcons/";

                    String[] strsplit = controlObject.getIconPath().split("/");
                    String strFileName = strsplit[strsplit.length - 1];
                    getFromSdcard(btn_main, iv_main, strFileName.replaceAll(" ", "_"), 2);
                    btn_main.setText("");
                    setTextValue("");
                    btn_main.setVisibility(View.GONE);
                    iv_main.setVisibility(View.VISIBLE);
                    Glide.with(context).load(controlObject.getIconPath()).into(iv_main);
                }

            }


        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }

    }


    public void getFromSdcard(CustomButton icon, ImageView iv_icon, String filename, int index) {
        try {
            String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/";
//            String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "ButtonIcons/";
            File cDir = context.getExternalFilesDir(strSDCardUrl);
            File file = new File(cDir.getAbsolutePath(), filename);
            if (cDir.isDirectory()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

//                                    Drawable imageBitmap = new BitmapDrawable(context.getResources(), myBitmap);
//                Drawable imageBitmap = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(myBitmap, myBitmap.getWidth(), myBitmap.getHeight(), true));

                if (myBitmap != null) {
                    Drawable imageBitmap = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(myBitmap, 100, 100, true));
                    if (index == 2) {
                        iv_icon.setImageBitmap(myBitmap);
                    } else if (controlObject.getButtonIconAlignment().equalsIgnoreCase(context.getString(R.string.left))) {
                        icon.setCompoundDrawablesWithIntrinsicBounds(imageBitmap, null, null, null);
                    } else if (controlObject.getButtonIconAlignment().equalsIgnoreCase(context.getString(R.string.right))) {
                        icon.setCompoundDrawablesWithIntrinsicBounds(null, null, imageBitmap, null);
                    } else if (controlObject.getButtonIconAlignment().equalsIgnoreCase(context.getString(R.string.top))) {
                        icon.setCompoundDrawablesWithIntrinsicBounds(null, imageBitmap, null, null);
                    } else {
                        icon.setCompoundDrawablesWithIntrinsicBounds(null, null, null, imageBitmap);
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getFromSdcard", e);
        }

    }


    public void setParamsMatchParent(View rView) {
        try {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rView.setLayoutParams(layoutParams);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setParamsMatchParent", e);
        }

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
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        }
    }

    @Override
    public boolean isEnabled() {

        return controlObject.isDisable();
    }

    @Override
    public void setEnabled(boolean enabled) {
//        setViewDisable(view, !enabled);
        controlObject.setDisable(!enabled);
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),enabled,ll_tap_text,view);
//        setViewDisableOrEnableDefault(context,view, enabled);
//        setViewDisable(view, enabled);

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
            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi));
            btn_main.setTypeface(typeface_bold);
            controlObject.setTextStyle(style);
        } else if (style != null && style.equalsIgnoreCase("Italic")) {
            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi));
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
            btn_main.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(btcolor)));
//            btn_main.setBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(btcolor)));

        }
    }

    public String getTextHexColorBG() {
        return controlObject.getTextHexColorBG();
    }


    @SuppressLint("NewApi")
    public void setTextHexColorBG(String txtbtcolor) {
        if (txtbtcolor != null && !txtbtcolor.equalsIgnoreCase("")) {
            controlObject.setTextHexColorBG(txtbtcolor);
            btn_main.setTextColor(ColorStateList.valueOf(Color.parseColor(txtbtcolor)));
        } /*else {
//            btn_main.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.success_green)));
            btn_main.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.theme_bhargo_color_four)));

        }*/
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

    /*General*/
    /*displayName,hint*/

    public String getDisplayName() {
        return controlObject.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        setTextValue(displayName);
        controlObject.setDisplayName(displayName);
        controlObject.setText(displayName);
    }

    public String getHint() {
        return controlObject.getHint();
    }

    public void setHint(String hint) {
        if (hint != null && !hint.isEmpty()) {
            tv_hint.setVisibility(View.VISIBLE);
            tv_hint.setText(hint);
        } else {
            tv_hint.setVisibility(View.GONE);
        }
        controlObject.setHint(hint);
    }

    public boolean isDisableClick() {
        return controlObject.isDisableClick();
    }

    public void setDisableClick(boolean disableClick) {

        controlObject.setDisableClick(disableClick);
        if (disableClick) {
            setViewDisable(view, false);
        }
    }


    /*Button Display Setting */
    public void setShape(String strShape) {
        try {
            if (strShape != null && !strShape.equalsIgnoreCase("")) {
                if (strShape.equalsIgnoreCase("Rounded Rectangle") || strShape.equalsIgnoreCase("0")) {
                    controlObject.setShape("Rounded Rectangle");
                    btn_main.setBackgroundResource(R.drawable.button_shape_rounded_rectangle);
                    iv_main.setBackgroundResource(R.drawable.button_shape_rounded_rectangle);
                } else if (strShape.equalsIgnoreCase("Round") || strShape.equalsIgnoreCase("1")) {
                    controlObject.setShape("Round");
                    btn_main.setBackgroundResource(R.drawable.button_shape_round);
                    iv_main.setBackgroundResource(R.drawable.button_shape_round);
                } else if (strShape.equalsIgnoreCase("Rectangle") || strShape.equalsIgnoreCase("2")) {
                    controlObject.setShape("Rectangle");
                    btn_main.setBackgroundResource(R.drawable.button_shape_rectangle);
                    iv_main.setBackgroundResource(R.drawable.button_shape_rectangle);
                } else if (strShape.equalsIgnoreCase("Square") || strShape.equalsIgnoreCase("3")) {
                    controlObject.setShape("Square");
                    btn_main.setBackgroundResource(R.drawable.button_shape_square);
                    iv_main.setBackgroundResource(R.drawable.button_shape_square);
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "setIconAlignmentEXp: " + "Shape");
            Log.getStackTraceString(e);
        }

    }

    public void setButtonType(String strButtonType) {
        try {
            if (strButtonType != null && !strButtonType.equalsIgnoreCase("")) {
                if (strButtonType.equalsIgnoreCase("Button with Icon") || strButtonType.equalsIgnoreCase("1")) {
                    controlObject.setTypeOfButton("Button with Icon");
                    btn_main.setVisibility(View.VISIBLE);
                    iv_main.setVisibility(View.GONE);
                } else if (strButtonType.equalsIgnoreCase("Icon") || strButtonType.equalsIgnoreCase("2")) {
                    controlObject.setTypeOfButton("Icon");
                    btn_main.setText("");
                    setTextValue("");
                    btn_main.setVisibility(View.GONE);
                    iv_main.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }

    public void setIconAlignment(String strAlignment) {
        try {
            if (strAlignment != null && !strAlignment.equalsIgnoreCase("")) {
                if (strAlignment.equalsIgnoreCase("Left") || strAlignment.equalsIgnoreCase("0")) {
                    controlObject.setButtonIconAlignment("Left");
                } else if (strAlignment.equalsIgnoreCase("Right") || strAlignment.equalsIgnoreCase("1")) {
                    controlObject.setButtonIconAlignment("Right");
                } else if (strAlignment.equalsIgnoreCase("Top") || strAlignment.equalsIgnoreCase("2")) {
                    controlObject.setButtonIconAlignment("Top");
                } else if (strAlignment.equalsIgnoreCase("Bottom") || strAlignment.equalsIgnoreCase("3")) {
                    controlObject.setButtonIconAlignment("Bottom");
                }

            }
        } catch (Exception e) {
            Log.d(TAG, "setIconAlignmentEXp: " + "ALign" + Log.getStackTraceString(e));

        }
    }

    public void setIconPath(String strIconPath) {
        try {
            controlObject.setIconPath(strIconPath);
            if (strIconPath != null && !strIconPath.isEmpty()) {
                String[] strSplit = strIconPath.split("/");
                String strFileName = strSplit[strSplit.length - 1].replaceAll(" ","_");

                if (controlObject.getTypeOfButton().equalsIgnoreCase("Button with Icon")) {
//                    String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession()
//                            + "/" + appName + "/" + "ButtonIcons/";

                    btn_main.setVisibility(View.VISIBLE);
                    iv_main.setVisibility(View.GONE);
                    getFromSdcard(btn_main, iv_main, strFileName, 1);

                    ///Drawable drawable = new BitmapDrawable(context.getResources(), ImproveHelper.getBitmapFromURL(controlObject.getIconPath()));
                    //btn_main.setBackground(drawable);

                } else if (controlObject.getTypeOfButton().equalsIgnoreCase("Icon")) {
//                    String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession()
//                            + "/" + appName + "/" + "ButtonIcons/";
                    btn_main.setVisibility(View.GONE);
                    iv_main.setVisibility(View.VISIBLE);
                    getFromSdcard(btn_main, iv_main, strFileName, 2);
                    btn_main.setText("");
                    setTextValue("");
                    Glide.with(context).load(controlObject.getIconPath()).into(iv_main);
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "setIconAlignmentEXp: " + "icon");
            Log.getStackTraceString(e);
        }

    }
    /*ControlUi Settings*/
    public LinearLayout getLl_main_inside(){
        return ll_main_inside;
    }
    public CustomButton getbtn_main() {

        return btn_main;
    }
    public LinearLayout getll_button() {

        return ll_button;
    }

    /*ControlUi Settings*/
    public ControlObject getControlObject() {
        return controlObject;
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

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        if(hideDisplayName) {
            setTextValue("");
        }
    }
    public void setValidateFormFields(boolean isValidate) {
        controlObject.setValidateFormFields(isValidate);
        view.setTag(R.string.form_validated,isValidate);
    }
    public void setButtonFileName(String ButtonFileName) {
    }



}

