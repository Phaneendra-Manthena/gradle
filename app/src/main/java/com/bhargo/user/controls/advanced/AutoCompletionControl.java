package com.bhargo.user.controls.advanced;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bhargo.user.BuildConfig;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import nk.mobileapps.spinnerlib.SpinnerData;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

public class AutoCompletionControl implements UIVariables {

    private static final String TAG = "AutoCompletionControl";
    private final Activity context;
    private final ControlObject controlObject;
    private final boolean subFormFlag;
    private final int subFormPos;
    private final String subFormName;
    View rView;
    ImproveHelper improveHelper;
    private LinearLayout linearLayout, ll_label,  ll_user_search,ll_main_inside,ll_tap_text,ll_displayName,ll_control_ui;
    private CustomTextView tv_displayName, tv_hint, tv_tapTextType,ct_showText;
    private ImageView iv_mandatory;
    private AutoCompleteTextView act_user_search;
    private GetServices getServices;
    private List<SpinnerData> list_ControlData;
    private String selectedItemValue;
    private String selectedText;
    private List<String> list_ControlItems=new ArrayList<>();
    private PlacesClient placesClient;
    AutocompleteSupportFragment autocompleteFragment;
    private CustomTextView ct_alertTypeTextInput;

    public AutoCompletionControl(Activity context, ControlObject controlObject, boolean subFormFlag, int subFormPos, String subFormName) {
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
                    rView = lInflater.inflate(R.layout.control_auto_completion_six, null);
                }else {
//                rView = lInflater.inflate(R.layout.control_auto_completion, null);
                    rView = lInflater.inflate(R.layout.layout_auto_completion_default, null);
                }
            } else {
//                rView = lInflater.inflate(R.layout.control_auto_completion, null);
                rView = lInflater.inflate(R.layout.layout_auto_completion_default, null);
            }
            if (rView != null) {
                ViewGroup parent = (ViewGroup) rView.getParent();
                if (parent != null)
                    parent.removeView(rView);
            }
            ll_displayName = rView.findViewById(R.id.ll_displayName);
            ll_label = rView.findViewById(R.id.ll_label);
            ll_user_search = rView.findViewById(R.id.ll_user_search);
            ll_tap_text = rView.findViewById(R.id.ll_tap_text);
            ll_main_inside = rView.findViewById(R.id.ll_main_inside);
            ll_control_ui = rView.findViewById(R.id.ll_control_ui);
            tv_displayName = rView.findViewById(R.id.tv_displayName);
            tv_hint = rView.findViewById(R.id.tv_hint);
            tv_tapTextType = rView.findViewById(R.id.tv_tapTextType);

            iv_mandatory = rView.findViewById(R.id.iv_mandatory);
            ct_alertTypeTextInput = rView.findViewById(R.id.ct_alertTypeText);
            act_user_search = rView.findViewById(R.id.act_user_search);
            ct_showText = rView.findViewById(R.id.ct_showText);
            act_user_search.setTag(controlObject.getControlName());
            act_user_search.setHint("Search here");

            String apiKey =  BuildConfig.API_KEY;
            if (!Places.isInitialized()) {
                Places.initialize(context, apiKey);
            }

            // Create a new Places client instance.
            placesClient = Places.createClient(context);
/*
            AppCompatActivity activity = (AppCompatActivity) rView.getContext();
            autocompleteFragment = (AutocompleteSupportFragment)
                    activity.getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

//            autocompleteFragment.getView().setBackgroundResource(R.drawable.round_rect_shape_ds);





            // Specify the types of place data to return.
//            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
//            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID,
//                    Place.Field.NAME,Place.Field.LAT_LNG));
//            autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
            autocompleteFragment
                    .setPlaceFields(Arrays.asList(Place.Field.ID,
                            Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS));

            // Set up a PlaceSelectionListener to handle the response.
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    // TODO: Get info about the selected place.
                    ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_active_background));
                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getLatLng());
                    //"Place: Visakhapatnam, lat/lng: (17.6868159,83.2184815)"
                    String latLngTmp1 = place.getLatLng().toString().replace("lat/lng: (","");
                    String latLngTmp2 = latLngTmp1.replace(")","");
                    String latLng = latLngTmp2.replace(",","$");
                    setSelectedText(place.getName());
                    setSelectedItemValue(latLng);
                }


                @Override
                public void onError(@NonNull Status status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: " + status);
                }
            });

*/
            setControlValues();
