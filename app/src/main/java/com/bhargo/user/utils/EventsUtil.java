package com.bhargo.user.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class EventsUtil {

    private final Context context;
    private String TAG;

    public EventsUtil(Context context) {

        this.context = context;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public LinkedHashMap<String, List<String>> convert_JSON(String serviceResponse, String[] OutParam_Names) {
        LinkedHashMap<String, List<String>> OutputData = new LinkedHashMap<>();
        try {
            JSONObject jMainObj = new JSONObject(serviceResponse);
            if (jMainObj.getString("Status").equalsIgnoreCase("200")) {
                JSONArray jArr = jMainObj.getJSONArray("FormData");
                for (int i = 0; i < jArr.length(); i++) {
                    JSONObject jObj = jArr.getJSONObject(i);
                    for (String outParam_name : OutParam_Names) {

                        String jObjValue = "";
                        if (!jObj.isNull(outParam_name)) {
                            jObjValue = jObj.getString(outParam_name);
                        }
                        if (OutputData.containsKey(outParam_name.toLowerCase())) {
                            List<String> value = OutputData.get(outParam_name.toLowerCase());
                            if (value != null) {
                                value.add(jObjValue);
                            }
                            OutputData.put(outParam_name.toLowerCase(), value);
                        } else {
                            List<String> value = new ArrayList<>();
                            value.add(jObjValue);
                            OutputData.put(outParam_name.toLowerCase(), value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "Convert_JSON", e);
        }

        return OutputData;
    }




}
