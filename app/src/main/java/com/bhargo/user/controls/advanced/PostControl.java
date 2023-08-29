package com.bhargo.user.controls.advanced;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.UserGroup;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nk.mobileapps.spinnerlib.SpinnerData;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

public class PostControl implements UIVariables {

    private static final String TAG = "PostControl";
    private final Activity context;
    private final ControlObject controlObject;
    private final boolean subFormFlag;
    private final int subFormPos;
    private final String subFormName;
    View rView;
    ImproveHelper improveHelper;
    private LinearLayout linearLayout,  ll_user_search,ll_tap_text,ll_main_inside,ll_displayName,ll_label,ll_isEnable;
    private CustomTextView tv_displayName, tv_hint, tv_tapTextType,ct_alertTypeText,ct_showText;
    private ImageView iv_mandatory;
    private AutoCompleteTextView act_post_search;
    private GetServices getServices;
    private List<SpinnerData> postControlData;
    private String selectedPostId;
    private String selectedPostName;

    public PostControl(Activity context, ControlObject controlObject, boolean subFormFlag, int subFormPos, String subFormName) {
        this.context = context;
        this.controlObject = controlObject;
        this.subFormFlag = subFormFlag;
        this.subFormPos = subFormPos;
        this.subFormName = subFormName;
        improveHelper = new ImproveHelper(context);
        initViews();
    }

