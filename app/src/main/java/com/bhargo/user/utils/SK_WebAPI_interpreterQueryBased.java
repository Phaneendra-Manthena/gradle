package com.bhargo.user.utils;

import android.app.ProgressDialog;
import android.util.Log;

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

public class SK_WebAPI_interpreterQueryBased {

    GetServices getServices;

    Map<String, String> InputMap;
    public String Service_NAMESPACE = "http://tempuri.org/",
            Path="ImproveApplications_V3.2/api/APIBuilderServices/APIServiceExecutionMethod";
//    Path="SRUSHTIApplication_V3.2/api/APIBuilderServices/APIServiceExecutionMethod";
    public List<String> OutParam_Names;
    public String Output_Type,Service_Result;
    public LinkedHashMap<String, List<String>> OutputData=new LinkedHashMap<String, List<String>>();
    public ProgressDialog progDailog;

    public void CallSoap_Service(String Service_NAMESPACE,
                                 Map<String, String> InputMap, List<String> OutParam_Names,
                                 final String Output_Type, String Service_Result, ProgressDialog progDailog,String token){




        this.Service_NAMESPACE=Service_NAMESPACE;
        this.InputMap=InputMap;
        this.OutParam_Names=OutParam_Names;
        this.Output_Type=Output_Type;
        this.Service_Result=Service_Result;
        this.progDailog=progDailog;



        getServices = RetrofitUtils.getUserService();


        Call<String> call = getServices.getServiceDataForPost(Path,InputMap,token);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.e("Result: ", "" + response.body().toString());
                if(Output_Type.equalsIgnoreCase("XML")){
                    Convert_Soap(response.body().toString());
                }else{
                    Convert_JSONA(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                Log.e("MainActivity", t.toString());
            }
        });

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

            progDailog.dismiss();

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

            progDailog.dismiss();


        } catch (JSONException e) {
            e.printStackTrace();
            progDailog.dismiss();
        }
    }

    public void Convert_JSONA(String XMLString){
        Document doc=XMLString_To_Document(XMLString);


        try {
            if(Service_Result.equalsIgnoreCase("Single")) {
                NodeList NodeOfArr=doc.getChildNodes();
                String serviceResponse=NodeOfArr.item(0).getTextContent();
                JSONObject Jobj = new JSONObject(serviceResponse);
                for (int i = 0; i <OutParam_Names.size() ; i++) {
                    String jobjvalue=Jobj.getString(OutParam_Names.get(i));
                    List<String> value=new ArrayList<String>();
                    value.add(jobjvalue);
                    OutputData.put(OutParam_Names.get(i).toLowerCase(),value);
                }
            }else{
                NodeList NodeOfArr=doc.getChildNodes();
                String serviceResponse=NodeOfArr.item(0).getTextContent();

                JSONObject JobjMain=new JSONObject(serviceResponse);
                JSONArray Jarr=JobjMain.getJSONArray("Data");
                if(Jarr.length()!=0) {
                    for (int i = 0; i < Jarr.length(); i++) {
                        JSONObject Jobj = Jarr.getJSONObject(i);
                        for (int j = 0; j < OutParam_Names.size(); j++) {
                            String jobjvalue = Jobj.getString(OutParam_Names.get(j));
                            if (OutputData.containsKey(OutParam_Names.get(j).toLowerCase())) {
                                List<String> value = OutputData.get(OutParam_Names.get(j).toLowerCase());
                                value.add(jobjvalue);
                                OutputData.put(OutParam_Names.get(j).toLowerCase(), value);
                            } else {
                                List<String> value = new ArrayList<String>();
                                value.add(jobjvalue);
                                OutputData.put(OutParam_Names.get(j).toLowerCase(), value);
                            }
                        }
                    }
                }
            }

            progDailog.dismiss();


        } catch (JSONException e) {
            e.printStackTrace();
            progDailog.dismiss();
        }
    }

}
