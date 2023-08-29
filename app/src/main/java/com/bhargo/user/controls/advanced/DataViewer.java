package com.bhargo.user.controls.advanced;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.ActionWithoutCondition_Bean;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.LanguageMapping;
import com.bhargo.user.Java_Beans.Param;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.adapters.DataViewerAdapter;
import com.bhargo.user.adapters.WidgetSliderAdapter;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.interfaces.ItemClickListener;
import com.bhargo.user.interfaces.SubFormDeleteInterface;
import com.bhargo.user.pojos.DataViewerModelClass;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.EventsUtil;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PropertiesNames;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.mongodb.App;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataViewer implements ItemClickListener, UIVariables {

    private static final String TAG = "DataViewer";
    private final Context context;
    public ControlObject controlObject;
    public DataViewerAdapter customAdapter;
    CustomTextView tv_displayName, tv_hint, tv_NoData;
    ImproveHelper improveHelper;
    LinearLayout ll_preViewContainer;
    DataCollectionObject dataCollectionObject;
    SubFormDeleteInterface subFormDeleteInterface;
    ScrollView scrollView;
    CustomEditText et_search;
    SessionManager sessionManager;
    View rView;
    int adapterPosition = -1;
    private List<DataViewerModelClass> dataViewerModelClassList = new ArrayList<>();
    private LinearLayout linearLayout, ll_label;
    private RecyclerView rvList;
    private JSONObject lazyLoadingObject;
    private ActionWithoutCondition_Bean ActionBean;
    private LinkedHashMap<String, List<String>> outputData = new LinkedHashMap<>();
    private boolean isLoading;
    private LinearLayout ll_view_pager;
    private ViewPager widget5_viewPager;
    private LinearLayout sliderDots;
    public DataViewer(Context context, ControlObject controlObject, LinearLayout ll_preViewContainer, DataCollectionObject dataCollectionObject, ScrollView scrollView) {
        this.context = context;
        this.controlObject = controlObject;
        this.ll_preViewContainer = ll_preViewContainer;
        this.dataCollectionObject = dataCollectionObject;
        this.scrollView = scrollView;
        improveHelper = new ImproveHelper(context);
        sessionManager = new SessionManager(context);
        initView();
    }

    public void SetDataViewerData(List<DataViewerModelClass> dataViewerModelClassList) {
        try {
            if (dataViewerModelClassList.size() > 0) {
                tv_NoData.setVisibility(View.GONE);
            } else {
                tv_NoData.setVisibility(View.VISIBLE);
            }

            this.dataViewerModelClassList = dataViewerModelClassList;
            /*if(controlObject.getDataViewer_UI_Pattern().contentEquals(AppConstants.Button_Group)&&dataViewerModelClassList.size()>0) {
                dataViewerModelClassList.get(0).setSelected(true);
            }*/
            if(controlObject.getDataViewer_UI_Pattern().contentEquals(AppConstants.WIDGET_4)){
                sliderDots.removeAllViews();
                initSlider(dataViewerModelClassList);
            }else {
                customAdapter = new DataViewerAdapter(context, dataViewerModelClassList, controlObject, tv_NoData);
                rvList.setAdapter(customAdapter);
                customAdapter.setCustomClickListener(this);
            }
//            rvList.setNestedScrollingEnabled(false);


        /*if(ll_preViewContainer != null) {
//            improveHelper.bothWrapContentAndDp(controlObject,ll_preViewContainer, AppConstants.CONTROL_TYPE_DATA_VIEWER, controlObject.getControlName());
            improveHelper.bothWrapContentAndDp(dataCollectionObject,controlObject,linearLayout, AppConstants.CONTROL_TYPE_DATA_VIEWER, controlObject.getControlName());
        }*/
            if (subFormDeleteInterface != null) {
                subFormDeleteInterface.bothWrapContentAndDp(controlObject.getControlType(), controlObject.getControlName(), scrollView);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "SetDataViewerData", e);
        }

    }

    private void initView() {
        try {
            linearLayout = new LinearLayout(context);
            //ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
//            ImproveHelper.layout_params_match_parent.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rView = layoutInflater.inflate(R.layout.activity_data_viewer, null);


            tv_displayName = rView.findViewById(R.id.tv_displayName);
            tv_hint = rView.findViewById(R.id.tv_hint);
            tv_displayName.setText(controlObject.getDisplayName());
            tv_displayName.setTag(controlObject.getControlName());
            tv_hint.setTag(controlObject.getControlName());
            tv_NoData = rView.findViewById(R.id.tv_NoData);
            et_search = rView.findViewById(R.id.et_search);
            rvList = rView.findViewById(R.id.rv_dvGrid);
            ll_label = rView.findViewById(R.id.ll_label);
            ll_view_pager = rView.findViewById(R.id.ll_view_pager);
            widget5_viewPager = rView.findViewById(R.id.viewPager);
            sliderDots = rView.findViewById(R.id.sliderDots);
            ll_view_pager.setVisibility(View.GONE);

            et_search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence != null)
                        if (customAdapter != null)
                            customAdapter.getFilter().filter(charSequence.toString().trim());

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            if (controlObject.isHideDisplayName()) {
                tv_displayName.setVisibility(View.GONE);
                tv_hint.setVisibility(View.GONE);
            } else {
                tv_displayName.setVisibility(View.VISIBLE);
                if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                    tv_hint.setVisibility(View.VISIBLE);
                    tv_hint.setText(controlObject.getHint());
                } else {
                    tv_hint.setVisibility(View.GONE);
                }
            }
            if(controlObject.isInvisible()){
                linearLayout.setVisibility(View.GONE);
            }
            if (controlObject.isDataViewer_searchEnabled()) {
                et_search.setVisibility(View.VISIBLE);
            } else {
                et_search.setVisibility(View.GONE);
            }

            if(controlObject.getDataViewer_UI_Pattern().contentEquals(AppConstants.WIDGET_4)){
                ll_view_pager.setVisibility(View.VISIBLE);
                rvList.setVisibility(View.GONE);
            }else {
                ll_view_pager.setVisibility(View.GONE);
                rvList.setVisibility(View.VISIBLE);
                dataViewerInItViews();
            }
//        customAdapter.setCustomClickListener(this);

//        if(controlObject.getDataViewer_FrameBG_HexColor()!=null && controlObject.getDataViewer_FrameBG_HexColor().contains("#")) {
//            rvList.setBackgroundColor(Color.parseColor(controlObject.getDataViewer_FrameBG_HexColor()));
//        }


            linearLayout.addView(rView);

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }

    }

    public void setResultFromGetDataManageDataAPIData(){
       ActionWithoutCondition_Bean ActionObj= getActionBean();
        LinkedHashMap<String, List<String>> OutputData =getOutputData();
        List<DataViewerModelClass> dataViewerModelClassList = new ArrayList<>();
        if (ActionObj.getMulti_DataType().equalsIgnoreCase("append")) {
            dataViewerModelClassList = getDataViewerModelClassList();
        }
        List<String> Header_list = new ArrayList<>();
        if (ActionObj.getHeader_Mapped_item() != null) {
            Header_list = OutputData.get(ActionObj.getHeader_Mapped_item().toLowerCase());
        }
        List<String> CornerText_list = new ArrayList<>();
        List<String> SubHeader_list = new ArrayList<>();
        //--EV--//
        List<String> Distance_list = new ArrayList<>();
        List<String> WorkingHours_list = new ArrayList<>();
        List<String> ItemOne_list = new ArrayList<>();
        List<String> ItemTwo_list = new ArrayList<>();
        List<String> Rating_list = new ArrayList<>();
        List<String> Source_Icons_list = new ArrayList<>();
        List<String> Source_Name_list = new ArrayList<>();
        List<String> Source_Time_list = new ArrayList<>();
        List<String> News_Type_list = new ArrayList<>();
        List<String> itemName_list = new ArrayList<>();
        List<String> watsApp_no_list = new ArrayList<>();
        List<String> dail_no_list = new ArrayList<>();
        //--EV--//
        List<String> DateandTime_list = new ArrayList<>();
        List<String> ImagePath_list = new ArrayList<>();
        List<String> ProfileImagePath_list = new ArrayList<>();
        LinkedHashMap<String, List<String>> hash_Description = new LinkedHashMap<String, List<String>>();
        List<String> Advance_ImageSource = new ArrayList<>();
        List<String> Advance_ConditionColumn = new ArrayList<>();
        List<String> DV_TransID_List = new ArrayList<>();
        List<String> itemValuesList = new ArrayList<>();
        if (ActionObj.getCorner_Mapped_item() != null && ActionObj.getCorner_Mapped_item().length() > 0) {
            CornerText_list = OutputData.get(ActionObj.getCorner_Mapped_item().toLowerCase());
        }

        if (ActionObj.getSubHeader_Mapped_item() != null && ActionObj.getSubHeader_Mapped_item().length() > 0) {
            SubHeader_list = OutputData.get(ActionObj.getSubHeader_Mapped_item().toLowerCase());
        }
        if (ActionObj.getDateandTime_Mapped_item() != null && ActionObj.getDateandTime_Mapped_item().length() > 0) {
            DateandTime_list = OutputData.get(ActionObj.getDateandTime_Mapped_item().toLowerCase());
        }
        if (ActionObj.getProfileImage_Mapped_item() != null && ActionObj.getProfileImage_Mapped_item().length() > 0) {
            ProfileImagePath_list = OutputData.get(ActionObj.getProfileImage_Mapped_item().toLowerCase());
            ImagePath_list = OutputData.get(ActionObj.getProfileImage_Mapped_item().toLowerCase());
        }
        if (ActionObj.getImage_Mapped_item() != null && ActionObj.getImage_Mapped_item().length() > 0) {
            ImagePath_list = OutputData.get(ActionObj.getImage_Mapped_item().toLowerCase());
        }
        if (ActionObj.getList_ImageAdvanced_Items() != null && ActionObj.getList_ImageAdvanced_Items().size() > 0) {
            if (ActionObj.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                Advance_ImageSource = OutputData.get(ActionObj.getImageAdvanced_ImageSource().toLowerCase());
            }
            Advance_ConditionColumn = OutputData.get(ActionObj.getImageAdvanced_ConditionColumn().toLowerCase());
        }


        if (ActionObj.getDescription_Mapped_item() != null && ActionObj.getDescription_Mapped_item().size() > 0) {
            for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                List<String> X_ItemList = OutputData.get(ActionObj.getDescription_Mapped_item().get(x).toLowerCase());
                hash_Description.put(ActionObj.getDescription_Mapped_item().get(x), X_ItemList);
            }
        }
        //--EV--//
        if (ActionObj.getDistance() != null && ActionObj.getDistance().length() > 0) {
            Distance_list = OutputData.get(ActionObj.getDistance().toLowerCase());
        }
        if (ActionObj.getWorking_hours() != null && ActionObj.getWorking_hours().length() > 0) {
            WorkingHours_list = OutputData.get(ActionObj.getWorking_hours().toLowerCase());
        }
        if (ActionObj.getItem_one_count() != null && ActionObj.getItem_one_count().length() > 0) {
            ItemOne_list = OutputData.get(ActionObj.getItem_one_count().toLowerCase());
        }
        if (ActionObj.getItem_two_count() != null && ActionObj.getItem_two_count().length() > 0) {
            ItemTwo_list = OutputData.get(ActionObj.getItem_two_count().toLowerCase());
        }
        if (ActionObj.getRating() != null && ActionObj.getRating().length() > 0) {
            Rating_list = OutputData.get(ActionObj.getRating().toLowerCase());
        }
        if (ActionObj.getSource_icon() != null && ActionObj.getSource_icon().length() > 0) {
            Source_Icons_list = OutputData.get(ActionObj.getSource_icon().toLowerCase());
        }
        if (ActionObj.getSource_name() != null && ActionObj.getSource_name().length() > 0) {
            Source_Name_list = OutputData.get(ActionObj.getSource_name().toLowerCase());
        }
        if (ActionObj.getSource_time() != null && ActionObj.getSource_time().length() > 0) {
            Source_Time_list = OutputData.get(ActionObj.getSource_time().toLowerCase());
        }
        if (ActionObj.getNews_type() != null && ActionObj.getNews_type().length() > 0) {
            News_Type_list = OutputData.get(ActionObj.getNews_type().toLowerCase());
        }
        if (ActionObj.getItemName() != null && ActionObj.getItemName().length() > 0) {
            itemName_list = OutputData.get(ActionObj.getItemName().toLowerCase());
        }
        if (ActionObj.getWatsAppNo() != null && ActionObj.getWatsAppNo().length() > 0) {
            watsApp_no_list = OutputData.get(ActionObj.getWatsAppNo().toLowerCase());
        }
        if (ActionObj.getDailNo() != null && ActionObj.getDailNo().length() > 0) {
            dail_no_list = OutputData.get(ActionObj.getDailNo().toLowerCase());
        }
        //--EV--//
        if (ActionObj.getDv_trans_id() != null && ActionObj.getDv_trans_id().length() > 0) {
            DV_TransID_List = OutputData.get(ActionObj.getDv_trans_id().toLowerCase());
        }
        if (ActionObj.getItemValue() != null && ActionObj.getItemValue().length() > 0) {
            itemValuesList = OutputData.get(ActionObj.getItemValue().toLowerCase());
        }

        if (!controlObject.getDataViewer_UI_Pattern().contentEquals(AppConstants.WIDGET_4)) {
            for (int i = 0; i < Header_list.size(); i++) {
                DataViewerModelClass dmv = new DataViewerModelClass();
                dmv.setHeading(Header_list.get(i));
                List<String> Description = new ArrayList<String>();
                if (DV_TransID_List != null && DV_TransID_List.size() != 0) {
                    dmv.setDv_trans_id(DV_TransID_List.get(i));
                }
                if (itemValuesList != null && itemValuesList.size() != 0) {
                    dmv.setItemValue(itemValuesList.get(i));
                }
                switch (controlObject.getDataViewer_UI_Pattern()) {
                    case AppConstants.GridView_With_Image_2_Columns:
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setImage_Path(ImagePath_list.get(i));
                        }
                        if (CornerText_list != null && CornerText_list.size() != 0) {
                            dmv.setCornerText(CornerText_list.get(i));
                        }

                        if (ActionObj.getList_ImageAdvanced_Items() != null && ActionObj.getList_ImageAdvanced_Items().size() > 0) {
                            if (ActionObj.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                dmv.setImage_Path(null);
                                dmv.setAdvanceImage_Source(Advance_ImageSource.get(i));
                            }
                            dmv.setList_Image_Path(ActionObj.getList_ImageAdvanced_Items());
                            dmv.setAdvanceImage_ConditionColumn(Advance_ConditionColumn.get(i));
                        }
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.GridView_With_Image_3_Columns:
                        dmv.setSubHeading(SubHeader_list.get(i));
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setImage_Path(ImagePath_list.get(i));
                        }
                        if (CornerText_list != null && CornerText_list.size() != 0) {
                            dmv.setCornerText(CornerText_list.get(i));
                        }
                        if (ActionObj.getList_ImageAdvanced_Items() != null && ActionObj.getList_ImageAdvanced_Items().size() > 0) {
                            if (ActionObj.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                dmv.setImage_Path(null);
                                dmv.setAdvanceImage_Source(Advance_ImageSource.get(i));
                            }
                            dmv.setList_Image_Path(ActionObj.getList_ImageAdvanced_Items());
                            dmv.setAdvanceImage_ConditionColumn(Advance_ConditionColumn.get(i));
                        }
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.GridView_With_Image_2_Columns_call:
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setImage_Path(ImagePath_list.get(i));
                        }
                        if (ActionObj.getList_ImageAdvanced_Items() != null && ActionObj.getList_ImageAdvanced_Items().size() > 0) {
                            if (ActionObj.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                dmv.setImage_Path(null);
                                dmv.setAdvanceImage_Source(Advance_ImageSource.get(i));
                            }
                            dmv.setList_Image_Path(ActionObj.getList_ImageAdvanced_Items());
                            dmv.setAdvanceImage_ConditionColumn(Advance_ConditionColumn.get(i));
                        }
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.GridView_With_Image_3_Columns_call:
                        dmv.setSubHeading(SubHeader_list.get(i));
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setImage_Path(ImagePath_list.get(i));
                        }
                        if (ActionObj.getList_ImageAdvanced_Items() != null && ActionObj.getList_ImageAdvanced_Items().size() > 0) {
                            if (ActionObj.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                dmv.setImage_Path(null);
                                dmv.setAdvanceImage_Source(Advance_ImageSource.get(i));
                            }
                            dmv.setList_Image_Path(ActionObj.getList_ImageAdvanced_Items());
                            dmv.setAdvanceImage_ConditionColumn(Advance_ConditionColumn.get(i));
                        }
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.GridView_With_Video_2_Columns:
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setVideo_Path(ImagePath_list.get(i));
                        }
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.GridView_With_Video_3_Columns:
                        dmv.setSubHeading(SubHeader_list.get(i));
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setVideo_Path(ImagePath_list.get(i));
                        }
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.GridView_With_Video_2_Columns_call:
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setVideo_Path(ImagePath_list.get(i));
                        }
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.GridView_With_Video_3_Columns_call:
                        dmv.setSubHeading(SubHeader_list.get(i));
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setVideo_Path(ImagePath_list.get(i));
                        }
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.ListView_2_Columns:
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        if (CornerText_list != null && CornerText_list.size() != 0) {
                            dmv.setCornerText(CornerText_list.get(i));
                        }

                        dmv.setDescription(Description);
                        break;
                    case AppConstants.ListView_With_Image_2_Columns:
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setProfileImage_Path(ImagePath_list.get(i));
                        }
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        if (CornerText_list != null && CornerText_list.size() != 0) {
                            dmv.setCornerText(CornerText_list.get(i));
                        }

                        dmv.setDescription(Description);
                        break;
                    case AppConstants.ListView_With_Image_3_Columns_call:
                        dmv.setSubHeading(SubHeader_list.get(i));
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setImage_Path(ImagePath_list.get(i));
                        }
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        if (CornerText_list != null && CornerText_list.size() != 0) {
                            dmv.setCornerText(CornerText_list.get(i));
                        }

                        dmv.setDescription(Description);
                    case AppConstants.ListView_With_Image_3_Columns:
                        dmv.setSubHeading(SubHeader_list.get(i));
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setImage_Path(ImagePath_list.get(i));
                        }
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        if (CornerText_list != null && CornerText_list.size() != 0) {
                            dmv.setCornerText(CornerText_list.get(i));
                        }

                        dmv.setDescription(Description);
                        break;
                    case AppConstants.Geo_Spatial_View:
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setGpsValue(ImagePath_list.get(i));
                        }
                        if (SubHeader_list.size() > 0) {
                            dmv.setSubHeading(SubHeader_list.get(i));
                        }
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            if (xItem != null) {
                                Description.add(xItem.get(i));
                            }
                        }
                        if (Description.size() > 0) {
                            dmv.setDescription(Description);
                        } else {
                            dmv.setDescription(new ArrayList<>());
                        }
                        break;
                    case AppConstants.TimeLine_View:
                        dmv.setDateandTime(DateandTime_list.get(i));
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.TimeLine_With_Photo_View:
                        if (ProfileImagePath_list != null && ProfileImagePath_list.size() != 0) {
                            dmv.setProfileImage_Path(ProfileImagePath_list.get(i));
                        }

                        dmv.setDateandTime(DateandTime_list.get(i));
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.LinearView_With_Video:
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.BlogSpot_View:
                        if (ProfileImagePath_list != null && ProfileImagePath_list.size() != 0) {
                            dmv.setProfileImage_Path(ProfileImagePath_list.get(i));
                        }
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setImage_Path(ImagePath_list.get(i));
                        }
                        dmv.setSubHeading(SubHeader_list.get(i));
                        dmv.setImage_Path(ImagePath_list.get(i));
