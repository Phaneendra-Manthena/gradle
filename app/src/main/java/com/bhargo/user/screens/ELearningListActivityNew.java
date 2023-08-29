package com.bhargo.user.screens;

import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.FilesInfoModel;
import com.bhargo.user.pojos.GetUserDistributionsResponse;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.DownloadFilesFromURL;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ELearningListActivityNew extends BaseActivity {

    private static final String TAG = "ELearningListActivity";
    ScrollView svEl;
    LinearLayout ll_svELearning, ll_eLearningMain;
    HashMap<String, List<FilesInfoModel>> stringListHashMap;
    //    HashMap<String, List<String>> stringListHashMap;
    List<String> headerList;
    Context context;
    ImproveHelper improveHelper;
    SessionManager sessionManager;
    List<FilesInfoModel> filesInfoModels = new ArrayList<FilesInfoModel>();
    List<GetUserDistributionsResponse> GetUserDistributionsList = new ArrayList<GetUserDistributionsResponse>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Theme
        setBhargoTheme(this, AppConstants.THEME, AppConstants.IS_FORM_THEME, "");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_learning_list_copy);

        context = ELearningListActivityNew.this;
        initializeActionBar();
        enableBackNavigation(true);
        improveHelper = new ImproveHelper(context);
        sessionManager = new SessionManager(context);
        svEl = findViewById(R.id.svEl);
        ll_svELearning = findViewById(R.id.ll_svELearning);
        ll_eLearningMain = findViewById(R.id.ll_eLearningMain);

//       IntentBundleData
        filesInfoModels = (List<FilesInfoModel>) getIntent().getSerializableExtra("FilesInfoModelList");
        GetUserDistributionsList = (List<GetUserDistributionsResponse>) getIntent().getSerializableExtra("GetUserDistributionsList");

        stringListHashMap = new HashMap<>();
//        stringListHashMap = ExpandableListSampleData.getData();

//        stringListHashMap.put(filesInfoModels.get(0).getSelectedFileName(),stringListHashMap);
        for (int f = 0; f < filesInfoModels.size(); f++) {
            stringListHashMap.put(filesInfoModels.get(f).getSelectedFileName(), filesInfoModels);
            if (stringListHashMap != null && stringListHashMap.keySet() != null && stringListHashMap.keySet().size() > 0) {
                headerList = new ArrayList<>(stringListHashMap.keySet());
                if (headerList != null && headerList.size() > 0) {
                    if (headerList.size() == 1) {
                        childStrip(ll_eLearningMain, 0, headerList.get(0), 0, true, stringListHashMap, headerList.get(0));
                    } else {
                        for (int i = 0; i < headerList.size(); i++) {
                            //                    headerStrip(ll_eLearningMain, i, headerList.get(i), stringListHashMap);
                            headerStrip(ll_eLearningMain, i, headerList.get(i), stringListHashMap);
                        }
                    }
                }
            }
        }
    }

    //    private void headerStrip(LinearLayout ll_eLearningMain, int i, String strHeader, HashMap<String, List<String>> stringListHashMap) {
    private void headerStrip(LinearLayout ll_eLearningMain, int i, String strHeader, HashMap<String, List<FilesInfoModel>> stringListHashMap) {
        try {
            final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View hView = lInflater.inflate(R.layout.el_parent_item_layout, null);

            LinearLayout ll_parent = hView.findViewById(R.id.ll_parent);
            LinearLayout ll_childMain = hView.findViewById(R.id.ll_childMain);
            CustomTextView tv_topicName = hView.findViewById(R.id.tv_topicName);
            CustomButton btn_expand = hView.findViewById(R.id.btn_expand);
            CustomButton btn_collapse = hView.findViewById(R.id.btn_collapse);
            String strHeaderRemoveQuotes = strHeader.replaceAll("'","");
            tv_topicName.setText(strHeaderRemoveQuotes);
            tv_topicName.setTag(i);

//            List<String> childList = stringListHashMap.get(strHeader);
            List<FilesInfoModel> childList = stringListHashMap.get(strHeader);
            if (childList != null && childList.size() > 0) { // Inside folders

                ll_parent.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_default_gray_bg));
                ll_childMain.setVisibility(View.GONE);
                btn_expand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_collapse.setVisibility(View.VISIBLE);
                        btn_expand.setVisibility(View.GONE);
                        ll_parent.setBackground(context.getResources().getDrawable(R.drawable.top_left_right_rounded_corners_default_gray_bg));
                        ll_childMain.setVisibility(View.VISIBLE);
                    }
                });
                btn_collapse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_collapse.setVisibility(View.GONE);
                        btn_expand.setVisibility(View.VISIBLE);
                        ll_parent.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_default_gray_bg));
                        ll_childMain.setVisibility(View.GONE);
                    }
                });
                for (int j = 0; j < childList.size(); j++) {
//                        childStrip(ll_childMain, j, childList.get(j), childList.size(), false, stringListHashMap, strHeader);
                    childStrip(ll_childMain, j, childList.get(j).getSelectedFileName(), childList.size(), false, stringListHashMap, strHeader);
                }

                ll_eLearningMain.addView(hView);
            }

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "headerStrip", e);
        }
    }


    //    private void childStrip(LinearLayout ll_childMain, int position, String strChild, int size, boolean isSingleFile, HashMap<String, List<String>> stringListHashMap, String strHeader) {
    private void childStrip(LinearLayout ll_childMain, int position, String strChild, int size, boolean isSingleFile, HashMap<String, List<FilesInfoModel>> fileInfoModelList, String strHeader) {
        try {
            final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View cView = lInflater.inflate(R.layout.el_child_item_layout, null);
            LinearLayout ll_child_item_main = cView.findViewById(R.id.ll_child_item_main);
            LinearLayout ll_el_child = cView.findViewById(R.id.ll_el_child);
            ImageView iv_fileTypeIcon = cView.findViewById(R.id.iv_fileTypeIcon);
            CustomTextView tv_fileName = cView.findViewById(R.id.tv_fileName);
            View dividerView = cView.findViewById(R.id.dividerView);
            if (size != 0 && position == (size - 1)) {
                dividerView.setVisibility(View.GONE);
            }
            if (isSingleFile && size == 0) {
                ll_el_child.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_default_white_bg));
                ll_child_item_main.setPadding(improveHelper.dpToPx(20), improveHelper.dpToPx(5), improveHelper.dpToPx(20), improveHelper.dpToPx(5));
                dividerView.setVisibility(View.GONE);
            }
            String strChildRemoveQuotes = strChild.replaceAll("'","");
            tv_fileName.setText(strChildRemoveQuotes);
            tv_fileName.setTag(position);
            ImproveHelper.loadImageForDataViewer(context,fileInfoModelList.get(strHeader).get(position).getFileCoverImage().trim().replaceAll("'",""),iv_fileTypeIcon,false);
