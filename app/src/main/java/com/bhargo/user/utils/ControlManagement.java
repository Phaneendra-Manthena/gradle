package com.bhargo.user.utils;

import static com.bhargo.user.utils.ImproveHelper.setEnable;

import com.bhargo.user.Java_Beans.ControlObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.Context;

public class ControlManagement {

    private static final String TAG = "ControlManagement";
    List<ControlObject> controlObjectList = new ArrayList<>();
    LinkedHashMap<String, Object> List_ControlClassObjects = new LinkedHashMap<String, Object>();
    Context context;
    ImproveHelper improveHelper;
    List<String> controlNames;
    int type = 0;

    public ControlManagement(Context context, List<ControlObject> controlObjectList,
                             LinkedHashMap<String, Object> List_ControlClassObjects,
                             List<String> controlNames, int type) {
        this.controlObjectList = controlObjectList;
        this.List_ControlClassObjects = List_ControlClassObjects;
        this.controlNames = controlNames;
        this.type = type;
        improveHelper = new ImproveHelper(context);
        setDisableOrEnable();
    }

    private void setDisableOrEnable() {
        switch (type) {
            case 1:
                disableOrEnableControls(controlNames);
                break;
            case 2:
                disableControls(controlNames);
                break;
            case 3:
                enableControls(controlNames);
                break;
        }
    }

    public void disableOrEnableControls(List<String> enableControlNames) {
        try {
            for (int i = 0; i < controlObjectList.size(); i++) {
                ControlObject temp_controlObj = controlObjectList.get(i);
                if (temp_controlObj.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SECTION) && temp_controlObj.isMakeItAsPopup()) {
                    List<ControlObject> sectionControlObjectList = temp_controlObj.getSubFormControlList();
                    for (int j = 0; j < sectionControlObjectList.size(); j++) {
                        ControlObject section_controlObj = sectionControlObjectList.get(j);
                        if (enableControlNames.contains(section_controlObj.getControlName())) {
                            setEnable(true, section_controlObj, List_ControlClassObjects);
                        } else if (!improveHelper.alwaysEnable(section_controlObj.getControlType())) {
                            setEnable(false, section_controlObj, List_ControlClassObjects);
                        }
                    }
                } else {
                    if (enableControlNames.contains(temp_controlObj.getControlName())) {
                        setEnable(true, temp_controlObj, List_ControlClassObjects);
                    } else if (!improveHelper.alwaysEnable(temp_controlObj.getControlType())) {
                        setEnable(false, temp_controlObj, List_ControlClassObjects);
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "SetDisable", e);
        }
    }

    public void enableControls(List<String> enableControlNames) {
        try {
            for (int i = 0; i < controlObjectList.size(); i++) {
                ControlObject temp_controlObj = controlObjectList.get(i);
                if (temp_controlObj.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SECTION) && temp_controlObj.isMakeItAsPopup()) {
                    List<ControlObject> sectionControlObjectList = temp_controlObj.getSubFormControlList();
                    for (int j = 0; j < sectionControlObjectList.size(); j++) {
                        ControlObject section_controlObj = sectionControlObjectList.get(j);
                        if (enableControlNames.contains(section_controlObj.getControlName())) {
                            setEnable(true, section_controlObj, List_ControlClassObjects);
                        }
                    }
                } else {
                if (enableControlNames.contains(temp_controlObj.getControlName())) {
                    setEnable(true, temp_controlObj, List_ControlClassObjects);
                }}
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "SetDisable", e);
        }
    }

    public void disableControls(List<String> disableControlNames) {
        try {
            for (int i = 0; i < controlObjectList.size(); i++) {
                ControlObject temp_controlObj = controlObjectList.get(i);
                if (temp_controlObj.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SECTION)&& temp_controlObj.isMakeItAsPopup()) {
                    List<ControlObject> sectionControlObjectList = temp_controlObj.getSubFormControlList();
                    for (int j = 0; j < sectionControlObjectList.size(); j++) {
                        ControlObject section_controlObj = sectionControlObjectList.get(j);
                        if (disableControlNames.contains(section_controlObj.getControlName())) {
                            setEnable(false, section_controlObj, List_ControlClassObjects);
                        }
                    }
                }else{
                if (disableControlNames.contains(temp_controlObj.getControlName()) && !improveHelper.alwaysEnable(temp_controlObj.getControlType())) {
                    setEnable(false, temp_controlObj, List_ControlClassObjects);
                }}
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "SetDisable", e);
        }
    }


}
