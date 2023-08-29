package com.bhargo.user.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.bhargo.user.interfaces.APIResultListener;
import com.bhargo.user.interfaces.GetServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SK_RestCall_WCF {

    public String Service_NAMESPACE = "http://tempuri.org/", rest_Service_URL = "", Path = "";
    public List<String> OutParam_Names;
    public String Output_Type, Service_Result;
    public LinkedHashMap<String, List<String>> OutputData = new LinkedHashMap<String, List<String>>();
    public ProgressDialog progDailog;
    GetServices getServices;
    Map<String, String> InputMap;
    Map<String, String> HeaderMap;
    String MethodType = "";
    String Extention_Path = "";

    LinkedHashMap<String,String> OutputPaths=new LinkedHashMap<String, String>();
    String hdOutputSaveFlow;
    List<String> paths=new ArrayList<String>();
    String ServiceSource,QueryType;
    private Context context;
    private APIResultListener apiResultListener;

    public void setApiResultListener(APIResultListener context) {
        apiResultListener = context;
    }
    public void CallSoap_Service(String Service_URL, String Service_NAMESPACE,
                                 Map<String, String> InputMap, List<String> OutParam_Names,
                                 final String Output_Type, String Service_Result, final ProgressDialog progDailog,
                                 String MethodType,LinkedHashMap<String,String> OutputPaths,
                                 String hdOutputSaveFlow,String ServiceSource,String QueryType,String token) {

//        this.Path=Service_URL.substring(Service_URL.indexOf("124/")+4);
        this.Path = Service_URL.substring(0, Service_URL.lastIndexOf("/"));
        this.Extention_Path = Service_URL.substring(Service_URL.lastIndexOf("/") + 1);

        this.Service_NAMESPACE = Service_NAMESPACE;
        this.InputMap = InputMap;
        this.OutParam_Names = OutParam_Names;
        this.Output_Type = Output_Type;
        this.Service_Result = Service_Result;
        this.progDailog = progDailog;
        this.MethodType = MethodType;
        getServices = RetrofitUtils.getUserServiceA(Path);

        this.OutputPaths=OutputPaths;
        this.hdOutputSaveFlow=hdOutputSaveFlow;
        this.ServiceSource=ServiceSource;
        this.QueryType=QueryType;

        Call<String> call;
        if (MethodType.equalsIgnoreCase("Get")) {
            call = getServices.getServiceData(Extention_Path, InputMap,token);
        } else {
            call = getServices.getServiceDataForPost(Extention_Path, InputMap,token);
        }

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                /*Log.e("Result: ", "" + response.body().toString());
                if (Output_Type.equalsIgnoreCase("XML")) {
                    Convert_Soap(response.body().toString());

                } else {
                    Convert_JSON(response.body().toString());
                }*/

                try {
                    JSONObject jsonObjResponse=new JSONObject();

                    if(ServiceSource.equalsIgnoreCase("Service Based")) {

                        for (int i = 0; i < OutParam_Names.size(); i++) {
                            paths.add(OutputPaths.get(OutParam_Names.get(i)));
                        }


                        if (Output_Type.equalsIgnoreCase("XML")) {

                            DataProcessingXML processingXML = new DataProcessingXML();
                            jsonObjResponse = processingXML.processData(response.body().toString(), paths);

                            ConvertJSontoHashMapObject(jsonObjResponse,response.body().toString(),"xml");

//                        Convert_Soap("<GetOneStudentXMLResult>"+response.body().toString()+"</GetOneStudentXMLResult>");
                        } else {
                            DataProcessingJSON processingJson = new DataProcessingJSON();
                            String responcestr = response.body().toString();
                            if (isJSONArrayValid(responcestr)) {
                                JSONObject jobjresponce = new JSONObject();
                                jobjresponce.put("ArrayObject", responcestr);
                                responcestr = jobjresponce.toString();
                            }
                            jsonObjResponse = processingJson.processData(hdOutputSaveFlow, responcestr, paths);

                            ConvertJSontoHashMapObject(jsonObjResponse,responcestr,"json");
//                        Convert_JSONA(response.body().toString());
                        }
                    }else{
                        if(QueryType.equalsIgnoreCase("DML")){
                            for (int i = 0; i < OutParam_Names.size(); i++) {
                                paths.add(OutputPaths.get(OutParam_Names.get(i)));
                            }


                            if (Output_Type.equalsIgnoreCase("XML")) {

                                DataProcessingXML processingXML = new DataProcessingXML();
                                jsonObjResponse = processingXML.processData(response.body().toString(), paths);

                                ConvertJSontoHashMapObject(jsonObjResponse,response.body().toString(),"xml");

//                        Convert_Soap("<GetOneStudentXMLResult>"+response.body().toString()+"</GetOneStudentXMLResult>");
                            } else {
                                DataProcessingJSON processingJson = new DataProcessingJSON();
                                String responcestr = response.body().toString();
                                if (isJSONArrayValid(responcestr)) {
                                    JSONObject jobjresponce = new JSONObject();
                                    jobjresponce.put("ArrayObject", responcestr);
                                    responcestr = jobjresponce.toString();
                                }
                                jsonObjResponse = processingJson.processData(hdOutputSaveFlow, responcestr, paths);

                                ConvertJSontoHashMapObject(jsonObjResponse,responcestr,"json");
//                        Convert_JSONA(response.body().toString());
                            }
                        }else{
                            JSONObject jobj=new JSONObject(response.body().toString());
                            if(jobj.getString("Status").equalsIgnoreCase("200")){
                                for (int i = 0; i < OutParam_Names.size(); i++) {
                                    paths.add(OutputPaths.get(OutParam_Names.get(i)));
                                }


                                if (Output_Type.equalsIgnoreCase("XML")) {

                                    DataProcessingXML processingXML = new DataProcessingXML();
                                    jsonObjResponse = processingXML.processData(jobj.toString(), paths);

                                    ConvertJSontoHashMapObject(jsonObjResponse,jobj.toString(),"xml");

//                        Convert_Soap("<GetOneStudentXMLResult>"+response.body().toString()+"</GetOneStudentXMLResult>");
                                } else {
                                    DataProcessingJSON processingJson = new DataProcessingJSON();
                                    String responcestr = jobj.toString();
                                    if (isJSONArrayValid(responcestr)) {
                                        JSONObject jobjresponce = new JSONObject();
                                        jobjresponce.put("ArrayObject", responcestr);
                                        responcestr = jobjresponce.toString();
                                    }
                                    jsonObjResponse = processingJson.processData(hdOutputSaveFlow, responcestr, paths);

                                    ConvertJSontoHashMapObject(jsonObjResponse,responcestr,"json");
//                        Convert_JSONA(response.body().toString());
                                }
                            }else{
                                progDailog.dismiss();
                            }

                        }
                    }
                }catch (Exception e){
                    Log.e("SK_WebAPI_interpreter", "Result is: "+response.body().toString());

                    progDailog.dismiss();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                Log.e("MainActivity", t.toString());
                progDailog.dismiss();
            }
        });

    }

    public Document XMLString_To_Document(String xmlStr) {
        Document doc = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(xmlStr)));


        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static boolean isJSONArrayValid(String string)
    {
        // edited, to include @Arthur's comment
        // e.g. in case JSONArray is valid as well...
        try {

            new JSONArray(string);

        } catch (JSONException ex1) {

            return false;
        }
        return true;
    }

    public void ConvertJSontoHashMapObject(JSONObject Jsonobject,String resposeStr,String responseType){
        try {
            for (int i = 0; i < OutParam_Names.size(); i++) {
                String pathstr=OutputPaths.get(OutParam_Names.get(i)).replace("/","_");
                JSONArray jArray = Jsonobject.getJSONArray(pathstr);
                List<String> listdata = new ArrayList<String>();
                if (jArray != null) {
                    for (int x=0;x<jArray.length();x++){
                        listdata.add(jArray.getString(x));
                    }
                }
                OutputData.put(OutParam_Names.get(i).toLowerCase(),listdata);
            }
        }catch (JSONException e){

        }
//        progDailog.dismiss();
        apiResultListener.onResult(resposeStr,responseType);
    }



}