//            Glide.with(context).load(fileInfoModelList.get(strHeader).get(position).getFileCoverImage().trim().replaceAll("'","")).into(iv_fileTypeIcon);

            ll_child_item_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.d(TAG, "onClickELLA: "+"onClickELLA");
                    ImproveHelper.showToastAlert(context, strChildRemoveQuotes);
//                    "http://103.117.174.17/ImproveUploadFiles/UploadFiles/EVStations/favorite_wishlist_red.png"
                    new DownloadFilesFromURL(context, fileInfoModelList.get(strHeader).get(position).getDownloadUrl(), sessionManager, v, TAG, strHeader, null, new DownloadFilesFromURL.DownloadFileListener() {
//                    new DownloadFilesFromURL(context, stringListHashMap.get(strHeader).get(0).getDownloadUrl(), sessionManager, v, TAG, strHeader,null, new DownloadFilesFromURL.DownloadFileListener() {

                        @Override
                        public void onSuccess(File file, String dataControlName) {

                        }

                        @Override
                        public void onFailed(String errorMessage) {

                        }
                    });
//                    new DownloadFilesFromURL(context,"http://103.117.174.17/ImproveUploadFiles/UploadFiles/EVStations/notifications_notifications.png",sessionManager,v,TAG,strHeader);
                }
            });
            tv_fileName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.d(TAG, "onClickELLA: "+"onClickELLA");
                    ImproveHelper.showToastAlert(context, strChildRemoveQuotes);
                    navigateToELearningView("http://103.117.174.17/ImproveUploadFiles/UploadFiles/EVStations/favorite_wishlist_red.png",strHeader);
//                    new DownloadFilesFromURL(context,"http://103.117.174.17/ImproveUploadFiles/UploadFiles/EVStations/favorite_wishlist_red.png",sessionManager,v,TAG,strHeader);
//                    new DownloadFilesFromURL(context,"http://103.117.174.17/ImproveUploadFiles/UploadFiles/EVStations/notifications_notifications.png",sessionManager,v,TAG,strHeader);
                }
            });

            ll_childMain.addView(cView);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "childStrip", e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateOnBackPressAlertDialog();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {

        navigateOnBackPressAlertDialog();
    }

    public void navigateOnBackPressAlertDialog() {
        try {
            hideKeyboard(context, view);
            ImproveHelper.alertDialogWithRoundShapeMaterialTheme(context, getString(R.string.are_you_sure), getString(R.string.yes), getString(R.string.d_no), new ImproveHelper.IL() {
                @Override
                public void onSuccess() {
                    finish();
                }

                @Override
                public void onCancel() {

                }
            });

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "navigateOnBackPressAlertDialog", e);
        }
    }

    public void navigateToELearningView(String strUrl, String strTopicName) {
        Intent intent = new Intent(context, ELearningViewActivity.class);
//
        intent.putExtra("ELearning_Type", "");
        intent.putExtra("FileName", "");
        intent.putExtra("ELearning_URL", strUrl);
        intent.putExtra("ELearning_FilesInfoPosition", "");
        intent.putExtra("FileNameExt", "");
        intent.putExtra("ELearning_FilesInfoList", "");
        intent.putExtra("TopicName", strTopicName);
        intent.putExtra("distributionIdView", "");
        intent.putExtra("ParentID", "");
        intent.putExtra("Selected_Node_Id", "");
        intent.putExtra("CategoryFileID", "");

        startActivity(intent);
        finish();

    }


}


