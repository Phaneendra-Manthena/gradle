package com.bhargo.user.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.SessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettingsMenuButton;

public class CustomGridRecyclerAdapter extends RecyclerView.Adapter<CustomGridRecyclerAdapter.MyViewHolder> {

    private static final String TAG = "CustomGridRecAdapter";
    private final int menuListItemsSize;
    List<ControlObject> typeOfButtonModel = new ArrayList<>();
    //    List<GridImagesModel> typeOfButtonModel;
    Context context;
    String strViewType, strTypeOfButton, orgId, appName;
    ControlObject coMenu;
    int noOfRows, notOfColumns;
    ControlObject controlObject;
    List<Integer> itemRemove = new ArrayList<>();
    int iTypeOfBorder;
    ArrayList<String> f = new ArrayList<String>();// list of file paths
    File[] listFile;
    int Marginleft,Marginright,Margintop,Marginbottom;
    SessionManager sessionManager;


    public CustomGridRecyclerAdapter(Context context, ControlObject controlObject, String strViewType,
                                     int noOfRows, int notOfColumns, List<ControlObject> typeOfButtonModel,
                                     int iTypeOfBorder, String strTypeOfButton, String orgId, String appName,int Marginleft
            ,int Marginright,int Margintop,int Marginbottom) {

        this.context = context;
        this.controlObject = controlObject;
        this.strViewType = strViewType;
        this.noOfRows = noOfRows;
        this.notOfColumns = notOfColumns;
        this.typeOfButtonModel = typeOfButtonModel;
        this.iTypeOfBorder = iTypeOfBorder;
        this.strTypeOfButton = strTypeOfButton;
        this.orgId = orgId;
        this.appName = appName;

        this.Marginleft=Marginleft;
        this.Marginright=Marginright;
        this.Margintop=Margintop;
        this.Marginbottom=Marginbottom;
        sessionManager = new SessionManager(context);
        Log.d(TAG, "CustomGridRecyclerAdapter: " + typeOfButtonModel.size());
        menuListItemsSize = typeOfButtonModel.size();

    }