//                                                for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
//                                                    List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
//                                                    Description.add(xItem.get(i));
//                                                }
//                                                dmv.setDescription(Description);
                        break;
                    case AppConstants.MapView_Item_Info:
                        dmv.setSubHeading(SubHeader_list.get(i));
                        dmv.setDistance(Distance_list.get(i));
                        dmv.setWorkingHours(WorkingHours_list.get(i));
                        dmv.setItemOneCount(ItemOne_list.get(i));
                        dmv.setItemTwoCount(ItemTwo_list.get(i));
                        break;
                    case AppConstants.EV_Dashboard_Design_One:
                    case AppConstants.EV_Dashboard_Design_Three:
                        dmv.setSubHeading(SubHeader_list.get(i));
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setImage_Path(ImagePath_list.get(i));
                        }
                        if (ActionObj.getList_ImageAdvanced_Items() != null && ActionObj.getList_ImageAdvanced_Items().size() > 0) {
                            if (ActionObj.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                dmv.setImage_Path(null);
                                dmv.setAdvanceImage_Source(Advance_ImageSource.get(i));
                            }
                            dmv.setList_Image_Path(ActionObj.getList_ImageAdvanced_Items());
                            dmv.setAdvanceImage_ConditionColumn(Advance_ConditionColumn.get(i));
                        }
                        dmv.setRating(Rating_list.get(i));
                        break;
                    case AppConstants.EV_Dashboard_Design_Two:
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setImage_Path(ImagePath_list.get(i));
                        }
                        if (ActionObj.getList_ImageAdvanced_Items() != null && ActionObj.getList_ImageAdvanced_Items().size() > 0) {
                            if (ActionObj.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                dmv.setImage_Path(null);
                                dmv.setAdvanceImage_Source(Advance_ImageSource.get(i));
                            }
                            dmv.setList_Image_Path(ActionObj.getList_ImageAdvanced_Items());
                            dmv.setAdvanceImage_ConditionColumn(Advance_ConditionColumn.get(i));
                        }

                        break;
                    case AppConstants.EV_News_Design:
                        if (Source_Icons_list.size() > 0) {
                            dmv.setSource_icon(Source_Icons_list.get(i));
                            dmv.setSource_name(Source_Name_list.get(i));
                            dmv.setSource_time(Source_Time_list.get(i));
                        }
                        dmv.setNews_type(News_Type_list.get(i));
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setImage_Path(ImagePath_list.get(i));
                        }
                        if (CornerText_list != null && CornerText_list.size() != 0) {
                            dmv.setCornerText(CornerText_list.get(i));
                        }
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.EV_Notifications_Design:
                        dmv.setSubHeading(SubHeader_list.get(i));
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.EV_Dealers_Design:
                        dmv.setSubHeading(SubHeader_list.get(i));
                        dmv.setDistance(Distance_list.get(i));
                        dmv.setWorkingHours(WorkingHours_list.get(i));
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setImage_Path(ImagePath_list.get(i));
                        }
                        break;
                    case AppConstants.EV_Jobs_Design:
                        dmv.setSubHeading(SubHeader_list.get(i));
                        dmv.setSource_name(Source_Name_list.get(i));
                        dmv.setSource_time(Source_Time_list.get(i));
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.Button_Group:
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setImage_Path(ImagePath_list.get(i));
                        }
                        break;
                    case AppConstants.GRID_WITH_TWO_COLUMNS:
                        if (ProfileImagePath_list != null && ProfileImagePath_list.size() != 0) {
                            dmv.setProfileImage_Path(ProfileImagePath_list.get(i));
                        }
                        if (ImagePath_list != null && ImagePath_list.size() != 0) {
                            dmv.setImage_Path(ImagePath_list.get(i));
                        }
                        dmv.setSubHeading(SubHeader_list.get(i));
                        dmv.setItemName(itemName_list.get(i));
                        dmv.setWatsAppNo(watsApp_no_list.get(i));
                        dmv.setDailNo(dail_no_list.get(i));
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.WIDGET_1:
                        dmv.setImage_Path(ImagePath_list.get(i));
                        dmv.setHeading(Header_list.get(i));
                        dmv.setSubHeading(SubHeader_list.get(i));
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.WIDGET_2:
                        dmv.setImage_Path(ImagePath_list.get(i));
                        dmv.setHeading(Header_list.get(i));
                        dmv.setSubHeading(SubHeader_list.get(i));
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.WIDGET_3:
                        dmv.setImage_Path(ImagePath_list.get(i));
                        dmv.setHeading(Header_list.get(i));
                        dmv.setSubHeading(SubHeader_list.get(i));
                        break;
                    case AppConstants.WIDGET_4:
                        dmv.setImage_Path(ImagePath_list.get(i));
                        break;
                    case AppConstants.WIDGET_5:
                        dmv.setHeading(Header_list.get(i));
                        dmv.setSubHeading(SubHeader_list.get(i));
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.WIDGET_6:
                        dmv.setImage_Path(ImagePath_list.get(i));
                        dmv.setProfileImage_Path(ProfileImagePath_list.get(i));
                        dmv.setHeading(Header_list.get(i));
                        dmv.setSubHeading(SubHeader_list.get(i));
                        break;
                    case AppConstants.WIDGET_7:
                        dmv.setHeading(Header_list.get(i));
                        dmv.setSubHeading(SubHeader_list.get(i));
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.WIDGET_8:
                        dmv.setHeading(Header_list.get(i));
                        dmv.setSubHeading(SubHeader_list.get(i));
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                    case AppConstants.WIDGET_9:
                        dmv.setImage_Path(ImagePath_list.get(i));
                        dmv.setHeading(Header_list.get(i));
                        dmv.setSubHeading(SubHeader_list.get(i));
                        break;
                    case AppConstants.WIDGET_10:
                        dmv.setImage_Path(ImagePath_list.get(i));
                        dmv.setHeading(Header_list.get(i));
                        dmv.setSubHeading(SubHeader_list.get(i));
                        for (int x = 0; x < ActionObj.getDescription_Mapped_item().size(); x++) {
                            List<String> xItem = hash_Description.get(ActionObj.getDescription_Mapped_item().get(x));
                            Description.add(xItem.get(i));
                        }
                        dmv.setDescription(Description);
                        break;
                }
                dataViewerModelClassList.add(dmv);
            }
        } else {

            if (ImagePath_list != null && ImagePath_list.size() > 0) {
                for (int i = 0; i < ImagePath_list.size(); i++) {
                    DataViewerModelClass dmv = new DataViewerModelClass();
                    dmv.setImage_Path(ImagePath_list.get(i));
                    dataViewerModelClassList.add(dmv);
                }

            }
        }
        SetDataViewerData(dataViewerModelClassList);
