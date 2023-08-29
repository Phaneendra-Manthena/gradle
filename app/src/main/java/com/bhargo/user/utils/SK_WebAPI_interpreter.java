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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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

public class SK_WebAPI_interpreter {

    GetServices getServices;

    Map<String, String> InputMap;
    public String Service_NAMESPACE = "http://tempuri.org/",
            Path="ImproveApplications_V3.2/api/APIBuilderServices/APIServiceExecutionMethod";
//            Path="SRUSHTIApplication_V3.2/api/APIBuilderServices/APIServiceExecutionMethod";

    public List<String> OutParam_Names;
    public String Output_Type,Service_Result;
    public LinkedHashMap<String, List<String>> OutputData=new LinkedHashMap<String, List<String>>();
    public ProgressDialog progDailog;
    LinkedHashMap<String,String> OutputPaths=new LinkedHashMap<String, String>();
    String hdOutputSaveFlow;
    List<String> paths=new ArrayList<String>();
    String ServiceSource,QueryType;
    public boolean fromsms=false;
    private Context context;
    private APIResultListener apiResultListener;

    public void setApiResultListener(APIResultListener context) {
        apiResultListener = context;
    }

    public void CallSoap_Service(boolean fromsms,String Service_NAMESPACE,
                                 Map<String, String> InputMap, List<String> OutParam_Names,
                                 final String Output_Type, String Service_Result,
                                 ProgressDialog progDailog,LinkedHashMap<String,String> OutputPaths,
                                 String hdOutputSaveFlow,String ServiceSource,String QueryType,String token){



        this.fromsms=fromsms;
        this.Service_NAMESPACE=Service_NAMESPACE;
        this.InputMap=InputMap;
        this.OutParam_Names=OutParam_Names;
        this.Output_Type=Output_Type;
        this.Service_Result=Service_Result;
        this.progDailog=progDailog;
        this.OutputPaths=OutputPaths;
        this.hdOutputSaveFlow=hdOutputSaveFlow;
        this.ServiceSource=ServiceSource;
        this.QueryType=QueryType;
        Path = GetServices.API_PATH;

        getServices = RetrofitUtils.getUserService();


        Call<String> call = getServices.getServiceDataForPost(Path,InputMap,token);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                //Log.e("Result: ", "" + response.body().toString());
                try {
                    JSONObject jsonObjResponse=new JSONObject();
                    if(fromsms){
//                        progDailog.dismiss();//pending
                        apiResultListener.onResult("",null);
                    }else {
                        if (ServiceSource.equalsIgnoreCase("Service Based")) {

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
                        } else {
                            if (QueryType.equalsIgnoreCase("DML")) {
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
                            } else {
                                JSONObject jobj = new JSONObject(response.body().toString());
                                if (jobj.getString("Status").equalsIgnoreCase("200")) {
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
                                } else {
//                                    progDailog.dismiss();
                                    apiResultListener.onResult("",null);
                                }

                            }
                        }
                    }
                }catch (Exception e){
                    Log.e("SK_WebAPI_interpreter", "Result is: "+response.body().toString());

//                    progDailog.dismiss();
                    apiResultListener.onResult("",null);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                Log.e("SK_WebAPI_interpreter", t.toString());
//                progDailog.dismiss();
                apiResultListener.onResult("",null);
            }
        });

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

    public Document XMLString_To_Document(String xmlStr) {
        Document doc=null;
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

    public void ConvertJSontoHashMapObject(JSONObject Jsonobject,String respStr,String respType){
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
        apiResultListener.onResult(respStr,respType);
    }

    public void Convert_Soap(String XMLString){

        Document doc=XMLString_To_Document(XMLString);
        try {

            if(Service_Result.equalsIgnoreCase("Single")) {
                for (int i = 0; i <OutParam_Names.size() ; i++) {

                    String objvalue=XMLHelper.getValueFromTag(doc,OutParam_Names.get(i)).trim();
                    List<String> value=new ArrayList<String>();
                    value.add(objvalue);
                    OutputData.put(OutParam_Names.get(i).toLowerCase(),value);
                }
            }else{
                NodeList NodeOfArr=doc.getChildNodes();
                NodeList listofItems=NodeOfArr.item(0).getChildNodes();

                for (int i = 0; i <listofItems.getLength(); ++i) {
                    Node nNode = listofItems.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;

                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document TempDoc = dBuilder.newDocument();
                        Node copiedNodeLabel = TempDoc.importNode(nNode, true);
                        TempDoc.appendChild(copiedNodeLabel);

                        for (int j = 0; j < OutParam_Names.size(); j++) {
                            if (OutputData.containsKey(OutParam_Names.get(j).toLowerCase())) {
                                List<String> value = OutputData.get(OutParam_Names.get(j).toLowerCase());
                                String objvalue = XMLHelper.getValueFromTag(TempDoc,OutParam_Names.get(j)).trim();
                                value.add(objvalue);
                                OutputData.put(OutParam_Names.get(j).toLowerCase(), value);
                            } else {
                                String objvalue = XMLHelper.getValueFromTag(TempDoc,OutParam_Names.get(j)).trim();
                                List<String> value = new ArrayList<String>();
                                value.add(objvalue);
                                OutputData.put(OutParam_Names.get(j).toLowerCase(), value);
                            }
                        }
                    }
                }

            }

//            progDailog.dismiss();
            apiResultListener.onResult(XMLString,"xml");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Convert_JSON(String serviceResponse){

        try {
            if(Service_Result.equalsIgnoreCase("Single")) {
                JSONObject Jobj = new JSONObject(serviceResponse);
                for (int i = 0; i <OutParam_Names.size() ; i++) {
                    String jobjvalue=Jobj.getString(OutParam_Names.get(i));
                    List<String> value=new ArrayList<String>();
                    value.add(jobjvalue);
                    OutputData.put(OutParam_Names.get(i),value);
                }
            }else{
                JSONArray Jarr=new JSONArray(serviceResponse);
                for (int i = 0; i <Jarr.length() ; i++) {
                    JSONObject Jobj=Jarr.getJSONObject(i);
                    for (int j = 0; j <OutParam_Names.size() ; j++) {
                        String jobjvalue=Jobj.getString(OutParam_Names.get(j));
                        if(OutputData.containsKey(OutParam_Names.get(j))){
                            List<String> value=OutputData.get(OutParam_Names.get(j));
                            value.add(jobjvalue);
                            OutputData.put(OutParam_Names.get(j),value);
                        }else{
                            List<String> value=new ArrayList<String>();
                            value.add(jobjvalue);
                            OutputData.put(OutParam_Names.get(j),value);
                        }
                    }
                }
            }

//            progDailog.dismiss();
            apiResultListener.onResult(serviceResponse,"json");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Convert_JSONA(String XMLString){
//        Document doc=XMLString_To_Document(XMLString);


        try {
            if(Service_Result.equalsIgnoreCase("Single")) {
//                NodeList NodeOfArr=doc.getChildNodes();
//                String serviceResponse=NodeOfArr.item(0).getTextContent();
                JSONObject Jobj = new JSONObject(XMLString);
                for (int i = 0; i <OutParam_Names.size() ; i++) {
                    String jobjvalue=Jobj.getString(OutParam_Names.get(i));
                    List<String> value=new ArrayList<String>();
                    value.add(jobjvalue);
                    OutputData.put(OutParam_Names.get(i).toLowerCase(),value);
                }
            }else{
//                NodeList NodeOfArr=doc.getChildNodes();
//                String serviceResponse=NodeOfArr.item(0).getTextContent();

                JSONArray Jarr=new JSONArray(XMLString);
                for (int i = 0; i <Jarr.length() ; i++) {
                    JSONObject Jobj=Jarr.getJSONObject(i);
                    for (int j = 0; j <OutParam_Names.size() ; j++) {
                        String jobjvalue=Jobj.getString(OutParam_Names.get(j).toLowerCase());
                        if(OutputData.containsKey(OutParam_Names.get(j).toLowerCase())){
                            List<String> value=OutputData.get(OutParam_Names.get(j).toLowerCase());
                            value.add(jobjvalue);
                            OutputData.put(OutParam_Names.get(j).toLowerCase(),value);
                        }else{
                            List<String> value=new ArrayList<String>();
                            value.add(jobjvalue);
                            OutputData.put(OutParam_Names.get(j).toLowerCase(),value);
                        }
                    }
                }
            }

//            progDailog.dismiss();
            apiResultListener.onResult(XMLString,"json");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