    public static float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = null;
//        if (strViewType.equalsIgnoreCase("Grid")) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_view_item, parent, false);
//        } else {
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_view_item_linear, parent, false);
//        }
        // set the view's size, margins, paddings and layout_sample_app parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items

        try {
//            strTypeOfButton = typeOfButtonModel.get(position).getTypeOfButton();  // Type of button
//            strTypeOfButton = typeOfButtonModel.get(position).getTypeOfButton();  // Type of button
//            strTypeOfButton = "2";
            Log.d(TAG, "strTypeOfButton: " + strTypeOfButton);

            // set margins for item
            Log.d(TAG, "onBindViewHolderBOrderRight: " + controlObject.getBorderMarginRight());

     /*       if (controlObject.getBorderMarginLeft() != null || !controlObject.getBorderMarginLeft().equals("null")) {
                coMarginleft = Integer.parseInt(controlObject.getBorderMarginLeft());
                Log.d(TAG, "ItemMarginPixel: " + coMarginleft);
            }
            if (controlObject.getBorderMarginTop() != null || !controlObject.getBorderMarginTop().equals("null")) {
                coMarginTop = Integer.parseInt(controlObject.getBorderMarginTop());
                Log.d(TAG, "ItemMarginPixel: " + coMarginTop);
            }
            if (controlObject.getBorderMarginRight() != null || !controlObject.getBorderMarginRight().equals("null")) {
                coMarginRight = Integer.parseInt(controlObject.getBorderMarginRight());
                Log.d(TAG, "ItemMarginPixel: " + coMarginRight);
            }
            if (controlObject.getBorderMarginBottom() != null || !controlObject.getBorderMarginBottom().equals("null")) {
                coMarginBottom = Integer.parseInt(controlObject.getBorderMarginBottom());
                Log.d(TAG, "ItemMarginPixel: " + coMarginBottom);
            }
            LinearLayout.LayoutParams co_LayoutMargin = (LinearLayout.LayoutParams) holder.ll_grid_item.getLayoutParams();
            co_LayoutMargin.setMargins(coMarginleft, coMarginTop, coMarginRight, coMarginBottom);
//            co_LayoutMargin.setMargins(10,10,10,10);
            holder.ll_grid_item.setLayoutParams(co_LayoutMargin);
            Log.d(TAG, "onBindViewHolderMargin: " + coMarginleft + coMarginTop + coMarginRight + coMarginBottom);
            // set margins for item end

            // Set Padding for item from Control Object
            if (controlObject.getBorderPaddingLeft() != null || !controlObject.getBorderPaddingLeft().equals("null")) {

                coPaddingleft = Integer.parseInt(controlObject.getBorderPaddingLeft());
            }

            if (controlObject.getBorderPaddingTop() != null) {

                coPaddingTop = Integer.parseInt(controlObject.getBorderPaddingTop());
            }
            if (controlObject.getBorderPaddingRight() != null || !controlObject.getBorderPaddingRight().equalsIgnoreCase("null")) {

                coPaddingRight = Integer.parseInt(controlObject.getBorderPaddingRight());
            }
            if (controlObject.getBorderPaddingBottom() != null || !controlObject.getBorderPaddingBottom().equalsIgnoreCase("null")) {
                coPaddingBottom = Integer.parseInt(controlObject.getBorderPaddingBottom());
            }
            holder.ll_grid_item.setPadding(coPaddingleft, coPaddingTop, coPaddingRight, coPaddingBottom);
//            holder.ll_grid_item.setPadding(10,10,10,10);
            Log.d(TAG, "onBindViewHolderPadding: " + coPaddingleft + coPaddingTop + coPaddingRight + coPaddingBottom);
*/

            if (typeOfButtonModel.get(position).isHideDisplayName()) {
                holder.grid_item_name.setVisibility(View.GONE);
            } else {
                holder.grid_item_name.setVisibility(View.VISIBLE);

            }

            //Type of Button (ICon,Button,none)
            mTypeOfButton(holder, strTypeOfButton, position, strViewType);

        } catch (Exception e) {
//            itemRemove.add(position);
//            holder.ll_grid_item.setVisibility(View.VISIBLE);
            holder.fl_main.setBackgroundColor(context.getColor(R.color.divider));
            holder.cv_grid_item.setVisibility(View.GONE);
            Log.d(TAG, "onBindViewHolder_CGRAE: " + e.toString());
        }
    }

    private void mTypeOfButton(MyViewHolder holder, String strTypeOfButton, int position, String strViewType) {
        Log.d(TAG, "MyViewHolderGetTypeOfButtonCheck: " + typeOfButtonModel.get(position).getTypeOfButton());
        if (typeOfButtonModel != null && typeOfButtonModel.size() > 0) {

            if (strViewType.equalsIgnoreCase("Grid")) {
                if (iTypeOfBorder == 2) {
                    LinearLayout.LayoutParams ll_parentItem = (LinearLayout.LayoutParams) holder.ll_grid_item.getLayoutParams();
                    ll_parentItem.setMargins(5, 5, 5, 5);
                    holder.ll_grid_item.setLayoutParams(ll_parentItem);
                    removeCardViewAttributes(strViewType, holder, iTypeOfBorder, false);
                } else if (iTypeOfBorder == 3) {
                    LinearLayout.LayoutParams ll_parentItem = (LinearLayout.LayoutParams) holder.ll_grid_item.getLayoutParams();
                    ll_parentItem.setMargins(5, 5, 5, 5);
                    holder.ll_grid_item.setLayoutParams(ll_parentItem);
                    removeCardViewAttributes(strViewType, holder, iTypeOfBorder, false);
                }
                /*else if (iTypeOfBorder == 4) {
                    LinearLayout.LayoutParams ll_parentItem = (LinearLayout.LayoutParams) holder.ll_grid_item.getLayoutParams();
                    ll_parentItem.setMargins(5, 5, 5, 5);
                    holder.ll_grid_item.setLayoutParams(ll_parentItem);
                    removeCardViewAttributes(holder, iTypeOfBorder, true);
                }*//* else if (iTypeOfBorder == 0 || iTypeOfBorder == 1) {

                    removeCardViewAttributes(holder, iTypeOfBorder, true);

                    LinearLayout.LayoutParams ll_parentItem = (LinearLayout.LayoutParams) holder.ll_grid_item.getLayoutParams();
                    ll_parentItem.setMargins(5, 5, 5, 5);
                    holder.ll_grid_item.setLayoutParams(ll_parentItem);
                }*/

                if(Marginleft>0||Marginright>0||Margintop>0||Marginbottom>0){
                    System.out.println("Marginleft==="+Marginleft);
                    System.out.println("Marginright==="+Marginright);
                    System.out.println("Margintop==="+Margintop);
                    System.out.println("Marginbottom==="+Marginbottom);

                    LinearLayout.LayoutParams ll_parentItem = (LinearLayout.LayoutParams) holder.cv_grid_item.getLayoutParams();
                    ll_parentItem.setMargins(Marginleft, Margintop, Marginright, Marginbottom);
                    holder.cv_grid_item.setLayoutParams(ll_parentItem);
                }else{
                    LinearLayout.LayoutParams ll_parentItem = (LinearLayout.LayoutParams) holder.cv_grid_item.getLayoutParams();
                    ll_parentItem.setMargins(3, 3, 3, 3);
                    holder.cv_grid_item.setLayoutParams(ll_parentItem);
                    //                holder.cv_grid_item.setUseCompatPadding(false);
                }
            }

//            if (strTypeOfButton.equalsIgnoreCase("1")) { // Icon
            if (strTypeOfButton.equalsIgnoreCase("Icon")) { // Icon
                holder.ll_button.setVisibility(View.GONE);
                holder.ll_icon.setVisibility(View.VISIBLE);

                String strSDCardUrl = "/Improve_User/" + orgId + "/" + appName + "/" + "MenuIcons/";

                String[] strsplit = typeOfButtonModel.get(position).getIconPath().split("/");
                String strFileName = strsplit[strsplit.length - 1];
                getFromSdcard(holder.icon,position,strFileName.replaceAll(" ", "_"));

//                String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + replaceWithUnderscore + "/" + imgUrlSplit[imgUrlSplit.length - 1];
//                Log.d(TAG, "onCreateSDCardPathCheck: " + strSDCardUrl);
//                for (int i = 0; i < 2; i++) {

//                    setImageFromSDCard(strSDCardUrl+"icon_mobile.PNG",holder.icon);
//                    getFromSdcard(strSDCardUrl,holder.icon,position);

//                }
                /*if (typeOfButtonModel.get(position).getIconPath() != null) {
//get the path of the sdcard and enter all the files to an array file

                    Glide.with(context).load(typeOfButtonModel.get(position).getIconPath())
                            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .dontAnimate().into(holder.icon);
//                    holder.icon.setImageDrawable(context.getDrawable(R.drawable.clubs));
                }*/
                if (typeOfButtonModel.get(position).getButtonColor() != null) {
                    holder.ll_grid_item.setBackgroundColor(Color.parseColor(typeOfButtonModel.get(position).getButtonColor()));
//                    holder.ll_grid_item.setBackgroundColor(Color.parseColor("#ffffff"));
                    Log.d(TAG, "ItemButtonColor: " + typeOfButtonModel.get(position).getButtonColor());
                }
                if (typeOfButtonModel.get(position).getDisplayName() != null) {
                    holder.grid_item_name.setText(typeOfButtonModel.get(position).getDisplayName());
                }
                setDisplaySettings(context, holder.grid_item_name, controlObject);
//            } else if (strTypeOfButton.equalsIgnoreCase("2")) { // Button
            } else if (strTypeOfButton.equalsIgnoreCase("Button")) { // Button
                holder.ll_icon.setVisibility(View.GONE);
                holder.ll_button.setVisibility(View.VISIBLE);

                mListWithButton(holder, strTypeOfButton);
//
//                LinearLayout.LayoutParams ll_parentItem = (LinearLayout.LayoutParams) holder.ll_grid_item.getLayoutParams();
//                ll_parentItem.setMargins(5, 5, 5, 5);
//                holder.ll_grid_item.setLayoutParams(ll_parentItem);

//                removeCardViewAttributes(holder, iTypeOfBorder, true);

//                    holder.btnIcon.setBackgroundColor(Integer.parseInt(typeOfButtonModel.get(position).getButtonColor()));
                if (typeOfButtonModel.get(position).getDisplayName() != null) {
                    holder.btnIcon.setText(typeOfButtonModel.get(position).getDisplayName());
                }
                if (typeOfButtonModel.get(position).getButtonColor() != null) {
                    holder.btnIcon.setBackgroundColor(Color.parseColor(typeOfButtonModel.get(position).getButtonColor()));
                    Log.d(TAG, "GetButtonColor: " + typeOfButtonModel.get(position).getButtonColor());
                }
//                    setDisplaySettings(context, holder.grid_item_name, controlObject);
                setDisplaySettingsMenuButton(context, holder.btnIcon, typeOfButtonModel, position);
//            } else if (strTypeOfButton.equalsIgnoreCase("3")) { //None
            } else if (strTypeOfButton.equalsIgnoreCase("None")) { //None
                holder.ll_icon.setVisibility(View.GONE);
                holder.ll_button.setVisibility(View.VISIBLE);

                mListWithButton(holder, strTypeOfButton);
//                removeCardViewAttributes(holder, iTypeOfBorder, true);

                if (typeOfButtonModel.get(position).getDisplayName() != null) {
                    holder.btnIcon.setText(typeOfButtonModel.get(position).getDisplayName());
                }
                holder.btnIcon.setBackgroundResource(0);
                setDisplaySettingsMenuButton(context, holder.btnIcon, typeOfButtonModel, position);
            }
        }
    }

    @Override
    public int getItemCount() {
        int itemCount;
        if (noOfRows > 0) {
            itemCount = notOfColumns * noOfRows;
        } else {
            itemCount = typeOfButtonModel.size();
        }
        return itemCount;
    }

    public void removeCardViewAttributes(String strViewType, MyViewHolder myViewHolder, int iTypeOfBorder, boolean isOnlyButton) {

        if ((iTypeOfBorder == 0 || iTypeOfBorder == 1) && isOnlyButton) {
            myViewHolder.cv_grid_item.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));  // setting cardView width and height params
            myViewHolder.ll_cv_item.setLayoutParams(new CardView.LayoutParams(
                    CardView.LayoutParams.WRAP_CONTENT, CardView.LayoutParams.WRAP_CONTENT));
            myViewHolder.ll_grid_item.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            myViewHolder.ll_button.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            myViewHolder.fl_main.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            myViewHolder.cv_grid_item.setPadding(0, 0, 0, 0);
            myViewHolder.cv_grid_item.setUseCompatPadding(false);
            myViewHolder.cv_grid_item.setCardElevation(0);
            myViewHolder.cv_grid_item.setRadius(0);

            LinearLayout.LayoutParams ll_parentItem = (LinearLayout.LayoutParams) myViewHolder.cv_grid_item.getLayoutParams();
            ll_parentItem.setMargins(5, 5, 5, 5);
            myViewHolder.cv_grid_item.setLayoutParams(ll_parentItem);
        } else {
            myViewHolder.cv_grid_item.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));  // setting cardView width and height params
            LinearLayout.LayoutParams ll_parentItem = (LinearLayout.LayoutParams) myViewHolder.cv_grid_item.getLayoutParams();
            ll_parentItem.setMargins(4, 4, 4, 4);
            myViewHolder.cv_grid_item.setLayoutParams(ll_parentItem);

            myViewHolder.ll_cv_item.setLayoutParams(new CardView.LayoutParams(
                    CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.MATCH_PARENT));
            myViewHolder.ll_grid_item.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            myViewHolder.cv_grid_item.setPadding(0, 0, 0, 0);
            myViewHolder.cv_grid_item.setUseCompatPadding(false);
            myViewHolder.cv_grid_item.setCardElevation(0);
            myViewHolder.cv_grid_item.setRadius(0);
        }