    private void initViews() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        View rView = lInflater.inflate(R.layout.control_user, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                    rView = lInflater.inflate(R.layout.control_user_six, null);
                }
            } else {
//                rView = lInflater.inflate(R.layout.control_user, null);
                rView = lInflater.inflate(R.layout.layout_user_control_default, null);
            }
            ll_main_inside = rView.findViewById(R.id.ll_main_inside);
            ll_label = rView.findViewById(R.id.ll_label);
            ll_isEnable = rView.findViewById(R.id.ll_isEnable);
            ll_user_search = rView.findViewById(R.id.ll_user_search);
            ll_tap_text = rView.findViewById(R.id.ll_tap_text);
            ll_displayName = rView.findViewById(R.id.ll_displayName);
            tv_displayName = rView.findViewById(R.id.tv_displayName);
            tv_hint = rView.findViewById(R.id.tv_hint);
            tv_tapTextType = rView.findViewById(R.id.tv_tapTextType);
            ct_alertTypeText = rView.findViewById(R.id.ct_alertTypeText);
            iv_mandatory = rView.findViewById(R.id.iv_mandatory);

            act_post_search = rView.findViewById(R.id.act_user_search);
            ct_showText = rView.findViewById(R.id.ct_showText);
            act_post_search.setHint(context.getString(R.string.search_posts));
            setControlValues();

            act_post_search.setOnFocusChangeListener((view, hasFocus) -> {

                if (!hasFocus) { if (ll_tap_text.isEnabled()) {
                    improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),hasFocus,ll_user_search,rView);
                }
                    if (act_post_search.getText().toString().isEmpty()) {
                        tv_tapTextType.setVisibility(View.VISIBLE);
                    }
                }else{
                    if (ll_tap_text.isEnabled()) {
                        improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),hasFocus,ll_user_search,rView);
                    }
                    ct_alertTypeText.setVisibility(View.GONE);
                }

            });

            act_post_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("auto complete position", String.valueOf(i));
                    ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_active_background));
                    if (postControlData != null) {
                        SpinnerData spinnerData = postControlData.get(i);
                        setSelectedPostId(spinnerData.getId());
                        setSelectedPostName(spinnerData.getName());
                        if(getSelectedPostId() != null && !getSelectedPostId().isEmpty()) {
                            controlObject.setText(getSelectedPostName());
                            controlObject.setControlValue(getSelectedPostId());
                        }

                    }
                }
            });

            act_post_search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence cs, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence cs, int start, int before, int count) {
                    if (cs.length() > 0) {
                        controlObject.setText("" + cs);
                    } else {
                        controlObject.setText(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            tv_tapTextType.setOnClickListener(view -> {
                setFocus();
            });


            linearLayout.addView(rView);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initViews", e);
        }
    }

    private void setFocus() {
        try {
            act_post_search.requestFocus();
            improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),true,ll_user_search,rView);
            ll_tap_text.setVisibility(View.GONE);
            ll_user_search.setVisibility(View.VISIBLE);

            setUserSearch();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setFocus", e);
        }
    }

    private void setUserSearch() {
        try {
//            SearchAdapter adapterDestination = new SearchAdapter(context, android.R.layout.simple_list_item_1);
            SearchAdapter adapterDestination = new SearchAdapter(context, R.layout.item_textview);
            act_post_search.setAdapter(adapterDestination);
            act_post_search.setThreshold(1);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setUserSearch", e);
        }
    }


    private void setControlValues() {
        try {
            ll_tap_text.setTag(controlObject.getControlType());
            tv_displayName.setText(controlObject.getDisplayName());
            tv_tapTextType.setVisibility(View.VISIBLE);
            tv_tapTextType.setText("Tap here to search " + controlObject.getDisplayName());

            if (controlObject.getHint() == null || controlObject.getHint().contentEquals("")) {
                tv_hint.setVisibility(View.GONE);
            } else {
                tv_hint.setVisibility(View.VISIBLE);
                tv_hint.setText(controlObject.getHint());
            }
            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            }
            if (controlObject.isNullAllowed()) {
                iv_mandatory.setVisibility(View.VISIBLE);
            } else {
                iv_mandatory.setVisibility(View.GONE);
            }
            if (controlObject.isDisable()) {
//                setViewDisable(rView, false);
//                setViewDisableOrEnableDefault(context,ll_main_inside, false);
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),!controlObject.isDisable(),ll_tap_text,rView);
            }
            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    public LinearLayout getPostControlView() {
        return linearLayout;
    }

    public String getSelectedPostId() {
        return selectedPostId;
    }

    public void setSelectedPostId(String selectedPostId) {
        this.selectedPostId = selectedPostId;
        controlObject.setControlValue(selectedPostId);
    }

    public String getSelectedPostName() {
        return selectedPostName;
    }

    public void setSelectedPostName(String selectedPostName) {
        this.selectedPostName = selectedPostName;
        controlObject.setText(selectedPostName);
    }

    public AutoCompleteTextView getAutoCompleteTextView() {
        return linearLayout.getChildAt(0).findViewById(R.id.act_user_search);
    }

    public class SearchAdapter extends ArrayAdapter<String> implements Filterable {
        private final Context context;
        private ArrayList<String> data;

        public SearchAdapter(@NonNull Context context, int resource) {
            super(context, resource);
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Nullable
        @Override
        public String getItem(int position) {
            return data.get(position);
        }

        @NonNull
        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    FilterResults results = null;
                    try {
                        results = new FilterResults();
                        String urlString = RetrofitUtils.BASE_URL+AppConstants.API_NAME_CHANGE+
                                "/V1.0/LoginService/GetSearchPostList_PostLocationControl";
                        InputStream inputStream = null;
                        HttpURLConnection connection = null;

                        String response = "";

                        JSONObject postParams = new JSONObject();
//                    postParams.put("orgId", AppConstants.GlobalObjects.getOrg_Name());
                        postParams.put("orgId", "");
                        if (controlObject.getUserType() != null && controlObject.getUserType().contentEquals("Groups")) {
                            postParams.put("type", "Group");
                            String groupIds = "";
                            if (controlObject.getGroups() != null && controlObject.getGroups().size() > 0) {
                                for (int i = 0; i < controlObject.getGroups().size(); i++) {
                                    if (i == 0) {
                                        groupIds = controlObject.getGroups().get(i).getId();
                                    } else {
                                        groupIds = groupIds + "," + controlObject.getGroups().get(i).getId();
                                    }
                                }
                            }
                            postParams.put("groups", groupIds);
                        } else {
                            postParams.put("type", "All");
                            postParams.put("groups", "");
                        }
                        if(constraint != null) {
                            postParams.put("typed_st     ring", constraint.toString().toLowerCase());
                        }

                        URL url = new URL(urlString);

                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setRequestProperty("Accept", "application/json");
                        connection.setDoInput(true);

                        connection.setDoOutput(true);

                        OutputStream os = connection.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, StandardCharsets.UTF_8));
//                        writer.write(encodeParameters(postParams, "UTF-8"));
                        String jsonInputString = postParams.toString();
                        byte[] input = jsonInputString.getBytes("utf-8");
                        os.write(input, 0, input.length);

                        writer.flush();
                        writer.close();
                        os.close();

                        connection.connect();

                        int responseCode = connection.getResponseCode();

                        if (responseCode != HttpURLConnection.HTTP_OK) {
                            return null;
                        }

                        inputStream = connection.getInputStream();
                        if (inputStream != null) {
                            response = readInputStream(inputStream);
                        }

                        JSONObject responseObj = new JSONObject(response.trim());

                        JSONArray jsonArray_Group = responseObj.getJSONArray("UserList");
                        ArrayList<String> suggestions = new ArrayList<>();
                        postControlData = new ArrayList<>();
                        for (int i = 0; i < jsonArray_Group.length(); i++) {
                            JSONObject jsonObj = jsonArray_Group.getJSONObject(i);
                            if (jsonObj.has("PostName")) {
                                suggestions.add(jsonObj.getString("PostName"));
                            } else {
                                suggestions.add(jsonObj.getString("PostLocationName"));
                            }
                            SpinnerData spinnerData = new SpinnerData();
                            if (jsonObj.has("PostName")) {
                                spinnerData.setName(jsonObj.getString("PostName"));
                            } else {
                                spinnerData.setName(jsonObj.getString("PostLocationName"));
                            }

                            spinnerData.setId(jsonObj.getString("PostID"));
                            spinnerData.setObject(jsonObj);
                            postControlData.add(spinnerData);
                        }

                        results.values = suggestions;
                        results.count = suggestions.size();

                        data = suggestions;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    if (results != null && results.count > 0) {

                        notifyDataSetChanged();

                        //String result_latcoder = coder.getFromLocation(results);
                    } else notifyDataSetInvalidated();
                }
            };
        }

        private String encodeParameters(Map<String, String> params, String paramsEncoding) {
            StringBuilder encodedParams = new StringBuilder();
            try {
                if (params != null) {
                    boolean first = true;
                    for (Map.Entry<String, String> entry : params.entrySet()) {

                        if (first)
                            first = false;
                        else
                            encodedParams.append("&");

                        encodedParams.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                        encodedParams.append("=");
                        encodedParams.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                    }
                }
            } catch (Exception e) {
                improveHelper.improveException(context, TAG, "encodeParameters", e);
            }
            return encodedParams.toString();
            /*catch (UnsupportedEncodingException uee) {
                throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
            }*/
        }

        private String readInputStream(InputStream in) throws IOException {

            StringBuilder total = new StringBuilder();
            try {
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }
            } catch (Exception e) {
                improveHelper.improveException(context, TAG, "readInputStream", e);
            }
            return total.toString();
        }


    }

    public boolean getVisibility() {
        controlObject.setInvisible(linearLayout.getVisibility() != View.VISIBLE);
        return controlObject.isInvisible();
    }


    public void setVisibility(boolean visibility) {

        if (visibility) {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        } else {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }


    public boolean isEnabled() {
        return !controlObject.isDisable();
    }


    public void setEnabled(boolean enabled) {
//        setViewDisable(rView, !enabled);
        controlObject.setDisable(enabled);
//        setViewDisableOrEnableDefault(context,rView, enabled);
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),enabled,ll_tap_text,rView);
    }

    /*DisplaySettings*/
    /*size,style,color*/

    @Override
    public String getTextSize() {
        return controlObject.getTextSize();
    }

    @Override
    public void setTextSize(String size) {
        if (size != null) {
            controlObject.setTextSize(size);
            tv_displayName.setTextSize(Float.parseFloat(size));
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
            tv_displayName.setTypeface(typeface_bold);
            controlObject.setTextStyle(style);
        } else if (style != null && style.equalsIgnoreCase("Italic")) {
            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
            tv_displayName.setTypeface(typeface_italic);
            controlObject.setTextStyle(style);
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
            tv_displayName.setTextColor(Color.parseColor(color));
        }
    }

    /*General*/
    /*displayName,hint,*/
    //pending:

    public String getDisplayName() {
        return controlObject.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        tv_displayName.setText(displayName);
        controlObject.setDisplayName(displayName);
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
    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        if (hideDisplayName) {
            ll_displayName.setVisibility(View.GONE);
        }
    }

    public boolean isInvisible() {
        return controlObject.isInvisible();
    }

    public void setInvisible(boolean invisible) {
        controlObject.setInvisible(invisible);
        if(invisible){
            linearLayout.setVisibility(View.GONE);
        }
    }
    public boolean isDisable() {
        return controlObject.isDisable();
    }

    public void setDisable(boolean disable) {
        controlObject.setDisable(disable);
        if(disable){
            improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),false,ll_tap_text,rView);
        }
    }

    public String getUserType() {
        return controlObject.getUserType();
    }

    public void setUserType(String userType) {
        controlObject.setUserType(userType);
    }

    public List<UserGroup> getGroups() {
        return controlObject.getGroups();
    }

    public void setGroups(List<UserGroup> groups) {
        controlObject.setGroups(groups);
    }


    public CustomTextView setAlertText() { // error alert

        return ct_alertTypeText;
    }

    public void setEditValues(String Value,String ValueId){
        setFocus();
        tv_tapTextType.setVisibility(View.GONE);
        ll_user_search.setVisibility(View.VISIBLE);
        act_post_search.setText(Value);
        setSelectedPostId(ValueId);
        setSelectedPostName(Value);

    }
    /*ControlUi Settings Start*/

    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
    }
    public LinearLayout getLl_main_inside() { // linear for taptext

        return ll_main_inside;
    }
    public LinearLayout getLl_label() { // linear for taptext

        return ll_label;
    }
    public LinearLayout getLl_isEnable() { // linear for taptext

        return ll_isEnable;
    }
    public LinearLayout getLl_user_search() { // linear for taptext

        return ll_user_search;
    }
    public CustomTextView getTv_displayName() { // linear for taptext

        return tv_displayName;
    }

    /*ControlUi Settings End*/
    public ControlObject getControlObject() {
        return controlObject;
    }

    public void clear(){
        improveHelper.controlFocusBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), false, ll_user_search, rView);
        ll_tap_text.setVisibility(View.VISIBLE);
        act_post_search.setText("");
        ll_user_search.setVisibility(View.GONE);
        controlObject.setText(null);
        controlObject.setControlValue(null);
    }

    public void showMessageBelowControl(String msg) {
        if(msg != null && !msg.isEmpty()) {
            ct_showText.setVisibility(View.VISIBLE);
            ct_showText.setText(msg);
        }else{
            ct_showText.setVisibility(View.GONE);
        }
    }

}