//            tv_tapTextType.setVisibility(View.GONE);
//            setFocus();

//            act_user_search.setOnFocusChangeListener((view, hasFocus) -> {

/*                if (!hasFocus) {
                    if (ll_tap_text.isEnabled()) {
                        improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),hasFocus,ll_tap_text,rView);
                    }
                }else{
                    if (ll_tap_text.isEnabled()) {
                        improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),hasFocus,ll_tap_text,rView);
                    }
                }*/

//            });

            act_user_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("auto complete position", String.valueOf(i));
                    ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_active_background));
                    if (list_ControlData != null) {
                        SpinnerData spinnerData = list_ControlData.get(i);
                        setSelectedText(spinnerData.getName());
                        setSelectedItemValue(spinnerData.getName());
                        if(selectedItemValue != null && !selectedItemValue.isEmpty()) {
                            controlObject.setText(spinnerData.getName());
                            controlObject.setControlValue(spinnerData.getName());
                        }

                    }
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

    public String getSelectedItemValue() {
        return selectedItemValue;
    }

    public void setSelectedItemValue(String selectedItemValue) {
        this.selectedItemValue = selectedItemValue;
        if(selectedItemValue != null && !selectedItemValue.isEmpty()) {
            controlObject.setText(selectedItemValue);
            controlObject.setControlValue(selectedItemValue);
        }
        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
            if (AppConstants.EventCallsFrom == 1) {
                AppConstants.EventFrom_subformOrNot = subFormFlag;
                if (subFormFlag) {
                    AppConstants.SF_Container_position = subFormPos;
                    AppConstants.Current_ClickorChangeTagName = subFormName;
                }
                AppConstants.GlobalObjects.setCurrent_GPS("");
                ((MainActivity) context).ChangeEvent(getAutoCompleteTextView());
            }
        }
    }

    public List<String> getList_ControlItems() {
        return list_ControlItems;
    }

    public void setList_ControlItems(List<String> list_ControlItems) {
        this.list_ControlItems = list_ControlItems;
//        this.list_ControlData = list_ControlItems;
        SearchAdapter adapterDestination = new SearchAdapter(context, android.R.layout.simple_list_item_1);
        act_user_search.setAdapter(adapterDestination);
        act_user_search.setThreshold(controlObject.getMinChartoSeearch());
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list_ControlItems);
//        act_user_search.setAdapter(adapter);
//        act_user_search.setThreshold(1);
//        if(list_ControlItems.size()>0) {
//            act_user_search.setThreshold(1);
//        }else{
//            act_user_search.setThreshold(2);
//        }