//        myViewHolder.cv_grid_item.setCorner(0);
    }

    public void mListWithButton(MyViewHolder holder, String strViewType) {
        if (strViewType.equalsIgnoreCase("Grid")) {
            holder.cv_grid_item.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));  // setting cardView width and height params
            holder.ll_cv_item.setLayoutParams(new CardView.LayoutParams(
                    CardView.LayoutParams.WRAP_CONTENT, CardView.LayoutParams.WRAP_CONTENT));
            holder.ll_grid_item.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            holder.ll_button.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            holder.cv_grid_item.setPadding(0, 0, 0, 0);
            holder.cv_grid_item.setUseCompatPadding(false);
            holder.cv_grid_item.setCardElevation(0);
            holder.cv_grid_item.setRadius(0);

//                    LinearLayout.LayoutParams ll_parentItem = (LinearLayout.LayoutParams) holder.ll_grid_item.getLayoutParams();
//                    ll_parentItem.setMargins(5, 5, 5, 5);
//                    holder.ll_grid_item.setLayoutParams(ll_parentItem);
            LinearLayout.LayoutParams ll_parentItem = (LinearLayout.LayoutParams) holder.cv_grid_item.getLayoutParams();
            ll_parentItem.setMargins(5, 5, 5, 5);
            holder.cv_grid_item.setLayoutParams(ll_parentItem);

        } else {
            //                    holder.fl_main.setLayoutParams(new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            holder.cv_grid_item.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));  // setting cardView width and height params
            holder.ll_cv_item.setLayoutParams(new CardView.LayoutParams(
                    CardView.LayoutParams.WRAP_CONTENT, CardView.LayoutParams.WRAP_CONTENT));
            holder.ll_grid_item.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            holder.ll_button.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            holder.cv_grid_item.setPadding(0, 0, 0, 0);
            holder.cv_grid_item.setUseCompatPadding(false);
            holder.cv_grid_item.setCardElevation(0);
            holder.cv_grid_item.setRadius(0);

            LinearLayout.LayoutParams ll_parentItem = (LinearLayout.LayoutParams) holder.cv_grid_item.getLayoutParams();
            ll_parentItem.setMargins(5, 5, 5, 5);
            holder.cv_grid_item.setLayoutParams(ll_parentItem);
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout ll_grid_item, ll_icon, ll_button;
        LinearLayout fl_main;
        //        CardView fl_main;
        ImageView icon;
        CustomTextView grid_item_name;
        CustomButton btnIcon;

        /*Default CardView */
        CardView cv_grid_item;
        LinearLayout ll_cv_item;
        /*Default CardView */

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            fl_main = itemView.findViewById(R.id.fl_main);
            ll_icon = itemView.findViewById(R.id.ll_icon);
            icon = itemView.findViewById(R.id.icon);
            grid_item_name = itemView.findViewById(R.id.grid_item_name);

            ll_button = itemView.findViewById(R.id.ll_button);
            btnIcon = itemView.findViewById(R.id.btnIcon);

            cv_grid_item = itemView.findViewById(R.id.cv_grid_item);
            ll_cv_item = itemView.findViewById(R.id.ll_cv_item);

            itemView.setOnClickListener(this);

            ll_grid_item = itemView.findViewById(R.id.ll_grid_item);
            ll_grid_item.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (getAdapterPosition() < menuListItemsSize) {

                ImproveHelper.showToastAlert(context, typeOfButtonModel.get(getAdapterPosition()).getDisplayName());

                if (typeOfButtonModel.get(getAdapterPosition()).isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.GlobalObjects.setCurrent_GPS("");
                        ((MainActivity) context).MenuClickEvent(typeOfButtonModel.get(getAdapterPosition()), v, controlObject.getControlName());
                    }
                }
            }

        }
    }

