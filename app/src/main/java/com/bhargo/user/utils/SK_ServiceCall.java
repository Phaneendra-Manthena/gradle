package com.bhargo.user.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bhargo.user.interfaces.APIResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SK_ServiceCall {

    public String Service_NAMESPACE = "http://dbtagriculture.bihar.gov.in/", Service_METHOD = "", Service_URL = "";
    public List<String> OutParam_Names;
    public String Output_Type,Service_Result;
    public LinkedHashMap<String, List<String>> OutputData=new LinkedHashMap<String, List<String>>();
    public ProgressDialog progDailog;
    Map<String, String> InputMap;

    LinkedHashMap<String,String> OutputPaths=new LinkedHashMap<String, String>();
    String hdOutputSaveFlow;
    List<String> paths=new ArrayList<String>();
    String ServiceSource,QueryType;
    private Context context;
    private APIResultListener apiResultListener;

    public void setApiResultListener(APIResultListener context) {
        apiResultListener = context;
    }
    public void CallSoap_Service(String Service_URL, String Service_METHOD, String Service_NAMESPACE,
                                 Map<String, String> InputMap, List<String> OutParam_Names,
                                 String Output_Type, String Service_Result, ProgressDialog progDailog
            ,LinkedHashMap<String,String> OutputPaths,
                                 String hdOutputSaveFlow,String ServiceSource,String QueryType){

        this.Service_URL=Service_URL;
        this.Service_METHOD=Service_METHOD;
        //this.Service_NAMESPACE=Service_NAMESPACE;
        this.InputMap=InputMap;
        this.OutParam_Names=OutParam_Names;
        this.Output_Type=Output_Type;
        this.Service_Result=Service_Result;
        this.progDailog=progDailog;

        this.OutputPaths=OutputPaths;
        this.hdOutputSaveFlow=hdOutputSaveFlow;
        this.ServiceSource=ServiceSource;
        this.QueryType=QueryType;

        ServiceCall_AsyncTask Proprunnera = new ServiceCall_AsyncTask();
        Proprunnera.execute();

    }

    private class ServiceCall_AsyncTask extends AsyncTask<String, String, String> {

        public String serviceResponse = "";
        SoapObject Serv_response2;
        String Serv_SOAP_ACTION;
        SoapObject response;
        ArrayList<String> soapRes = null;
        int Type = 0;
        private String Serv_resp;

        @Override
        protected String doInBackground(String... strings) {
            publishProgress("Loading ..."); // Calls onProgressUpdate()
            try {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType, encodeParameters(InputMap, "UTF-8"));
                Request request = new Request.Builder()
                        .url(Service_URL+"/"+Service_METHOD)
                        .method("POST", body)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build();
                Response response = client.newCall(request).execute();
                serviceResponse = response.body().string();
                Log.d("response",serviceResponse);
/*

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                SoapObject request = new SoapObject(Service_NAMESPACE, Service_METHOD);
                Set<String> keys=InputMap.keySet();
                String[] Controlskeys = keys.toArray(new String[keys.size()]);
                    for (int i = 0; i < Controlskeys.length; i++) {
                        request.addProperty(Controlskeys[i], InputMap.get(Controlskeys[i]));
                    }

                Serv_SOAP_ACTION = Service_NAMESPACE + Service_METHOD;

                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE transport = new HttpTransportSE(Service_URL);
                transport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                try {
                    transport.call(Service_NAMESPACE + Service_METHOD, envelope);

                } catch (SocketTimeoutException e) {
                    serviceResponse = e.getMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                // bodyIn is the body object received with this envelope
                if (envelope.bodyIn != null) {
                    try {
                        response = (SoapObject) envelope.getResponse();
                        this.serviceResponse=response.toString();
                        Type = 0;
                    } catch (ClassCastException e) {
                        SoapPrimitive resString = (SoapPrimitive) envelope.getResponse();
                        this.serviceResponse = resString.toString();
                        Type = 1;
                    }

                }*/

            } catch (Exception e) {
                e.printStackTrace();
                serviceResponse = e.getMessage();
            }
            return serviceResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObjResponse=new JSONObject();
                if(ServiceSource.equalsIgnoreCase("Service Based")) {
                    for (int i = 0; i < OutParam_Names.size(); i++) {
                        paths.add(OutputPaths.get(OutParam_Names.get(i)));
                    }
                    if (Output_Type.equalsIgnoreCase("XML")) {
                        serviceResponse="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                                "<ArrayOfDBTFarmerRegistration xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://dbtagriculture.bihar.gov.in/\">\n" +
                                "  <DBTFarmerRegistration>\n" +
                                "    <Exist>1</Exist>\n" +
                                "    <Registration_ID>2371516487434</Registration_ID>\n" +
                                "    <FarmerName>RAJIV KUMAR </FarmerName>\n" +
                                "    <Father_Husband_Name>ONKAR  PRASAD</Father_Husband_Name>\n" +
                                "    <CastCateogary>????? ????</CastCateogary>\n" +
                                "    <AadhaarNo>2097XXXX3979</AadhaarNo>\n" +
                                "    <DistrictCode>237</DistrictCode>\n" +
                                "    <DistrictName>NAWADA</DistrictName>\n" +
                                "    <BlockCode>1516</BlockCode>\n" +
                                "    <BlockName>KAWAKOLE</BlockName>\n" +
                                "    <PanchayatCode>98336</PanchayatCode>\n" +
                                "    <PanchayatName>NAWADIH</PanchayatName>\n" +
                                "    <VillageCode>257769</VillageCode>\n" +
                                "    <VillageName>Nawadih</VillageName>\n" +
                                "    <MobileNumber>6201338773</MobileNumber>\n" +
                                "    <DistrictCode_LG>210</DistrictCode_LG>\n" +
                                "    <BlockCode_LG>1918</BlockCode_LG>\n" +
                                "    <VillageCode_LG>257769</VillageCode_LG>\n" +
                                "    <DOB>10-Aug-1995</DOB>\n" +
                                "    <AccountNo>39012805674</AccountNo>\n" +
                                "    <IFSCCODE>SBIN0000141</IFSCCODE>\n" +
                                "    <BankName>SBI</BankName>\n" +
                                "    <Gender>?????</Gender>\n" +
                                "    <Farmertype>??? ?????</Farmertype>\n" +
                                "    <PanchayatCode_LG>98336</PanchayatCode_LG>\n" +
                                "    <FarmerCat>????</FarmerCat>\n" +
                                "  </DBTFarmerRegistration>\n" +
                                "<CATALOG>\n" +
                                "<PLANT>\n" +
                                "<COMMON>Bloodroot</COMMON>\n" +
                                "<BOTANICAL>Sanguinaria canadensis</BOTANICAL>\n" +
                                "<ZONE>4</ZONE>\n" +
                                "<LIGHT>Mostly Shady</LIGHT>\n" +
                                "<PRICE>$2.44</PRICE>\n" +
                                "<AVAILABILITY>031599</AVAILABILITY>\n" +
                                "</PLANT>\n" +
                                "<PLANT>\n" +
                                "<COMMON>Columbine</COMMON>\n" +
                                "<BOTANICAL>Aquilegia canadensis</BOTANICAL>\n" +
                                "<ZONE>3</ZONE>\n" +
                                "<LIGHT>Mostly Shady</LIGHT>\n" +
                                "<PRICE>$9.37</PRICE>\n" +
                                "<AVAILABILITY>030699</AVAILABILITY>\n" +
                                "</PLANT>\n" +
                                "<PLANT>\n" +
                                "<COMMON>Marsh Marigold</COMMON>\n" +
                                "<BOTANICAL>Caltha palustris</BOTANICAL>\n" +
                                "<ZONE>4</ZONE>\n" +
                                "<LIGHT>Mostly Sunny</LIGHT>\n" +
                                "<PRICE>$6.81</PRICE>\n" +
                                "<AVAILABILITY>051799</AVAILABILITY>\n" +
                                "</PLANT>\n" +
                                " </CATALOG>\n" +
                                "</ArrayOfDBTFarmerRegistration>";
                       /* DataProcessingXML processingXML = new DataProcessingXML();
                        jsonObjResponse = processingXML.xmlToJson(serviceResponse, paths);
                        ConvertJSontoHashMapObject(jsonObjResponse,serviceResponse,"xml");*/
                        apiResultListener.onResult(serviceResponse,"xml");

                    } else {
                        DataProcessingJSON processingJson = new DataProcessingJSON();
                        String responcestr = serviceResponse;
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
                            jsonObjResponse = processingXML.processData(serviceResponse, paths);

                            ConvertJSontoHashMapObject(jsonObjResponse,serviceResponse,"xml");

//                        Convert_Soap("<GetOneStudentXMLResult>"+response.body().toString()+"</GetOneStudentXMLResult>");
                        } else {
                            DataProcessingJSON processingJson = new DataProcessingJSON();
                            String responcestr = serviceResponse;
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
                        JSONObject jobj=new JSONObject(serviceResponse);
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
                            apiResultListener.onResult("",null);
//                            progDailog.dismiss();
                        }

                    }
                }
            }catch (Exception e){
                Log.e("SK_WebAPI_interpreter", "Result is: "+serviceResponse.toString());

//                progDailog.dismiss();
                apiResultListener.onResult("",null);
            }

        }
    }

 /*   public void Convert_Soap(SoapObject soapobj){
        try {
            if(Service_Result.equalsIgnoreCase("Single")) {
                for (int i = 0; i <OutParam_Names.size() ; i++) {
                    String objvalue=soapobj.getProperty(OutParam_Names.get(i)).toString();
                    List<String> value=new ArrayList<String>();
                    value.add(objvalue);
                    OutputData.put(OutParam_Names.get(i).toLowerCase(),value);
                }
            }else{
                for (int i = 0; i < soapobj.getPropertyCount(); ++i) {
                    for (int j = 0; j <OutParam_Names.size() ; j++) {
                        if (OutputData.containsKey(OutParam_Names.get(j).toLowerCase())) {
                            List<String> value=OutputData.get(OutParam_Names.get(j).toLowerCase());
                            String objvalue=((SoapObject) soapobj.getProperty(i)).getProperty(OutParam_Names.get(j)).toString();
                            value.add(objvalue);
                            OutputData.put(OutParam_Names.get(j).toLowerCase(),value);
                        }else{
                            String objvalue=((SoapObject) soapobj.getProperty(i)).getProperty(OutParam_Names.get(j)).toString();
                            List<String> value=new ArrayList<String>();
                            value.add(objvalue);
                            OutputData.put(OutParam_Names.get(j).toLowerCase(),value);
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
                    OutputData.put(OutParam_Names.get(i).toLowerCase(),value);
                }
            }else{
                JSONArray Jarr=new JSONArray(serviceResponse);
                for (int i = 0; i <Jarr.length() ; i++) {
                    JSONObject Jobj=Jarr.getJSONObject(i);
                    for (int j = 0; j <OutParam_Names.size() ; j++) {
                        String jobjvalue=Jobj.getString(OutParam_Names.get(j));
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

            progDailog.dismiss();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

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

    public void ConvertJSontoHashMapObject(JSONObject Jsonobject,String responseStr,String responseType){
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
        apiResultListener.onResult(responseStr,responseType);
    }

    private String encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            if (params != null) {
                boolean first = true;
                for (Map.Entry<String, String> entry : params.entrySet()) {

                    if (first)
                        first = false;
                    else
                        encodedParams.append("&");

                    encodedParams.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    encodedParams.append("=");
                    encodedParams.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                }
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }




}
