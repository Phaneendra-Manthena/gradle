package com.bhargo.user.controls.advanced;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;

import java.util.LinkedHashMap;

public class GridControlView {

    private static final String TAG = "GridControlView";

    private Context context;
    public ControlObject controlObject;
    private String strUserLocationStructure;
    private LinkedHashMap<String, Object> list_ControlClassObjects;
    private LinearLayout linearLayout;

    public GridControlView(Context context, ControlObject controlObject, String strUserLocationStructure
            , LinkedHashMap<String, Object> list_ControlClassObjects) {

        this.context = context;
        this.controlObject = controlObject;
        this.strUserLocationStructure = strUserLocationStructure;
        this.list_ControlClassObjects = list_ControlClassObjects;
    }

    private void initView(){
        linearLayout = new LinearLayout(context);
        linearLayout.setTag(controlObject.getControlName());
    }

    private void addGrid(){
        final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = lInflater.inflate(R.layout.control_subform, null);
        final LinearLayout ll_MainSubFormContainer = view.findViewById(R.id.ll_MainSubFormContainer);

    }
}