//    public void setImageFromSDCard(String strImagePath,ImageView icon) {
//
//        File imgFile = new File(Environment.getExternalStorageDirectory(), strImagePath);
//        Log.d(TAG, "setImageFromSDCard: " + imgFile);
//        if (imgFile.exists()) {
//
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//
//            icon.setImageBitmap(myBitmap);
//
//        }
//
//
//
//    }

//    public void setImageFromSDCard(String appName, String filename) {
//
////        File imgFile = new File(Environment.getExternalStorageDirectory(), strImagePath);
////        File imgFile = new File(Environment.getExternalStorageDirectory() + strImagePath);
//        String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "MenuIcons/";
//        File cDir = context.getExternalFilesDir(strSDCardUrl);
//        File appSpecificExternalDir = new File(cDir.getAbsolutePath(), filename);
//        Log.d(TAG, "FileExitsAdapter: " + appSpecificExternalDir);
//        if (appSpecificExternalDir.exists()) {
//
//            Bitmap myBitmap = BitmapFactory.decodeFile(appSpecificExternalDir.getAbsolutePath());
//
//            iv_circle.setImageBitmap(myBitmap);
//
//        }
//    }


    public void getFromSdcard(ImageView icon, int position,String filename) {

        String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "MenuIcons/";
//        File file = context.getExternalFilesDir(strSDCardUrl);
//        File file = new File(cDir.getAbsolutePath(), filename);
        File cDir = context.getExternalFilesDir(strSDCardUrl);
        File file = new File(cDir.getAbsolutePath(), filename);
        if (cDir.isDirectory())
        {
//            System.out.println("Icon isDirectory is === true");
//            System.out.println("Icon isDirectory is === true"+file.exists());
//            listFile = file.listFiles();
//
//
//            for (int i = 0; i < listFile.length; i++)
//            {
//
//                f.add(listFile[i].getAbsolutePath());
//
//            }
//            Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position));
//            icon.setImageBitmap(myBitmap);
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            icon.setImageBitmap(myBitmap);
        }
    }
//    public void getFromSdcard(String strImagePath,ImageView icon,int position)
//    {
//        File file= new File(android.os.Environment.getExternalStorageDirectory(),strImagePath);
//
//        if (file.isDirectory())
//        {
//            System.out.println("Icon isDirectory is === true");
//            System.out.println("Icon isDirectory is === true"+file.exists());
//            listFile = file.listFiles();
//
//
//            for (int i = 0; i < listFile.length; i++)
//            {
//
//                f.add(listFile[i].getAbsolutePath());
//
//            }
//            Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position));
//            icon.setImageBitmap(myBitmap);
//        }
//    }

}