//                                    bothWrapContentAndDp(ActionObj.getResult_DisplayType(), SelectedSubForm);
    }

    private void dataViewerInItViews() {
        try {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            switch (controlObject.getDataViewer_UI_Pattern()) {
                case AppConstants.GridView_With_Image_2_Columns:
                case AppConstants.GridView_With_Image_3_Columns:
                case AppConstants.GridView_With_Image_2_Columns_call:
                case AppConstants.GridView_With_Image_3_Columns_call:
                case AppConstants.GridView_With_Video_2_Columns:
                case AppConstants.GridView_With_Video_3_Columns:
                case AppConstants.GridView_With_Video_2_Columns_call:
                case AppConstants.GridView_With_Video_3_Columns_call:
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rvList.setLayoutManager(gridLayoutManager);
                    break;
                case AppConstants.ListView_2_Columns:
                case AppConstants.ListView_With_Image_2_Columns:
                case AppConstants.ListView_With_Image_3_Columns:
                case AppConstants.Geo_Spatial_View:
                case AppConstants.TimeLine_View:
                case AppConstants.TimeLine_With_Photo_View:
                case AppConstants.LinearView_With_Video:
                case AppConstants.BlogSpot_View:
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    if (controlObject.isEnableHorizontalScroll()) {
                        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    } else {
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    }
                    rvList.setLayoutManager(linearLayoutManager);
                    break;
                case AppConstants.ListView_With_Image_3_Columns_call:
                case AppConstants.MapView_Item_Info:
                case AppConstants.EV_News_Design:
                case AppConstants.EV_Jobs_Design:
                case AppConstants.EV_Notifications_Design:
                case AppConstants.EV_Dealers_Design:
                case AppConstants.EV_Dashboard_Design_One:
                case AppConstants.EV_Dashboard_Design_Two:
                case AppConstants.EV_Dashboard_Design_Three:
                    LinearLayoutManager linearLayoutManagerHorizontal = new LinearLayoutManager(context);
                    if (controlObject.isEnableHorizontalScroll()) {
                        linearLayoutManagerHorizontal.setOrientation(LinearLayoutManager.HORIZONTAL);
                    } else {
                        linearLayoutManagerHorizontal.setOrientation(LinearLayoutManager.VERTICAL);
                    }
                    rvList.setLayoutManager(linearLayoutManagerHorizontal);
                    break;
                case AppConstants.Button_Group:
                    LinearLayoutManager linearLayoutManagerHorizontal1 = new LinearLayoutManager(context);
                    linearLayoutManagerHorizontal1.setOrientation(LinearLayoutManager.HORIZONTAL);
                    rvList.setLayoutManager(linearLayoutManagerHorizontal1);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvList.getContext(),
                            linearLayoutManagerHorizontal1.getOrientation());

                    rvList.addItemDecoration(dividerItemDecoration);
                    break;
                case AppConstants.GRID_WITH_TWO_COLUMNS:
                    GridLayoutManager gridLayoutManager_ = new GridLayoutManager(context, 2);
                    gridLayoutManager_.setOrientation(LinearLayoutManager.VERTICAL);
                    rvList.setLayoutManager(gridLayoutManager_);
                case AppConstants.WIDGET_1:
                case AppConstants.WIDGET_2:
                case AppConstants.WIDGET_3:
                case AppConstants.WIDGET_5:
                case AppConstants.WIDGET_6:
                case AppConstants.WIDGET_7:
                case AppConstants.WIDGET_8:
                case AppConstants.WIDGET_9:
                case AppConstants.WIDGET_10:
                    rvList.setLayoutManager(layoutManager);
                    /*rvList.addItemDecoration(new DividerItemDecoration(rvList.getContext(),
                            layoutManager.getOrientation()));*/
                    break;

            }

            customAdapter = new DataViewerAdapter(context, dataViewerModelClassList, controlObject, tv_NoData);
            rvList.setAdapter(customAdapter);

            if (controlObject.isLazyLoadingEnabled()) {
                setOnScrollListener(rvList);
            }
        }catch (Exception e){
            Log.d(TAG, "dataViewerInItViews: "+e.toString());
        }
    }

    private void setOnScrollListener(RecyclerView rvList) {

        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int lastCompletelyVisibleItemPosition = -1;
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    lastCompletelyVisibleItemPosition = ((LinearLayoutManager) (recyclerView.getLayoutManager())).findLastCompletelyVisibleItemPosition();
                } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    lastCompletelyVisibleItemPosition = ((GridLayoutManager) (recyclerView.getLayoutManager())).findLastCompletelyVisibleItemPosition();
                }
                System.out.println("lastCompletelyVisibleItemPosition==" + lastCompletelyVisibleItemPosition);
                if (lastCompletelyVisibleItemPosition == recyclerView.getLayoutManager().getChildCount() - 1 && !isLoading) {

                    int min = controlObject.getCurrentMaxPosition() + 1;
                    int max = min + Integer.parseInt(controlObject.getThreshold()) - 1;
                    try {
                        lazyLoadingObject.put("Range", min + "-" + max);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    customAdapter.updateEmptyObject();
                    isLoading = true;
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        customAdapter.removeEmptyObject();
                        callFormFields(lazyLoadingObject, max);
                    }, 2000);
