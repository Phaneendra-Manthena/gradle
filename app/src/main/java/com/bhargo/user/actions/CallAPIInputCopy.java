/*
package com.bhargo.user.actions;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHECKBOX;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHECK_LIST;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DATA_CONTROL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DROP_DOWN;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_RADIO_BUTTON;
import static com.bhargo.user.utils.AppConstants.Global_API;

import android.content.Context;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.API_InputParam_Bean;
import com.bhargo.user.Java_Beans.ActionWithoutCondition_Bean;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.GetAPIDetails_Bean;
import com.bhargo.user.MainActivity;
import com.bhargo.user.controls.advanced.GridControl;
import com.bhargo.user.controls.advanced.SubformView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CallAPIInputCopy {
    String TAG = "CallAPIInput";
    ActionWithoutCondition_Bean actionBean;
    GetAPIDetails_Bean getAPIDetails_bean;
    Context context;
    HashMap<String, Object> l_dataSources = new HashMap<>();
    HashMap<String, Object> l_filters = new HashMap<>();
    HashMap<String, Object> l_keyType = new HashMap<>();
    ExpressionMainHelper ehelper;

    public CallAPIInputCopy(Context context, ActionWithoutCondition_Bean actionBean, GetAPIDetails_Bean getAPIDetails_bean) {
        this.context = context;
        this.actionBean = actionBean;
        this.getAPIDetails_bean = getAPIDetails_bean;
        ehelper = new ExpressionMainHelper();
    }

    public static String getColNameFromExpression(String expression) {
        String controlname = expression;
        if (controlname.toLowerCase().endsWith("_coordinates_allrows")) {
            controlname = controlname.substring(0, controlname.lastIndexOf("_"));
            controlname = controlname.substring(0, controlname.lastIndexOf("_"));
        } else if (controlname.toLowerCase().endsWith("_allrows")) {
            controlname = controlname.substring(0, controlname.lastIndexOf("_"));
        } else if (controlname.toLowerCase().endsWith("_processrow")) {
            controlname = controlname.substring(0, controlname.lastIndexOf("_"));
        } else if (controlname.toLowerCase().endsWith("_checkedrows")) {
            controlname = controlname.substring(0, controlname.lastIndexOf("_"));
        } else if (controlname.toLowerCase().endsWith("_uncheckedrows")) {
            controlname = controlname.substring(0, controlname.lastIndexOf("_"));
        } else if (controlname.toLowerCase().endsWith("_allcolumns")) {
            controlname = controlname.substring(0, controlname.lastIndexOf("_"));
        }
        return controlname;
    }

    private LinkedHashMap<String, List<String>> getDataSourceData(String dataSource) {
        LinkedHashMap<String, List<String>> map_Data = new LinkedHashMap<>();
        String valueType = dataSource.substring(4, dataSource.indexOf("."));
        String sourceName = dataSource.substring(4, dataSource.indexOf(")"));
        if (valueType.equalsIgnoreCase("Variables")) {
            sourceName = sourceName.split("\\.")[2].trim().toLowerCase();
        } else {
            sourceName = sourceName.split("\\.")[1].trim().toLowerCase();
        }
        if (valueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
            List<ControlObject> list_Controls = new ArrayList<>();
            if (MainActivity.getInstance().List_ControlClassObjects.get(sourceName) instanceof GridControl) {
                GridControl gridControl = (GridControl) MainActivity.getInstance().List_ControlClassObjects.get(sourceName);
                if (gridControl != null) {
                    list_Controls = gridControl.controlObject.getSubFormControlList();
                }
            } else {
                SubformView subview = (SubformView) MainActivity.getInstance().List_ControlClassObjects.get(sourceName);
                if (subview != null) {
                    list_Controls = subview.controlObject.getSubFormControlList();
                }
            }
            for (int i = 0; i < list_Controls.size(); i++) {
                ControlObject temp_controlObj = list_Controls.get(i);
                if (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_RADIO_BUTTON)
                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DROP_DOWN)
                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECKBOX)
                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECK_LIST)
                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DATA_CONTROL)) {
                    List<String> Valuestr = ImproveHelper.getListOfValuesFromGlobalObject(context, dataSource.substring(0, dataSource.indexOf(")")) + "." + temp_controlObj.getControlName() + "_id_allrows)");
                    map_Data.put(temp_controlObj.getControlName() + "_id", Valuestr);
                }
                List<String> Valuestr = ImproveHelper.getListOfValuesFromGlobalObject(context, dataSource.substring(0, dataSource.indexOf(")")) + "." + temp_controlObj.getControlName() + "_allrows)");
                map_Data.put(temp_controlObj.getControlName(), Valuestr);
            }
        } else if (valueType.equalsIgnoreCase(Global_API)) {
            //nk pending
        } else {
            //map_Data = RealmDBHelper.getTableDataInLHM(context, sourceName);
            //nk pending
        }
        return map_Data;
    }

    private void getAllDataSources_Filters_ParentType(List<API_InputParam_Bean> list_input) {
        l_dataSources.clear();
        l_filters.clear();
        l_keyType.clear();
        for (int i = 0; i < list_input.size(); i++) {
            API_InputParam_Bean inputParamBeam = list_input.get(i);
            String dataSourceName = inputParamBeam.get_inParam_DataSourceName();
            String apiInParam = inputParamBeam.getInParam_Name();//means:Current Key Name
            String inparamType = inputParamBeam.get_InParam_Type();//JArray,Array,Value
            boolean isFiltersAvailable = inputParamBeam.is_inParam_isFiltersAvailable();
            List<API_InputParam_Bean> filters = inputParamBeam.get_FilterParams();
            l_keyType.put(apiInParam, inparamType);//in xml i need know whether key will be parent or no ???
            if (dataSourceName != null && !dataSourceName.trim().isEmpty()) {
                l_dataSources.put(apiInParam, getDataSourceData(dataSourceName));
                if (isFiltersAvailable) {
                    l_filters.put(apiInParam, filters);
                }
            }
        }
    }

    public HashMap<String, Object> getInputHashMapObj(List<API_InputParam_Bean> list_input, String SuccessCaseDetails) {
        HashMap<String, Object> inputobj = new HashMap<>();
        HashMap<String, Object> filtersDS = new HashMap<>();
        try {
            getAllDataSources_Filters_ParentType(list_input);
            JSONObject jobj = new JSONObject(SuccessCaseDetails);
            JSONObject inputObject = jobj.getJSONObject("InputParameters");
            JSONObject BodyObject = inputObject.getJSONObject("Body");
            // Convert JSON object to JSON schema representation
            String jsonString = String.valueOf(BodyObject.getJSONObject("postInputRawTypeContent"));
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonSchema = JsonBuilder.generateJsonSchema(jsonObject);
            String jsonSchemaString = jsonSchema.toString(4);
            JSONArray jArrayKeys = BodyObject.getJSONArray("postInputRawTypeContentKeys");
            //Convert json Obj to Sample HashMap
            HashMap<String, Object> sampleHashMapObj = JsonBuilder.ConvertJSONToHashMap(jsonString);
            for (int i = 0; i < list_input.size(); i++) {
                API_InputParam_Bean inputParamBeam = list_input.get(i);
                boolean isParentAvailable = inputParamBeam.is_inParam_isParentAvailable();
                boolean isFiltersAvailable = inputParamBeam.is_inParam_isFiltersAvailable();
                String ParentName = inputParamBeam.get_inParam_ParentName();
                String DataSourceName = inputParamBeam.get_inParam_DataSourceName();
                String inparamType = inputParamBeam.get_InParam_Type();//JArray,Array,Value
                String static_ = inputParamBeam.getInParam_Static();
                //static_ empty= DataSourceName Exist and inputParameter is no
                //static_ no= DataSourceName "" and inputParameter is expression or Value
                boolean expressionFlag = inputParamBeam.isInParam_ExpressionExists();
                String expressionOrValue = inputParamBeam.getInParam_Mapped_ID();
                String apiInParam = inputParamBeam.getInParam_Name();//means:Current Key Name
                List<API_InputParam_Bean> filters = inputParamBeam.get_FilterParams();
                String keyPath = getKeyPath(apiInParam, jArrayKeys);
                if (!isParentAvailable) {
                    //No Parent
                    setKeyWithorWithoutValueInObj(inputobj, inparamType, apiInParam, expressionOrValue);
                } else {
                    //Parent exist
                    if (inparamType.equalsIgnoreCase("Value")) {
                        // Value data set to Obj
                    } else if (inparamType.equalsIgnoreCase("JObject")) {
                        //initialization key in Obj without value For JArray and JObject i think
                    } else if (inparamType.equalsIgnoreCase("Array")) {
                        // Array data set to Obj
                    } else if (inparamType.equalsIgnoreCase("JArray")) {
                        //initialization key in Obj without value For JArray and JObject i think
                    }
                    String[] parentKeys = keyPath.split("/");//skip last index
                    if (parentKeys.length - 1 == 1) {
                        //1 Level : States/StateID:Filter pending
                        levelOne(inputobj, parentKeys, static_, expressionOrValue, apiInParam, inparamType,filtersDS);
                    } else if (parentKeys.length - 1 == 2) {
                        //2 Level :States/Districts/DistrictID:Filter pending
                        levelTwo(inputobj, parentKeys, static_, expressionOrValue, apiInParam, inparamType);
                    } else if (parentKeys.length - 1 == 3) {
                        //3 Level :States/Districts/Mandals/MandalID:pending:Filter pending
                        levelThree(inputobj, parentKeys, static_, expressionOrValue, apiInParam, inparamType);
                    } else if (parentKeys.length - 1 == 4) {
                        //4 Level :States/Districts/Mandals/Schools/SchoolID:pending:Filter pending
                        levelFour(inputobj, parentKeys, static_, expressionOrValue, apiInParam, inparamType);
                    } else if (parentKeys.length - 1 == 5) {
                        //5 Level Case 1:States/Districts/Mandals/Schools/Classes(JArray)/ClassID:pending:Filter pending
                        //Case 2:States/Districts/Mandals/Schools/Address(JObject)/DoorNo:pending:Filter pending
                        levelFive(inputobj, parentKeys, static_, expressionOrValue, apiInParam, inparamType);
                    } else if (parentKeys.length - 1 == 6) {
                        //6 Level :States/Districts/Mandals/Schools/Classes/Section/SectionID:pending:Filter pending
                        levelSix(inputobj, parentKeys, static_, expressionOrValue, apiInParam, inparamType);
                    }
                }
            }
            //build jsonObj with HashMap and Jsonschema
            String updateJObjData = JsonBuilder.buildJsonFromHashMap(inputobj, jsonSchemaString);
            JSONObject updateJObj = new JSONObject(updateJObjData);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getPostObject", e);
            return new HashMap<>();
        }
        return inputobj;
    }

    private void setKeyWithorWithoutValueInObj(HashMap<String, Object> inputobj,
                                               String inparamType, String apiInParam, String expressionOrValue) {
        String eVal = ehelper.ExpressionHelper(context, expressionOrValue);
        if (inparamType.equalsIgnoreCase("Value")) {
            inputobj.put(apiInParam, eVal); // Value data set to Obj
        } else if (inparamType.equalsIgnoreCase("JObject")) {
            HashMap<String, Object> hashMapList = new HashMap<>();
            inputobj.put(apiInParam, hashMapList); //initialization key in Obj without value For JArray and JObject i think
        } else if (inparamType.equalsIgnoreCase("Array")) {
            List<String> l_ObjValue = new ArrayList<>();
            if (eVal.contains("^")) {
                l_ObjValue = Arrays.asList(eVal.split("\\^"));
            } else if (eVal.contains(",")) {
                l_ObjValue = Arrays.asList(eVal.split("\\,"));
            } else {
                l_ObjValue.add(eVal);
            }
            inputobj.put(apiInParam, l_ObjValue); // Array data set to Obj
        } else if (inparamType.equalsIgnoreCase("JArray")) {
            List<HashMap<String, Object>> hashMapList = new ArrayList<>();
            inputobj.put(apiInParam, hashMapList); //initialization key in Obj without value For JArray and JObject i think
        }
    }

    //Without Filter
    private void levelOneCopy(HashMap<String, Object> inputobj, String[] parentKeys, String static_,
                              String expressionOrValue, String apiInParam, String inparamType) {
        //apiInParam:currentkey,inparamType:currentType(Value,Array,JArray,JObject)
        if (static_.isEmpty() && expressionOrValue.isEmpty()) {//initialization key in Obj without value
            if (l_keyType.get(parentKeys[0]).toString().equalsIgnoreCase("JArray")) {
                List<HashMap<String, Object>> parentData = (List<HashMap<String, Object>>) inputobj.get(parentKeys[0]);
                for (int j = 0; j < parentData.size(); j++) {
                    HashMap<String, Object> hashMap = parentData.get(j);
                    //initialization key in Obj with or without value
                    setKeyWithorWithoutValueInObj(hashMap, inparamType, apiInParam, expressionOrValue);
                    parentData.set(j, hashMap);
                }
                inputobj.put(parentKeys[0], parentData);
            }
        } else {
            expressionOrValue = getColNameFromExpression(expressionOrValue);
            if (l_dataSources.containsKey(parentKeys[0])) {//datasource exist
                LinkedHashMap<String, List<String>> pDataSource = (LinkedHashMap<String, List<String>>) l_dataSources.get(parentKeys[0]);
                if (l_keyType.get(parentKeys[0]).toString().equalsIgnoreCase("JArray")) {
                    List<HashMap<String, Object>> parentData = (List<HashMap<String, Object>>) inputobj.get(parentKeys[0]);
                    int dataSourceCount = pDataSource.keySet().size() == 0 ? 0 : pDataSource.get(pDataSource.keySet().iterator().next()).size();
                    for (int j = 0; j < dataSourceCount; j++) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        if (parentData.size() > j) {
                            hashMap = parentData.get(j);
                        }
                        String objValue = "";
                        if (pDataSource.containsKey(expressionOrValue)) {
                            objValue = pDataSource.get(expressionOrValue).get(j);
                        } else {
                            objValue = ImproveHelper.getValueFromGlobalObject(context, expressionOrValue);
                        }
                        if (inparamType.equalsIgnoreCase("Value")) {
                            hashMap.put(apiInParam, objValue);
                        } else if (inparamType.equalsIgnoreCase("Array")) {
                            List<String> hashMapList = new ArrayList<>();
                            if (objValue.contains("^")) {
                                hashMapList = Arrays.asList(objValue.split("\\^"));
                            } else {
                                hashMapList.add(objValue);
                            }
                            hashMap.put(apiInParam, hashMapList);
                        }
                        if (parentData.size() > j) {
                            parentData.set(j, hashMap);
                        } else {
                            parentData.add(hashMap);
                        }
                    }
                    inputobj.put(parentKeys[0], parentData);
                } else {//JObject
                    HashMap<String, Object> parentData = (HashMap<String, Object>) inputobj.get(parentKeys[0]);
                }
            } else {//no datasource
                if (expressionOrValue.startsWith("(im:")) { //no datasource
                    //pending
                    List<String> inValue = ImproveHelper.getListOfValuesFromGlobalObject(context, expressionOrValue.trim());
                    if (l_keyType.get(parentKeys[0]).toString().equalsIgnoreCase("JArray")) {
                        List<HashMap<String, Object>> parentData = (List<HashMap<String, Object>>) inputobj.get(parentKeys[0]);
                        for (int j = 0; j < parentData.size(); j++) {
                            HashMap<String, Object> hashMap = parentData.get(j);
                            if (inparamType.equalsIgnoreCase("Array")) {
                                hashMap.put(apiInParam, inValue);
                            }
                            parentData.set(j, hashMap);
                        }
                        inputobj.put(parentKeys[0], parentData);
                    }
                }
            }
        }
    }

    private void setLastLevel_jArray(HashMap<String, Object> inputobj, HashMap<String, Integer> level_keypos, String parentKeyLastLevel,
                                     String expressionOrValue, String apiInParam, String inparamType,HashMap<String, Object> filtersDS) {
        expressionOrValue = getColNameFromExpression(expressionOrValue);
        List<HashMap<String, Object>> parentData = (List<HashMap<String, Object>>) inputobj.get(parentKeyLastLevel);
        if (l_dataSources.containsKey(parentKeyLastLevel)) {//datasource exist
            LinkedHashMap<String, List<String>> pDataSource = (LinkedHashMap<String, List<String>>) l_dataSources.get(parentKeyLastLevel);
            if (l_filters.containsKey(parentKeyLastLevel) && pDataSource.size() > 0) {//filter exist then update data in datasource
                List<API_InputParam_Bean> filters = (List<API_InputParam_Bean>) l_filters.get(parentKeyLastLevel);
                pDataSource = getFilterData(pDataSource, filters, inputobj, level_keypos, parentKeyLastLevel);//nk pending
            }
            int dataSourceCount = pDataSource.keySet().size() == 0 ? 0 : pDataSource.get(pDataSource.keySet().iterator().next()).size();
            for (int j = 0; j < dataSourceCount; j++) {
                HashMap<String, Object> hashMap = new HashMap<>();
                if (parentData.size() > j) {
                    hashMap = parentData.get(j);
                }
                String objValue = "";
                if (pDataSource.containsKey(expressionOrValue)) {
                    objValue = pDataSource.get(expressionOrValue).get(j);
                    setKeyWithorWithoutValueInObj(hashMap, inparamType, apiInParam, objValue);
                } else {
                    setKeyWithorWithoutValueInObj(hashMap, inparamType, apiInParam, expressionOrValue);
                }
                if (parentData.size() > j) {
                    parentData.set(j, hashMap);
                } else {
                    parentData.add(hashMap);
                }
            }
        } else {//no datasource
            if (parentData.size() == 0) {
                HashMap<String, Object> hashMap = new HashMap<>();
                setKeyWithorWithoutValueInObj(hashMap, inparamType, apiInParam, expressionOrValue);
                parentData.add(hashMap);
            } else {
                for (int j = 0; j < parentData.size(); j++) {
                    HashMap<String, Object> hashMap = parentData.get(j);
                    //initialization key in Obj with or without value
                    setKeyWithorWithoutValueInObj(hashMap, inparamType, apiInParam, expressionOrValue);
                    parentData.set(j, hashMap);
                }
            }
        }
        inputobj.put(parentKeyLastLevel, parentData);
    }

    private void setLastLevel_jObject(HashMap<String, Object> inputobj, HashMap<String, Integer> level_keypos, String parentKeyLastLevel,
                                      String expressionOrValue, String apiInParam, String inparamType,HashMap<String, Object> filtersDS) {
        expressionOrValue = getColNameFromExpression(expressionOrValue);
        HashMap<String, Object> parentDataJObj = (HashMap<String, Object>) inputobj.get(parentKeyLastLevel);
        if (l_dataSources.containsKey(parentKeyLastLevel)) {//datasource exist
            LinkedHashMap<String, List<String>> pDataSource = (LinkedHashMap<String, List<String>>) l_dataSources.get(parentKeyLastLevel);
            if (l_filters.containsKey(parentKeyLastLevel) && pDataSource.size() > 0) {//filter exist then update data in datasource
                List<API_InputParam_Bean> filters = (List<API_InputParam_Bean>) l_filters.get(parentKeyLastLevel);
                pDataSource = getFilterData(pDataSource, filters, inputobj, level_keypos, parentKeyLastLevel);//nk pending
            }
            int dataSourceCount = pDataSource.keySet().size() == 0 ? 0 : pDataSource.get(pDataSource.keySet().iterator().next()).size();
            List<HashMap<String, Object>> parentData = new ArrayList<>();
            for (int j = 0; j < dataSourceCount; j++) {
                HashMap<String, Object> hashMap = new HashMap<>();
                if (parentData.size() > j) {
                    hashMap = parentData.get(j);
                }
                String objValue = "";
                if (pDataSource.containsKey(expressionOrValue)) {
                    objValue = pDataSource.get(expressionOrValue).get(j);
                    setKeyWithorWithoutValueInObj(hashMap, inparamType, apiInParam, objValue);
                } else {
                    setKeyWithorWithoutValueInObj(hashMap, inparamType, apiInParam, expressionOrValue);
                }
                if (parentData.size() > j) {
                    parentData.set(j, hashMap);
                } else {
                    parentData.add(hashMap);
                }
            }
        } else {//no datasource
            setKeyWithorWithoutValueInObj(parentDataJObj, inparamType, apiInParam, expressionOrValue);
           */