//        act_user_search.showDropDown();


    }

    private void setFocus() {
        try {
            tv_tapTextType.setVisibility(View.GONE);
            ll_user_search.setVisibility(View.VISIBLE);

//            setUserSearch();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setFocus", e);
        }
    }

    private void setUserSearch() {
        try {
            SearchAdapter adapterDestination = new SearchAdapter(context, android.R.layout.simple_list_item_1);
            act_user_search.setAdapter(adapterDestination);
            act_user_search.setThreshold(1);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setUserSearch", e);
        }
    }


    private void setControlValues() {
        try {
            ll_tap_text.setTag(controlObject.getControlType());
            tv_displayName.setText(controlObject.getDisplayName());
            tv_tapTextType.setVisibility(View.GONE);
            tv_tapTextType.setText("Tap here to search " + controlObject.getDisplayName());

            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            }
            if (controlObject.getHint() == null || controlObject.getHint().contentEquals("")) {
                tv_hint.setVisibility(View.GONE);
            } else {
                tv_hint.setVisibility(View.VISIBLE);
                tv_hint.setText(controlObject.getHint());
            }

            if(controlObject.isGoogleLocationSearch()){
                act_user_search.setVisibility(View.GONE);
//                autocompleteFragment.getView().setVisibility(View.VISIBLE);

            }else{
//                autocompleteFragment.getView().setVisibility(View.GONE);
                act_user_search.setVisibility(View.VISIBLE);
            }
            if (controlObject.isDisable()) {
//                setViewDisable(rView, false);
//                setViewDisableOrEnableDefault(context,ll_main_inside, false);
//                tv_tapTextType.setClickable(false);
                act_user_search.setEnabled(false);
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

    public LinearLayout getAutoCompletionControlView() {
        return linearLayout;
    }


    public String getAutoCompleteTextValue(){
        return act_user_search.getText().toString().trim();
    }

    public String getAutoCompleteTextValueId(){
        return act_user_search.getText().toString().trim();
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

                    FilterResults results = new FilterResults();

                    try{
                        ArrayList<String> suggestions = new ArrayList<>();
                        list_ControlData = new ArrayList<>();

                        if(list_ControlItems!=null&&list_ControlItems.size()>0){
                            for (int i = 0; i <list_ControlItems.size() ; i++) {

                                if(controlObject.getSearchKeyAt().equalsIgnoreCase("Contains")&&
                                        list_ControlItems.get(i).toLowerCase().contains(constraint.toString().toLowerCase())) {
                                    SpinnerData spinnerData = new SpinnerData();
                                    spinnerData.setName(list_ControlItems.get(i));
                                    spinnerData.setId(list_ControlItems.get(i));
                                    suggestions.add(list_ControlItems.get(i));
                                    list_ControlData.add(spinnerData);
                                }else if(controlObject.getSearchKeyAt().equalsIgnoreCase("Starts with")&&
                                        list_ControlItems.get(i).toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                                    SpinnerData spinnerData = new SpinnerData();
                                    spinnerData.setName(list_ControlItems.get(i));
                                    spinnerData.setId(list_ControlItems.get(i));
                                    suggestions.add(list_ControlItems.get(i));
                                    list_ControlData.add(spinnerData);
                                }else if(controlObject.getSearchKeyAt().equalsIgnoreCase("Ends with")&&
                                        list_ControlItems.get(i).toLowerCase().endsWith(constraint.toString().toLowerCase())) {
                                    SpinnerData spinnerData = new SpinnerData();
                                    spinnerData.setName(list_ControlItems.get(i));
                                    spinnerData.setId(list_ControlItems.get(i));
                                    suggestions.add(list_ControlItems.get(i));
                                    list_ControlData.add(spinnerData);
                                }
                            }
                            results.values = suggestions;
                            results.count = suggestions.size();
                            data = suggestions;
                        }else{
                            if(constraint.length()>=controlObject.getMinChartoSeearch()){
                                if (controlObject.isOnFocusEventExists() && !AppConstants.Initialize_Flag) {
                                    if (AppConstants.EventCallsFrom == 1) {
                                        AppConstants.EventFrom_subformOrNot = subFormFlag;
                                        if (subFormFlag) {
                                            AppConstants.SF_Container_position = subFormPos;
                                            AppConstants.Current_ClickorChangeTagName = subFormName;
                                        }
                                        AppConstants.GlobalObjects.setCurrent_GPS("");
                                        ((MainActivity) context).FocusExist(getAutoCompleteTextView());
                                    }
                                }
                            }
                        }


                    } catch (Exception  e) {
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
                return encodedParams.toString();
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
            }
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

    public String getSelectedText() {
        return selectedText;
    }

    public void setSelectedText(String selectedText) {
        this.selectedText = selectedText;
        controlObject.setText(selectedText);
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
    /*displayName,hint,minChartoSeearch,searchKeyAt*/

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

    public int getMinChartoSeearch() {
        return controlObject.getMinChartoSeearch();
    }

    public void setMinChartoSearch(int minChartoSearch) {
        controlObject.setMinChartoSeearch(minChartoSearch);
    }

    public String getSearchKeyAt() {
        return controlObject.getSearchKeyAt();
    }

    public void setSearchKeyAt(String searchKeyAt) {
        controlObject.setSearchKeyAt(searchKeyAt);;
    }

    /*Validations*/
    /*mandatory,mandatoryErrorMessage,uniqueField,uniqueFieldErrorMessage*/
    public boolean isMandatory() {
        return controlObject.isNullAllowed();
    }

    public void setMandatory(boolean mandatory) {
        controlObject.setNullAllowed(mandatory);
    }

    public String getMandatoryErrorMessage() {
        return controlObject.getMandatoryFieldError();
    }

    public void setMandatoryErrorMessage(String mandatoryErrorMessage) {
        controlObject.setMandatoryFieldError(mandatoryErrorMessage);
    }

    public boolean isUniqueField() {
        return controlObject.isUniqueField();
    }

    public void setUniqueField(boolean uniqueField) {
        controlObject.setUniqueField(uniqueField);
    }

    public String getUniqueFieldErrorMessage() {
        return controlObject.getUniqueFieldError();
    }

    public void setUniqueFieldErrorMessage(String uniqueFieldErrorMessage) {
        controlObject.setUniqueFieldError(uniqueFieldErrorMessage);
    }

    /*Options*/
    /*hideDisplayName,googleLocationSearch
    invisible/visibility,disable/enabled
     */
    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        ll_displayName.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
        controlObject.setHideDisplayName(hideDisplayName);
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
        controlObject.setDisable(!enabled);
        act_user_search.setEnabled(true);

//        setViewDisableOrEnableDefault(context,rView, enabled);
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),!controlObject.isDisable(),ll_tap_text,rView);
    }

    public boolean isGoogleLocationSearch() {
        return controlObject.isGoogleLocationSearch();
    }

    public void setGoogleLocationSearch(boolean googleLocationSearch) {
        controlObject.setGoogleLocationSearch(googleLocationSearch);
    }
    public String getAutoCompleteInputValue() {
        return act_user_search.getText().toString();
    }
    public CustomTextView setAlertAutoCompleteText() { // error alert


        return ct_alertTypeTextInput;
    }
    public boolean isInvisible() {
        return controlObject.isInvisible();
    }

    public void setInvisible(boolean invisible) {
        controlObject.setInvisible(invisible);
        if(invisible){
            linearLayout.setVisibility(View.GONE);
        }else{
            linearLayout.setVisibility(View.VISIBLE);
        }
    }
    public boolean isDisable() {
        return controlObject.isDisable();
    }

    public void setDisable(boolean disable) {
        controlObject.setDisable(disable);
        act_user_search.setEnabled(false);
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),false,ll_tap_text,rView);
    }

    /* Control Ui Settings*/

    public CustomTextView gettap_text() { // taptext
        return tv_tapTextType;
    }
    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
    }
    public LinearLayout getLl_main_inside() {

        return ll_main_inside;
    }
    public LinearLayout getLl_control_ui() {

        return ll_control_ui;
    }
    public CustomTextView getTv_displayName() {

        return tv_displayName;
    }
    /* Control Ui Settings*/
    public ControlObject getControlObject() {
        return controlObject;
    }

    public void clear(){
        controlObject.setText(null);
        controlObject.setControlValue(null);
        tv_tapTextType.setVisibility(View.VISIBLE);
        ll_user_search.setVisibility(View.GONE);
        act_user_search.setText("");
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