//                    callFormFields(lazyLoadingObject,max);
                }
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public LinearLayout getDataViewer() {
        return linearLayout;
    }

    public List<DataViewerModelClass> getDataViewerModelClassList() {
        return dataViewerModelClassList;
    }

    public void setDataViewerModelClassList(List<DataViewerModelClass> dataViewerModelClassList) {

        this.dataViewerModelClassList = dataViewerModelClassList;
    }

    @Override
    public void onCustomClick(Context context, View view, int position, String orgId) {
        System.out.println("position at Dataviewer==" + position);
        dataViewerModelClassList.get(position);
        view.setTag(controlObject.getControlName());
        AppConstants.EventFrom_subformOrNot = false;
        AppConstants.dw_position = position;
       /* //EV//
        if(dataViewerModelClassList.get(position).getNews_type()!=null && dataViewerModelClassList.get(position).getNews_type().equalsIgnoreCase("VIDEO")){

        }
        //EV//*/
        if(controlObject.isOnClickEventExists()&& !AppConstants.Initialize_Flag) {
            ((MainActivity) context).ClickEvent(view);
        }
    }

    public JSONObject getLazyLoadingObject() {
        return lazyLoadingObject;
    }

    public void setLazyLoadingObject(JSONObject lazyLoadingObject) {
        this.lazyLoadingObject = lazyLoadingObject;
    }

    private void callFormFields(JSONObject jsonObject, int max) {

        Map<String, String> maindata = new HashMap<>();
        maindata.put("Data", jsonObject.toString());
        System.out.println("dataJson==" + jsonObject);
        GetServices getServices = RetrofitUtils.getUserService();
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject) jsonParser.parse(jsonObject.toString());