/* if (parentData.size() == 0) {
                HashMap<String, Object> hashMap = new HashMap<>();
                setKeyWithorWithoutValueInObj(hashMap, inparamType, apiInParam, expressionOrValue);
                parentData.add(hashMap);
            } else {
                for (int j = 0; j < parentData.size(); j++) {
                    HashMap<String, Object> hashMap = parentData.get(j);
                    //initialization key in Obj with or without value
                    setKeyWithorWithoutValueInObj(hashMap, inparamType, apiInParam, expressionOrValue);
                    parentData.set(j, hashMap);
                }
            }*//*

        }
        inputobj.put(parentKeyLastLevel, parentDataJObj);
    }

    private HashMap<String, Integer> setLevelWiseKeysPositions(HashMap<String, Integer> level_keypos, HashMap<String, Object> parentData, int levelpos, String parentkey) {
        if (l_dataSources.containsKey(parentkey)) {
            LinkedHashMap<String, List<String>> parentDataDs = (LinkedHashMap<String, List<String>>) l_dataSources.get(parentkey);
            for (String key : parentDataDs.keySet()) {
                level_keypos.put(key.toLowerCase(), levelpos);
            }
        }
        return level_keypos;
    }

    //For Level 1 Done: Filter will not contain but datasource may or maynot
    private void levelOne(HashMap<String, Object> inputobj, String[] parentKeys, String static_,
                          String expressionOrValue, String apiInParam, String inparamType,HashMap<String, Object> filtersDS) {
        //apiInParam:currentkey,inparamType:currentType(Value,Array,JArray,JObject)
        HashMap<String, Integer> level_keypos = new HashMap<>();
        if (l_keyType.get(parentKeys[0]).toString().equalsIgnoreCase("JArray")) {
            setLastLevel_jArray(inputobj, level_keypos, parentKeys[0], expressionOrValue, apiInParam, inparamType,filtersDS);
        } else {//JObject
            setLastLevel_jObject(inputobj, level_keypos, parentKeys[0], expressionOrValue, apiInParam, inparamType,filtersDS);
        }
    }

    //With Filter Pending
    private void levelTwo(HashMap<String, Object> inputobj, String[] parentKeys, String static_,
                          String expressionOrValue, String apiInParam, String inparamType) {
        //apiInParam:currentkey,inparamType:currentType(Value,Array,JArray,JObject)
        HashMap<String, Integer> level_keypos = new HashMap<>();
        if (l_keyType.get(parentKeys[0]).toString().equalsIgnoreCase("JArray")) {
            List<HashMap<String, Object>> parentData_l1 = (List<HashMap<String, Object>>) inputobj.get(parentKeys[0]);
            for (int l1 = 0; l1 < parentData_l1.size(); l1++) {//top parent level 1 loop
                level_keypos = setLevelWiseKeysPositions(level_keypos, parentData_l1.get(l1), l1, parentKeys[0]);
                HashMap<String, Object> obj_l1 = parentData_l1.get(l1);
                if (l_keyType.get(parentKeys[1]).toString().equalsIgnoreCase("JArray")) {
                    setLastLevel_jArray(obj_l1, level_keypos, parentKeys[1], expressionOrValue, apiInParam, inparamType);
                } else {//JObject
                    setLastLevel_jObject(obj_l1, level_keypos, parentKeys[1], expressionOrValue, apiInParam, inparamType);
                }
            }
            inputobj.put(parentKeys[0], parentData_l1);
        } else {//JObject
            setLastLevel_jObject(inputobj, level_keypos, parentKeys[0], expressionOrValue, apiInParam, inparamType);
        }
    }

    //With Filter Pending
    private void levelThree(HashMap<String, Object> inputobj, String[] parentKeys, String static_,
                            String expressionOrValue, String apiInParam, String inparamType) {
        //apiInParam:currentkey,inparamType:currentType(Value,Array,JArray,JObject)
        //Level 1 Open
        HashMap<String, Integer> level_keypos = new HashMap<>();
        if (l_keyType.get(parentKeys[0]).toString().equalsIgnoreCase("JArray")) {
            List<HashMap<String, Object>> parentData_l1 = (List<HashMap<String, Object>>) inputobj.get(parentKeys[0]);
            for (int l1 = 0; l1 < parentData_l1.size(); l1++) {//top level 1 parent loop
                level_keypos = setLevelWiseKeysPositions(level_keypos, parentData_l1.get(l1), l1, parentKeys[0]);
                HashMap<String, Object> obj_l1 = parentData_l1.get(l1);
                //Level 2 Open
                if (l_keyType.get(parentKeys[1]).toString().equalsIgnoreCase("JArray")) {
                    List<HashMap<String, Object>> parentData_l2 = (List<HashMap<String, Object>>) obj_l1.get(parentKeys[1]);
                    for (int l2 = 0; l2 < parentData_l2.size(); l2++) {// level 2 parent loop
                        level_keypos = setLevelWiseKeysPositions(level_keypos, parentData_l2.get(l2), l2, parentKeys[1]);
                        HashMap<String, Object> obj_l2 = parentData_l2.get(l2);
                        //Level 3 Open
                        if (l_keyType.get(parentKeys[2]).toString().equalsIgnoreCase("JArray")) {
                            setLastLevel_jArray(obj_l2, level_keypos, parentKeys[2], expressionOrValue, apiInParam, inparamType);
                        } else {//JObject
                            setLastLevel_jObject(obj_l2, level_keypos, parentKeys[2], expressionOrValue, apiInParam, inparamType);
                        }
                        ////Level 3 Close
                        parentData_l2.set(l2, obj_l2);
                    }
                    obj_l1.put(parentKeys[1], parentData_l2);
                } else {//JObject
                    setLastLevel_jObject(obj_l1, level_keypos, parentKeys[1], expressionOrValue, apiInParam, inparamType);
                }
                //Level 2 Close
                parentData_l1.set(l1, obj_l1);
            }
            inputobj.put(parentKeys[0], parentData_l1);
        } else {//JObject
            setLastLevel_jObject(inputobj, level_keypos, parentKeys[0], expressionOrValue, apiInParam, inparamType);
        }
        //Level 1 Close


    }

    //With Filter Pending
    private void levelFour(HashMap<String, Object> inputobj, String[] parentKeys, String static_,
                           String expressionOrValue, String apiInParam, String inparamType) {
        //apiInParam:currentkey,inparamType:currentType(Value,Array,JArray,JObject)
        //Level 1 Open
        HashMap<String, Integer> level_keypos = new HashMap<>();
        if (l_keyType.get(parentKeys[0]).toString().equalsIgnoreCase("JArray")) {
            List<HashMap<String, Object>> parentData_l1 = (List<HashMap<String, Object>>) inputobj.get(parentKeys[0]);
            for (int l1 = 0; l1 < parentData_l1.size(); l1++) {//top level 1 parent loop
                level_keypos = setLevelWiseKeysPositions(level_keypos, parentData_l1.get(l1), l1, parentKeys[0]);
                HashMap<String, Object> obj_l1 = parentData_l1.get(l1);
                //Level 2 Open
                if (l_keyType.get(parentKeys[1]).toString().equalsIgnoreCase("JArray")) {
                    List<HashMap<String, Object>> parentData_l2 = (List<HashMap<String, Object>>) obj_l1.get(parentKeys[1]);
                    for (int l2 = 0; l2 < parentData_l2.size(); l2++) {// level 2 parent loop
                        level_keypos = setLevelWiseKeysPositions(level_keypos, parentData_l2.get(l2), l2, parentKeys[1]);
                        HashMap<String, Object> obj_l2 = parentData_l2.get(l2);
                        //Level 3 Open
                        if (l_keyType.get(parentKeys[2]).toString().equalsIgnoreCase("JArray")) {
                            List<HashMap<String, Object>> parentData_l3 = (List<HashMap<String, Object>>) obj_l2.get(parentKeys[2]);
                            for (int l3 = 0; l3 < parentData_l3.size(); l3++) {// level 3 parent loop
                                level_keypos = setLevelWiseKeysPositions(level_keypos, parentData_l3.get(l3), l3, parentKeys[2]);
                                HashMap<String, Object> obj_l3 = parentData_l3.get(l3);
                                //Level 4 Open
                                if (l_keyType.get(parentKeys[3]).toString().equalsIgnoreCase("JArray")) {
                                    setLastLevel_jArray(obj_l3, level_keypos, parentKeys[3], expressionOrValue, apiInParam, inparamType);
                                } else {//JObject
                                    setLastLevel_jObject(obj_l3, level_keypos, parentKeys[3], expressionOrValue, apiInParam, inparamType);
                                }
                                //Level 4 Close
                                parentData_l3.set(l3, obj_l3);
                            }
                            obj_l2.put(parentKeys[2], parentData_l3);
                        } else {//JObject
                            setLastLevel_jObject(obj_l2, level_keypos, parentKeys[2], expressionOrValue, apiInParam, inparamType);
                        }
                        ////Level 3 Close
                        parentData_l2.set(l2, obj_l2);
                    }
                    obj_l1.put(parentKeys[1], parentData_l2);
                } else {//JObject
                    setLastLevel_jObject(obj_l1, level_keypos, parentKeys[1], expressionOrValue, apiInParam, inparamType);
                }
                //Level 2 Close
                parentData_l1.set(l1, obj_l1);
            }
            inputobj.put(parentKeys[0], parentData_l1);
        } else {//JObject
            setLastLevel_jObject(inputobj, level_keypos, parentKeys[0], expressionOrValue, apiInParam, inparamType);
        }
        //Level 1 Close


    }

    //With Filter Pending
    private void levelFive(HashMap<String, Object> inputobj, String[] parentKeys, String static_,
                           String expressionOrValue, String apiInParam, String inparamType) {
        loopForAllLevels(inputobj, parentKeys);
        //apiInParam:currentkey,inparamType:currentType(Value,Array,JArray,JObject)
        //Level 1 Open
        HashMap<String, Integer> level_keypos = new HashMap<>();
        if (l_keyType.get(parentKeys[0]).toString().equalsIgnoreCase("JArray")) {
            List<HashMap<String, Object>> parentData_l1 = (List<HashMap<String, Object>>) inputobj.get(parentKeys[0]);
            for (int l1 = 0; l1 < parentData_l1.size(); l1++) {//top level 1 parent loop
                level_keypos = setLevelWiseKeysPositions(level_keypos, parentData_l1.get(l1), l1, parentKeys[0]);
                HashMap<String, Object> obj_l1 = parentData_l1.get(l1);
                //Level 2 Open
                if (l_keyType.get(parentKeys[1]).toString().equalsIgnoreCase("JArray")) {
                    List<HashMap<String, Object>> parentData_l2 = (List<HashMap<String, Object>>) obj_l1.get(parentKeys[1]);
                    for (int l2 = 0; l2 < parentData_l2.size(); l2++) {// level 2 parent loop
                        level_keypos = setLevelWiseKeysPositions(level_keypos, parentData_l2.get(l2), l2, parentKeys[1]);
                        HashMap<String, Object> obj_l2 = parentData_l2.get(l2);
                        //Level 3 Open
                        if (l_keyType.get(parentKeys[2]).toString().equalsIgnoreCase("JArray")) {
                            List<HashMap<String, Object>> parentData_l3 = (List<HashMap<String, Object>>) obj_l2.get(parentKeys[2]);
                            for (int l3 = 0; l3 < parentData_l3.size(); l3++) {// level 3 parent loop
                                level_keypos = setLevelWiseKeysPositions(level_keypos, parentData_l3.get(l3), l3, parentKeys[2]);
                                HashMap<String, Object> obj_l3 = parentData_l3.get(l3);
                                //Level 4 Open
                                if (l_keyType.get(parentKeys[3]).toString().equalsIgnoreCase("JArray")) {
                                    List<HashMap<String, Object>> parentData_l4 = (List<HashMap<String, Object>>) obj_l3.get(parentKeys[3]);
                                    for (int l4 = 0; l4 < parentData_l4.size(); l4++) {
                                        level_keypos = setLevelWiseKeysPositions(level_keypos, parentData_l4.get(l4), l4, parentKeys[3]);
                                        HashMap<String, Object> obj_l4 = parentData_l4.get(l4);
                                        //Level 5 Open
                                        if (l_keyType.get(parentKeys[4]).toString().equalsIgnoreCase("JArray")) {
                                            setLastLevel_jArray(obj_l4, level_keypos, parentKeys[4], expressionOrValue, apiInParam, inparamType);
                                        } else if (l_keyType.get(parentKeys[4]).toString().equalsIgnoreCase("JObject")) {//JObject
                                            setLastLevel_jObject(obj_l4, level_keypos, parentKeys[4], expressionOrValue, apiInParam, inparamType);
                                        }
                                        //Level 5 Close
                                        parentData_l4.set(l4, obj_l4);
                                    }
                                    obj_l3.put(parentKeys[3], parentData_l4);
                                } else {//JObject
                                    setLastLevel_jObject(obj_l3, level_keypos, parentKeys[3], expressionOrValue, apiInParam, inparamType);
                                }
                                //Level 4 Close
                                parentData_l3.set(l3, obj_l3);
                            }
                            obj_l2.put(parentKeys[2], parentData_l3);
                        } else {//JObject
                            setLastLevel_jObject(obj_l2, level_keypos, parentKeys[2], expressionOrValue, apiInParam, inparamType);
                        }
                        ////Level 3 Close
                        parentData_l2.set(l2, obj_l2);
                    }
                    obj_l1.put(parentKeys[1], parentData_l2);
                } else {//JObject
                    setLastLevel_jObject(obj_l1, level_keypos, parentKeys[1], expressionOrValue, apiInParam, inparamType);
                }
                //Level 2 Close
                parentData_l1.set(l1, obj_l1);
            }
            inputobj.put(parentKeys[0], parentData_l1);
        } else {//JObject
            setLastLevel_jObject(inputobj, level_keypos, parentKeys[0], expressionOrValue, apiInParam, inparamType);
        }
        //Level 1 Close
    }


    private void levelSix(HashMap<String, Object> inputobj, String[] parentKeys, String static_,
                          String expressionOrValue, String apiInParam, String inparamType) {
        loopForAllLevels(inputobj, parentKeys);
        //apiInParam:currentkey,inparamType:currentType(Value,Array,JArray,JObject)
        //all levels pos's
        HashMap<String, Integer> level_keypos = new HashMap<>();
        //Level 1 Open
        if (l_keyType.get(parentKeys[0]).toString().equalsIgnoreCase("JArray")) {
            List<HashMap<String, Object>> parentData_l1 = (List<HashMap<String, Object>>) inputobj.get(parentKeys[0]);
            for (int l1 = 0; l1 < parentData_l1.size(); l1++) {//top level 1 parent loop
                level_keypos = setLevelWiseKeysPositions(level_keypos, parentData_l1.get(l1), l1, parentKeys[0]);
                HashMap<String, Object> obj_l1 = parentData_l1.get(l1);
                //Level 2 Open
                if (l_keyType.get(parentKeys[1]).toString().equalsIgnoreCase("JArray")) {
                    List<HashMap<String, Object>> parentData_l2 = (List<HashMap<String, Object>>) obj_l1.get(parentKeys[1]);
                    for (int l2 = 0; l2 < parentData_l2.size(); l2++) {// level 2 parent loop
                        level_keypos = setLevelWiseKeysPositions(level_keypos, parentData_l2.get(l2), l2, parentKeys[1]);
                        HashMap<String, Object> obj_l2 = parentData_l2.get(l2);
                        //Level 3 Open
                        if (l_keyType.get(parentKeys[2]).toString().equalsIgnoreCase("JArray")) {
                            List<HashMap<String, Object>> parentData_l3 = (List<HashMap<String, Object>>) obj_l2.get(parentKeys[2]);
                            for (int l3 = 0; l3 < parentData_l3.size(); l3++) {// level 3 parent loop
                                level_keypos = setLevelWiseKeysPositions(level_keypos, parentData_l3.get(l3), l3, parentKeys[2]);
                                HashMap<String, Object> obj_l3 = parentData_l3.get(l3);
                                //Level 4 Open
                                if (l_keyType.get(parentKeys[3]).toString().equalsIgnoreCase("JArray")) {
                                    List<HashMap<String, Object>> parentData_l4 = (List<HashMap<String, Object>>) obj_l3.get(parentKeys[3]);
                                    for (int l4 = 0; l4 < parentData_l4.size(); l4++) {// level 4 parent loop
                                        level_keypos = setLevelWiseKeysPositions(level_keypos, parentData_l4.get(l4), l4, parentKeys[3]);
                                        HashMap<String, Object> obj_l4 = parentData_l4.get(l4);
                                        //Level 5 Open
                                        if (l_keyType.get(parentKeys[4]).toString().equalsIgnoreCase("JArray")) {
                                            List<HashMap<String, Object>> parentData_l5 = (List<HashMap<String, Object>>) obj_l4.get(parentKeys[4]);
                                            for (int l5 = 0; l5 < parentData_l5.size(); l5++) {//level 5 parent loop
                                                level_keypos = setLevelWiseKeysPositions(level_keypos, parentData_l5.get(l5), l5, parentKeys[4]);
                                                HashMap<String, Object> obj_l5 = parentData_l5.get(l5);
                                                //Level 6 Open
                                                if (l_keyType.get(parentKeys[5]).toString().equalsIgnoreCase("JArray")) {
                                                    setLastLevel_jArray(obj_l5, level_keypos, parentKeys[5], expressionOrValue, apiInParam, inparamType);
                                                } else {//JObject
                                                    setLastLevel_jObject(obj_l5, level_keypos, parentKeys[5], expressionOrValue, apiInParam, inparamType);
                                                }
                                                //Level 6 Close
                                                parentData_l5.set(l5, obj_l5);
                                            }
                                            obj_l4.put(parentKeys[4], parentData_l5);
                                        } else {//JObject
                                            setLastLevel_jObject(obj_l4, level_keypos, parentKeys[4], expressionOrValue, apiInParam, inparamType);
                                        }
                                        //Level 5 Close
                                        parentData_l4.set(l4, obj_l4);
                                    }
                                    obj_l3.put(parentKeys[3], parentData_l4);
                                } else {//JObject
                                    setLastLevel_jObject(obj_l3, level_keypos, parentKeys[3], expressionOrValue, apiInParam, inparamType);
                                }
                                //Level 4 Close
                                parentData_l3.set(l3, obj_l3);
                            }
                            obj_l2.put(parentKeys[2], parentData_l3);
                        } else {//JObject
                            setLastLevel_jObject(obj_l2, level_keypos, parentKeys[2], expressionOrValue, apiInParam, inparamType);
                        }
                        ////Level 3 Close
                        parentData_l2.set(l2, obj_l2);
                    }
                    obj_l1.put(parentKeys[1], parentData_l2);
                } else {//JObject
                    setLastLevel_jObject(obj_l1, level_keypos, parentKeys[1], expressionOrValue, apiInParam, inparamType);
                }
                //Level 2 Close
                parentData_l1.set(l1, obj_l1);
            }
            inputobj.put(parentKeys[0], parentData_l1);
        } else {//JObject
            setLastLevel_jObject(inputobj, level_keypos, parentKeys[0], expressionOrValue, apiInParam, inparamType);
        }
        //Level 1 Close
    }

    private LinkedHashMap<String, List<String>> getFilterData(LinkedHashMap<String, List<String>> dataSource,
                                                              List<API_InputParam_Bean> filters,
                                                              HashMap<String, Object> parentObj_wherecolVal,
                                                              HashMap<String, Integer> level_keypos,
                                                              String parentKeyLastLevel) {
        //(inputParameter(value/expression))InParam_ExpressionValue:(im:SubControls.states.states_state_id_ProcessRow)
        //(formFieldsInputParam)InParam_Name:dist_state_id
        //(operator)InParam_Operator:Equals
        //(condition)InParam_and_or
        //(NearBy)InParam_near_by_distance
        //(NoofRecords)InParam_near_by_records
        String condition = null;
        String whereColVal = "";
        for (int f = 0; f < filters.size(); f++) {
            API_InputParam_Bean filter = filters.get(f);
            String whereColName = filter.getInParam_Name();
            String operator = filter.getInParam_Operator();
            String expression = filter.getInParam_ExpressionValue();
            condition = filter.getInParam_and_or();
            String nearBy = filter.get_InParam_nearBy();
            String noofRecords = filter.get_InParam_noOfRecords();
            if (dataSource.containsKey(whereColName) && expression != null && !expression.isEmpty()) {
                if (expression.startsWith("(im:")) {
                    expression = expression.substring(1, expression.length() - 1);
                    String[] keyExpression = expression.split("\\.");
                    if (keyExpression[2].endsWith("ProcessRow")) {
                        keyExpression[2] = keyExpression[2].substring(0, keyExpression[2].lastIndexOf("_"));

                        AppConstants.SF_Container_position = level_keypos.get(keyExpression[2]);
                        whereColVal = ehelper.ExpressionHelper(context, filter.getInParam_ExpressionValue());
                    }
                } else {
                    whereColVal = ehelper.ExpressionHelper(context, filter.getInParam_ExpressionValue());
                }
                //filter datasource
                List<String> temp = dataSource.get(whereColName);
                List<Integer> filterPosInDS = new ArrayList<>();
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i).equals(whereColVal)) {//condition,Equal
                        filterPosInDS.add(i);
                    }
                }
                LinkedHashMap<String, List<String>> filteredMap = new LinkedHashMap<>();
                for (Map.Entry<String, List<String>> entry : dataSource.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                    List<String> uVals = new ArrayList<>();
                    for (int fpos = 0; fpos < filterPosInDS.size(); fpos++) {
                        uVals.add(entry.getValue().get(filterPosInDS.get(fpos)));
                    }
                    filteredMap.put(entry.getKey(), uVals);
                }
                dataSource = filteredMap;
            } else {
                //??? if whereColName dont contain in datasource, wt should i do? clear or no
            }
        }
        return dataSource;
    }

    private String getKeyPath(String keyName, JSONArray jArrayKeys) throws JSONException {
        //keyName	:	StateID
        //path	:	States/StateID
        String path = "";
        for (int i = 0; i < jArrayKeys.length(); i++) {
            JSONObject jsonObject = jArrayKeys.getJSONObject(i);
            if (jsonObject.get("keyName").toString().equalsIgnoreCase(keyName)) {
                path = jsonObject.get("path").toString();
                break;
            }
        }
        return path;
    }

    private void loopForAllLevels(HashMap<String, Object> inputobj, String[] parentKeys) {

        for (int i = 0; i < parentKeys.length - 1; i++) {
            if (l_keyType.get(parentKeys[0]).toString().equalsIgnoreCase("JArray")) {

            } else if (l_keyType.get(parentKeys[0]).toString().equalsIgnoreCase("JObject")) {

            }
        }

        for (HashMap.Entry<String, Object> entry : inputobj.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                //(HashMap<String, Object>) value

            } else if (value instanceof List) {
                for (Object listItem : (List<?>) value) {
                    if (listItem instanceof Map) {
                        //(Map<String, Object>) listItem
                    } else {
                        //listItem.toString()
                    }
                }
            } else {
                //value.toString()

            }
        }
    }

}
*/