//        Call<String> call = getServices.GetFormData(maindata);
        Call<String> call = getServices.GetFormData1(sessionManager.getAuthorizationTokenId(), jo);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("Result: ", "" + response.body());
                if (response.body() != null) {
                    String Outputcolumns = "", Outputcolumns_copy = "";
                    /*for (int i = 0; i < ActionBean.getList_Form_InParams().size(); i++) {
                        Outputcolumns = Outputcolumns + "," + ActionBean.getList_Form_InParams().get(i).getInParam_Name();
                        Outputcolumns_copy = Outputcolumns_copy + ",[" + ActionBean.getList_Form_InParams().get(i).getInParam_Name() + "]";
                    }
                    *//*if (gpsContainInInParams) {
                        Outputcolumns = Outputcolumns + "," + "DistanceInKM";
                    }*//*
                    Outputcolumns = Outputcolumns.substring(Outputcolumns.indexOf(",") + 1);
                    Outputcolumns_copy = Outputcolumns_copy.substring(Outputcolumns_copy.indexOf(",") + 1);*/
//                    String Outputcolumns = "", Outputcolumns_copy = "";
                    for (int i = 0; i < ActionBean.getList_Form_OutParams().size(); i++) {
                        if (ActionBean.getList_Form_OutParams().get(i).getOutParam_Mapped_ID() != null && !ActionBean.getList_Form_OutParams().get(i).getOutParam_Mapped_ID().equals("")) {
                            Outputcolumns = Outputcolumns + "," + ActionBean.getList_Form_OutParams().get(i).getOutParam_Mapped_ID();
                            Outputcolumns_copy = Outputcolumns_copy + ",[" + ActionBean.getList_Form_OutParams().get(i).getOutParam_Mapped_ID() + "]";
                        }else if(ActionBean.getList_Form_OutParams().get(i).getList_OutParam_Languages()!=null && ActionBean.getList_Form_OutParams().get(i).getList_OutParam_Languages().size()>0){
//                            String appLanguage = ImproveHelper.getLocale(context);
                            List<LanguageMapping> languageMappings = ActionBean.getList_Form_OutParams().get(i).getList_OutParam_Languages();
                            for (int j = 0; j <languageMappings.size() ; j++) {
                                Outputcolumns = Outputcolumns+","+languageMappings.get(j).getOutParam_Lang_Mapped();
                            }
                        }
                    }
                    /*if (gpsContainInInParams) {
                        Outputcolumns = Outputcolumns + "," + "DistanceInKM";
                    }*/
                    Outputcolumns = Outputcolumns.substring(Outputcolumns.indexOf(",") + 1);
                    EventsUtil eventsUtil = new EventsUtil(context);
                    eventsUtil.setTAG(TAG);
                    LinkedHashMap<String, List<String>> OutputData = eventsUtil.convert_JSON(response.body(), Outputcolumns.split("\\,"));

                    List<DataViewerModelClass> dataViewerModelClassList = new ArrayList<>();
                   /* if (ActionBean.getMulti_DataType().equalsIgnoreCase("append")) {
                        dataViewerModelClassList = getDataViewerModelClassList();
                    }*/

                    List<String> Header_list = OutputData.get(ActionBean.getHeader_Mapped_item().toLowerCase());
                    List<String> CornerText_list = new ArrayList<>();
                    List<String> SubHeader_list = new ArrayList<>();
                    List<String> DateandTime_list = new ArrayList<>();
                    List<String> ImagePath_list = new ArrayList<>();
                    List<String> ProfileImagePath_list = new ArrayList<>();
                    LinkedHashMap<String, List<String>> hash_Description = new LinkedHashMap<String, List<String>>();
                    List<String> Advance_ImageSource = new ArrayList<>();
                    List<String> Advance_ConditionColumn = new ArrayList<>();
                    //--EV--//
                    List<String> Distance_list = new ArrayList<>();
                    List<String> WorkingHours_list = new ArrayList<>();
                    List<String> ItemOne_list = new ArrayList<>();
                    List<String> ItemTwo_list = new ArrayList<>();
                    List<String> Rating_list = new ArrayList<>();
                    List<String> Source_Icons_list = new ArrayList<>();
                    List<String> Source_Name_list = new ArrayList<>();
                    List<String> Source_Time_list = new ArrayList<>();
                    List<String> News_Type_list = new ArrayList<>();
                    //--EV--//

                    if (ActionBean.getCorner_Mapped_item() != null && ActionBean.getCorner_Mapped_item().length() > 0) {
                        CornerText_list = OutputData.get(ActionBean.getCorner_Mapped_item().toLowerCase());
                    }

                    if (ActionBean.getSubHeader_Mapped_item() != null && ActionBean.getSubHeader_Mapped_item().length() > 0) {
                        SubHeader_list = OutputData.get(ActionBean.getSubHeader_Mapped_item().toLowerCase());
                    }
                    if (ActionBean.getDateandTime_Mapped_item() != null && ActionBean.getDateandTime_Mapped_item().length() > 0) {
                        DateandTime_list = OutputData.get(ActionBean.getDateandTime_Mapped_item().toLowerCase());
                    }

                    if (ActionBean.getImage_Mapped_item() != null && ActionBean.getImage_Mapped_item().length() > 0) {
                        ImagePath_list = OutputData.get(ActionBean.getImage_Mapped_item().toLowerCase());
                    }
                    if (ActionBean.getList_ImageAdvanced_Items() != null && ActionBean.getList_ImageAdvanced_Items().size() > 0) {
                        if (ActionBean.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                            Advance_ImageSource = OutputData.get(ActionBean.getImageAdvanced_ImageSource().toLowerCase());
                        }
                        Advance_ConditionColumn = OutputData.get(ActionBean.getImageAdvanced_ConditionColumn().toLowerCase());
                    }

                    if (ActionBean.getProfileImage_Mapped_item() != null && ActionBean.getProfileImage_Mapped_item().length() > 0) {
                        ProfileImagePath_list = OutputData.get(ActionBean.getProfileImage_Mapped_item().toLowerCase());
                    }
                    if (ActionBean.getDescription_Mapped_item() != null && ActionBean.getDescription_Mapped_item().size() > 0) {
                        for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                            List<String> X_ItemList = OutputData.get(ActionBean.getDescription_Mapped_item().get(x).toLowerCase());
                            hash_Description.put(ActionBean.getDescription_Mapped_item().get(x), X_ItemList);
                        }
                    }
                    //--EV--//
                    if (ActionBean.getDistance() != null && ActionBean.getDistance().length() > 0) {
                        Distance_list = OutputData.get(ActionBean.getDistance().toLowerCase());
                    }
                    if (ActionBean.getWorking_hours() != null && ActionBean.getWorking_hours().length() > 0) {
                        WorkingHours_list = OutputData.get(ActionBean.getWorking_hours().toLowerCase());
                    }
                    if (ActionBean.getItem_one_count() != null && ActionBean.getItem_one_count().length() > 0) {
                        ItemOne_list = OutputData.get(ActionBean.getItem_one_count().toLowerCase());
                    }
                    if (ActionBean.getItem_two_count() != null && ActionBean.getItem_two_count().length() > 0) {
                        ItemTwo_list = OutputData.get(ActionBean.getItem_two_count().toLowerCase());
                    }
                    if (ActionBean.getRating() != null && ActionBean.getRating().length() > 0) {
                        Rating_list = OutputData.get(ActionBean.getRating().toLowerCase());
                    }
                    if (ActionBean.getSource_icon() != null && ActionBean.getSource_icon().length() > 0) {
                        Source_Icons_list = OutputData.get(ActionBean.getSource_icon().toLowerCase());
                    }
                    if (ActionBean.getSource_name() != null && ActionBean.getSource_name().length() > 0) {
                        Source_Name_list = OutputData.get(ActionBean.getSource_name().toLowerCase());
                    }
                    if (ActionBean.getSource_time() != null && ActionBean.getSource_time().length() > 0) {
                        Source_Time_list = OutputData.get(ActionBean.getSource_time().toLowerCase());
                    }
                    if (ActionBean.getNews_type() != null && ActionBean.getNews_type().length() > 0) {
                        News_Type_list = OutputData.get(ActionBean.getNews_type().toLowerCase());
                    }
                    //--EV--//
                    if (Header_list != null) {

                        for (int i = 0; i < Header_list.size(); i++) {
                            DataViewerModelClass dmv = new DataViewerModelClass();
                            dmv.setHeading(Header_list.get(i));
                            List<String> Description = new ArrayList<String>();
                            switch (controlObject.getDataViewer_UI_Pattern()) {
                                case AppConstants.GridView_With_Image_2_Columns:
                                    if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                        dmv.setImage_Path(ImagePath_list.get(i));
                                    }
                                    if (CornerText_list != null && CornerText_list.size() != 0) {
                                        dmv.setCornerText(CornerText_list.get(i));
                                    }

                                    if (ActionBean.getList_ImageAdvanced_Items() != null && ActionBean.getList_ImageAdvanced_Items().size() > 0) {
                                        if (ActionBean.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                            dmv.setImage_Path(null);
                                            dmv.setAdvanceImage_Source(Advance_ImageSource.get(i));
                                        }
                                        dmv.setList_Image_Path(ActionBean.getList_ImageAdvanced_Items());
                                        dmv.setAdvanceImage_ConditionColumn(Advance_ConditionColumn.get(i));
                                    }
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    dmv.setDescription(Description);
                                    break;
                                case AppConstants.GridView_With_Image_3_Columns:
                                    dmv.setSubHeading(SubHeader_list.get(i));
                                    if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                        dmv.setImage_Path(ImagePath_list.get(i));
                                    }
                                    if (CornerText_list != null && CornerText_list.size() != 0) {
                                        dmv.setCornerText(CornerText_list.get(i));
                                    }
                                    if (ActionBean.getList_ImageAdvanced_Items() != null && ActionBean.getList_ImageAdvanced_Items().size() > 0) {
                                        if (ActionBean.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                            dmv.setImage_Path(null);
                                            dmv.setAdvanceImage_Source(Advance_ImageSource.get(i));
                                        }
                                        dmv.setList_Image_Path(ActionBean.getList_ImageAdvanced_Items());
                                        dmv.setAdvanceImage_ConditionColumn(Advance_ConditionColumn.get(i));
                                    }
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    dmv.setDescription(Description);
                                    break;
                                case AppConstants.GridView_With_Image_2_Columns_call:
                                    if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                        dmv.setImage_Path(ImagePath_list.get(i));
                                    }
                                    if (ActionBean.getList_ImageAdvanced_Items() != null && ActionBean.getList_ImageAdvanced_Items().size() > 0) {
                                        if (ActionBean.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                            dmv.setImage_Path(null);
                                            dmv.setAdvanceImage_Source(Advance_ImageSource.get(i));
                                        }
                                        dmv.setList_Image_Path(ActionBean.getList_ImageAdvanced_Items());
                                        dmv.setAdvanceImage_ConditionColumn(Advance_ConditionColumn.get(i));
                                    }
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    dmv.setDescription(Description);
                                    break;
                                case AppConstants.GridView_With_Image_3_Columns_call:
                                    dmv.setSubHeading(SubHeader_list.get(i));
                                    if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                        dmv.setImage_Path(ImagePath_list.get(i));
                                    }
                                    if (ActionBean.getList_ImageAdvanced_Items() != null && ActionBean.getList_ImageAdvanced_Items().size() > 0) {
                                        if (ActionBean.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                            dmv.setImage_Path(null);
                                            dmv.setAdvanceImage_Source(Advance_ImageSource.get(i));
                                        }
                                        dmv.setList_Image_Path(ActionBean.getList_ImageAdvanced_Items());
                                        dmv.setAdvanceImage_ConditionColumn(Advance_ConditionColumn.get(i));
                                    }
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    dmv.setDescription(Description);
                                    break;
                                case AppConstants.GridView_With_Video_2_Columns:
                                    if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                        dmv.setVideo_Path(ImagePath_list.get(i));
                                    }
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    dmv.setDescription(Description);
                                    break;
                                case AppConstants.GridView_With_Video_3_Columns:
                                    dmv.setSubHeading(SubHeader_list.get(i));
                                    if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                        dmv.setVideo_Path(ImagePath_list.get(i));
                                    }
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    dmv.setDescription(Description);
                                    break;
                                case AppConstants.GridView_With_Video_2_Columns_call:
                                    if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                        dmv.setVideo_Path(ImagePath_list.get(i));
                                    }
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    dmv.setDescription(Description);
                                    break;
                                case AppConstants.GridView_With_Video_3_Columns_call:
                                    dmv.setSubHeading(SubHeader_list.get(i));
                                    if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                        dmv.setVideo_Path(ImagePath_list.get(i));
                                    }
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    dmv.setDescription(Description);
                                    break;
                                case AppConstants.ListView_2_Columns:
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    if (CornerText_list != null && CornerText_list.size() != 0) {
                                        dmv.setCornerText(CornerText_list.get(i));
                                    }

                                    dmv.setDescription(Description);
                                    break;
                                case AppConstants.ListView_With_Image_2_Columns:
                                    if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                        dmv.setImage_Path(ImagePath_list.get(i));
                                    }
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    if (CornerText_list != null && CornerText_list.size() != 0) {
                                        dmv.setCornerText(CornerText_list.get(i));
                                    }

                                    dmv.setDescription(Description);
                                    break;
                                case AppConstants.ListView_With_Image_3_Columns_call:
                                case AppConstants.ListView_With_Image_3_Columns:
                                    dmv.setSubHeading(SubHeader_list.get(i));
                                    if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                        dmv.setImage_Path(ImagePath_list.get(i));
                                    }
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    if (CornerText_list != null && CornerText_list.size() != 0) {
                                        dmv.setCornerText(CornerText_list.get(i));
                                    }

                                    dmv.setDescription(Description);
                                    break;
                                case AppConstants.MapView_Item_Info:
                                    dmv.setSubHeading(SubHeader_list.get(i));
                                    dmv.setDistance(Distance_list.get(i));
                                    dmv.setWorkingHours(WorkingHours_list.get(i));
                                    dmv.setItemOneCount(ItemOne_list.get(i));
                                    dmv.setItemTwoCount(ItemTwo_list.get(i));
                                case AppConstants.Geo_Spatial_View:
                                    if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                        dmv.setGpsValue(ImagePath_list.get(i));
                                    }
                                    if (SubHeader_list.size() > 0) {
                                        dmv.setSubHeading(SubHeader_list.get(i));
                                    }
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        if (xItem != null) {
                                            Description.add(xItem.get(i));
                                        }
                                    }
                                    if (Description.size() > 0) {
                                        dmv.setDescription(Description);
                                    } else {
                                        dmv.setDescription(new ArrayList<>());
                                    }
                                    break;
                                case AppConstants.TimeLine_View:
                                    dmv.setDateandTime(DateandTime_list.get(i));
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    dmv.setDescription(Description);
                                    break;
                                case AppConstants.TimeLine_With_Photo_View:
                                    if (ProfileImagePath_list != null && ProfileImagePath_list.size() != 0) {
                                        dmv.setProfileImage_Path(ProfileImagePath_list.get(i));
                                    }

                                    dmv.setDateandTime(DateandTime_list.get(i));
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    dmv.setDescription(Description);
                                    break;
                                case AppConstants.LinearView_With_Video:
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    dmv.setDescription(Description);
                                    break;
                                case AppConstants.BlogSpot_View:
                                    if (ProfileImagePath_list != null && ProfileImagePath_list.size() != 0) {
                                        dmv.setProfileImage_Path(ProfileImagePath_list.get(i));
                                    }
                                    if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                        dmv.setImage_Path(ImagePath_list.get(i));
                                    }
                                    dmv.setSubHeading(SubHeader_list.get(i));
                                    dmv.setImage_Path(ImagePath_list.get(i));
                                    break;
                                case AppConstants.EV_Dashboard_Design_One:
                                case AppConstants.EV_Dashboard_Design_Three:
                                    dmv.setSubHeading(SubHeader_list.get(i));
                                    if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                        dmv.setImage_Path(ImagePath_list.get(i));
                                    }
                                    dmv.setHeading(Header_list.get(i));
                                    dmv.setSubHeading(SubHeader_list.get(i));
                                    dmv.setRating(Rating_list.get(i));
                                    break;
                                case AppConstants.EV_Dashboard_Design_Two:
                                    dmv.setSubHeading(SubHeader_list.get(i));
                                    if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                        dmv.setImage_Path(ImagePath_list.get(i));
                                    }
                                    dmv.setHeading(Header_list.get(i));
                                    break;
                                case AppConstants.EV_News_Design:
                                    if (Source_Icons_list.size() > 0) {
                                        dmv.setSource_icon(Source_Icons_list.get(i));
                                        dmv.setSource_name(Source_Name_list.get(i));
                                        dmv.setSource_time(Source_Time_list.get(i));
                                    }
                                    dmv.setNews_type(News_Type_list.get(i));
                                    if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                        dmv.setImage_Path(ImagePath_list.get(i));
                                    }
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    dmv.setDescription(Description);
                                    break;
                                case AppConstants.EV_Notifications_Design:
                                    dmv.setSubHeading(SubHeader_list.get(i));
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    dmv.setDescription(Description);
                                    break;
                                case AppConstants.EV_Dealers_Design:
                                    dmv.setSubHeading(SubHeader_list.get(i));
                                    dmv.setDistance(Distance_list.get(i));
                                    dmv.setWorkingHours(WorkingHours_list.get(i));
                                    if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                        dmv.setImage_Path(ImagePath_list.get(i));
                                    }
                                    break;
                                case AppConstants.EV_Jobs_Design:
                                    dmv.setSubHeading(SubHeader_list.get(i));
                                    dmv.setSource_name(Source_Name_list.get(i));
                                    dmv.setSource_time(Source_Time_list.get(i));
                                    for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                        List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                        Description.add(xItem.get(i));
                                    }
                                    dmv.setDescription(Description);
                                    break;
                            }
                            dataViewerModelClassList.add(dmv);
                        }

                        customAdapter.update(dataViewerModelClassList, customAdapter.getItemCount());
                        isLoading = false;
                        controlObject.setCurrentMaxPosition(max);
                    }


                } else {
                    ImproveHelper.showToast(context, "No Data Found...");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    public LinkedHashMap<String, List<String>> Convert_JSON(String serviceResponse, String[] OutParam_Names) {
        LinkedHashMap<String, List<String>> OutputData = new LinkedHashMap<String, List<String>>();
        try {
            JSONObject jmainobj = new JSONObject(serviceResponse);
            if (jmainobj.getString("Status").equalsIgnoreCase("200")) {
                JSONArray Jarr = jmainobj.getJSONArray("FormData");
                for (int i = 0; i < Jarr.length(); i++) {
                    JSONObject Jobj = Jarr.getJSONObject(i);
                    for (int j = 0; j < OutParam_Names.length; j++) {

                        String jobjvalue = "";
                        if (!Jobj.isNull(OutParam_Names[j])) {
                            jobjvalue = Jobj.getString(OutParam_Names[j]);
                        }
                        if (OutputData.containsKey(OutParam_Names[j].toLowerCase())) {
                            List<String> value = OutputData.get(OutParam_Names[j].toLowerCase());
                            value.add(jobjvalue);
                            OutputData.put(OutParam_Names[j].toLowerCase(), value);
                        } else {
                            List<String> value = new ArrayList<String>();
                            value.add(jobjvalue);
                            OutputData.put(OutParam_Names[j].toLowerCase(), value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "Convert_JSON", e);
        }

        return OutputData;
    }

    public ActionWithoutCondition_Bean getActionBean() {
        return ActionBean;
    }

    public void setActionBean(ActionWithoutCondition_Bean actionBean) {
        ActionBean = actionBean;
    }

    public void setCustomClickListener(SubFormDeleteInterface subFormDeleteInterface) {
        this.subFormDeleteInterface = subFormDeleteInterface;
    }

    public LinkedHashMap<String, List<String>> getOutputData() {
        return outputData;
    }

    public void setOutputData(LinkedHashMap<String, List<String>> outputData) {
        this.outputData = outputData;
    }

    public DataViewerModelClass getDataViewerModel(int position) {
        return dataViewerModelClassList.get(position);
    }

    @Override
    public String getTextSize() {
        return controlObject.getTextSize();
    }

    @Override
    public void setTextSize(String size) {
        if (size != null && !size.isEmpty()) {
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
        if(style != null && !style.isEmpty()) {
            if (style.equalsIgnoreCase("Bold")) {
                Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
                tv_displayName.setTypeface(typeface_bold);
                controlObject.setTextStyle(style);
            } else if (style.equalsIgnoreCase("Italic")) {
                Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
                tv_displayName.setTypeface(typeface_italic);
                controlObject.setTextStyle(style);
            }
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

    public void setSelection(String id) {
        for (int i = 0; i < dataViewerModelClassList.size(); i++) {
            DataViewerModelClass dataViewerModelClass = dataViewerModelClassList.get(i);
            if (dataViewerModelClass.getDv_trans_id() != null && id.contentEquals(dataViewerModelClass.getDv_trans_id())) {
                adapterPosition = i;
                break;
            }
        }
        if (adapterPosition != -1) {
            customAdapter.setSelectedItemPositionInAdapter(adapterPosition);
            rvList.scrollToPosition(adapterPosition);
        }
    }

    public void setVisibility(boolean visible) {
        if (visible) {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        } else {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }

    public void setDisplayName(String propertyText) {
        controlObject.setDisplayName(propertyText);
        tv_displayName.setVisibility(View.VISIBLE);
        tv_displayName.setText(propertyText);
    }

    public void setHint(String propertyText) {
        controlObject.setHint(propertyText);
        if (propertyText != null && !propertyText.isEmpty()) {
            tv_hint.setVisibility(View.VISIBLE);
            tv_hint.setText(propertyText);
        } else {
            tv_hint.setVisibility(View.GONE);
        }
    }

    public void setHideDisplayName(Boolean valueOf) {
        controlObject.setHideDisplayName(valueOf);
        if (valueOf) {
            tv_displayName.setVisibility(View.GONE);
            tv_hint.setVisibility(View.GONE);
        }else{
            tv_displayName.setVisibility(View.VISIBLE);
            if(controlObject.getHint() != null && !controlObject.getHint().isEmpty()){
                tv_hint.setVisibility(View.VISIBLE);
            }else {
                tv_hint.setVisibility(View.GONE);
            }
        }
    }


    public void dataViewerSetPropertiesAction(List<Param> propertiesListMain) {
        try {
            List<Param> propertiesList = new ArrayList<>();
            String propertyText = "";
            ExpressionMainHelper expressionMainHelper = new ExpressionMainHelper();
            propertiesList = propertiesListMain;
            if (propertiesList != null && propertiesList.size() > 0) {
                for (int i = 0; i < propertiesList.size(); i++) {
                    Param property = propertiesList.get(i);
                    if (property.getName().contentEquals("ExpressionBuilder")) {
                        propertyText = expressionMainHelper.ExpressionHelper(context, property.getText());
                    } else {
                        propertyText = property.getText();
                    }
                    if (!propertyText.contentEquals("")) {
                        if (property.getValue().contentEquals(PropertiesNames.DISPLAY_NAME)) {
                            setDisplayName(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.HINT)) {
                            setHint(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.HORIZONTAL_SCROLL)) {
                            controlObject.setEnableHorizontalScroll(Boolean.valueOf(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.THRESHOLD)) {
                            controlObject.setThreshold(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.LAZY_LOADING)) {
                            controlObject.setLazyLoadingEnabled(Boolean.valueOf(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.SEARCH_BASED_ON)) {
                            controlObject.setDataViewer_searchEnabled(Boolean.valueOf(propertyText));
                            if(Boolean.valueOf(propertyText)){
                                et_search.setVisibility(View.VISIBLE);
                            } else {
                                et_search.setVisibility(View.GONE);
                            }
                        } else if (property.getValue().contentEquals(PropertiesNames.SEARCH_HEADER)) {
                            controlObject.setDataViewer_HeaderSearchEnabled(Boolean.valueOf(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.SEARCH_SUB_HEADER)) {
                            controlObject.setDataViewer_SubHeaderSearchEnabled(Boolean.valueOf(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.SEARCH_DESCRIPTION)) {
                            controlObject.setDataViewer_DescriptionSearchEnabled(Boolean.valueOf(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.UI_PATTERN)) {
                            controlObject.setDataViewer_UI_Pattern(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.DATA_VIEWER_SHAPE)) {
                            controlObject.setDataViewer_Shape(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.DEFAULT_IMAGE_PATH)) {
                            controlObject.setDataViewer_DefualtImage_path(propertyText.trim());
                        }  else if (property.getValue().contentEquals(PropertiesNames.PROFILE_IMAGE_PATH)) {
                            controlObject.setDataViewer_DefualtImage_path(propertyText.trim());
                        } else if (property.getValue().contentEquals(PropertiesNames.FRAME_BG_HEX_COLOR)) {
                            controlObject.setDataViewer_FrameBG_HexColor(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.HEADER_HEX_COLOR)) {
                            controlObject.setDataViewer_Header_HexColor(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.SUB_HEADER_HEX_COLOR)) {
                            controlObject.setDataViewer_SubHeader_HexColor(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.DESCRIPTION_HEX_COLOR)) {
                            controlObject.setDataViewer_Description_HexColor(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.DATE_TIME_HEX_COLOR)) {
                            controlObject.setDataViewer_DateTime_HexColor(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.HIDE_DISPLAY_NAME)) {
                            setHideDisplayName(Boolean.valueOf(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.INVISIBLE)) {
                            setVisibility(!Boolean.parseBoolean(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.FONT_SIZE)) {
                            setTextSize(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.FONT_COLOR)) {
                            setTextColor(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.FONT_STYLE)) {
                            setTextStyle(propertyText);
                        }
                    }
                }

                dataViewerInItViews();
            }
        }catch (Exception e){
            Log.d(TAG, "dataViewerSetPropertiesAction: "+e.toString());
        }
    }
    Timer timer;
    public void initSlider(List<DataViewerModelClass> dataViewerModelClassList){


        WidgetSliderAdapter widgetSliderAdapter = new WidgetSliderAdapter(context,dataViewerModelClassList,controlObject);

        widget5_viewPager.setAdapter(widgetSliderAdapter);

        int dotscount = widgetSliderAdapter.getCount();
        ImageView[] dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(context);
            dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDots.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));

        widget5_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                widget5_viewPager.post(new Runnable(){

                    @Override
                    public void run() {
                        widget5_viewPager.setCurrentItem((widget5_viewPager.getCurrentItem()+1)%dataViewerModelClassList.size());
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);

    }
    public ControlObject getControlObject() {
        return controlObject;
    }
}
