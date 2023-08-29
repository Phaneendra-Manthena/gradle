package com.bhargo.user.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bhargo.user.interfaces.APIResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SK_FileDataService {
    private static final String TAG = "SK_FileDataService";
    File file;
    String url;
    public ProgressDialog progDailog;
    Map<String, String> InputMap;
    Map<String, String> ImageMap;
    public List<String> OutParam_Names;
    List<String> paths=new ArrayList<String>();
    LinkedHashMap<String,String> OutputPaths=new LinkedHashMap<String, String>();
    String hdOutputSaveFlow;
    public LinkedHashMap<String, List<String>> OutputData=new LinkedHashMap<String, List<String>>();
    JSONObject ServerMap=new JSONObject();
    private Context context;
    private APIResultListener apiResultListener;
    private String bearerToken;

    public void setApiResultListener(APIResultListener context) {
        apiResultListener = context;
    }

    public void CallFileData_Service(String url,Map<String, String> InputMap,
                                     ProgressDialog progDailog,Map<String, String> ImageMap,
                                     List<String> OutParam_Names,LinkedHashMap<String,String> OutputPaths,
                                     String hdOutputSaveFlow,JSONObject ServerMap, String bearerToken){

        this.url=url;
        this.progDailog=progDailog;
        this.InputMap=InputMap;
        this.ImageMap=ImageMap;
        this.OutParam_Names=OutParam_Names;
        this.OutputPaths=OutputPaths;
        this.hdOutputSaveFlow=hdOutputSaveFlow;
        this.ServerMap=ServerMap;
        this.bearerToken = bearerToken;

        FileDataService_AsyncTask Proprunnera = new FileDataService_AsyncTask();
        Proprunnera.execute();

    }

    private class FileDataService_AsyncTask extends AsyncTask<String, String, String> {

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
                OkHttpClient client = new OkHttpClient()
                        .newBuilder()
                        .writeTimeout(180, TimeUnit.SECONDS)
                        .readTimeout(180, TimeUnit.SECONDS)
                        .build();

                MediaType mediaType = MediaType.parse("text/plain");




                MultipartBody.Builder builder= new MultipartBody.Builder()
                        .setType(MultipartBody.FORM);

                Set<String> keys=InputMap.keySet();
                String[] Controlskeys = keys.toArray(new String[keys.size()]);
                for (int i = 0; i < Controlskeys.length; i++) {
                    if(ImageMap.size()>0&&ImageMap.containsKey(Controlskeys[i])){
                        File file = new File(ImageMap.get(Controlskeys[i]));
                        String contentType = file.toURL().openConnection().getContentType();
                        builder.addFormDataPart(Controlskeys[i], Controlskeys[i]+".jpg",
                                RequestBody.create(MediaType.parse(contentType),file));
                    }else{
                        builder.addFormDataPart(Controlskeys[i], InputMap.get(Controlskeys[i]));
                    }

                }
                if(ServerMap!=null) {
                    builder.addFormDataPart("APIInformation", ServerMap.toString());
                }
                RequestBody requestBody =builder.build();


//                RequestBody requestBody = new MultipartBody.Builder()
//                        .setType(MultipartBody.FORM)
//                        .addFormDataPart("FileInput", "test.jpg",
//                                RequestBody.create(MediaType.parse(contentType),file))
//                        .addFormDataPart("TextInput","Santhosh")
//                        .build();



                Request postRequest = new Request.Builder()
                        .url(url)
                        .method("POST", requestBody)
                        .addHeader("Authorization",bearerToken)

//                        .post(requestBody)
                        .build();

                try {
                    okhttp3.Response response = client.newCall(postRequest).execute();
//                    serviceResponse=response.body().string();
                    serviceResponse=response.peekBody(2048).string();
//                    openDataCollectionActivity(response.body().string());
//                    String jsondata ="{\"appName\":\"Credit Application Form\",\"controls\":[{\"controlType\":\"Dropdown\",\"displayName\":\"Postal Addvou\"},{\"controlType\":\"FileUpload\",\"displayName\":\"nenwsry Address\"},{\"controlType\":\"TextInput\",\"displayName\":\"Phone\"},{\"controlType\":\"TextInput\",\"displayName\":\"Emanl\"},{\"controlType\":\"TextInput\",\"displayName\":\"Year E slublnshcd\"},{\"controlType\":\"TextInput\",\"displayName\":\"Contact Name\"},{\"controlType\":\"Dropdown\",\"displayName\":\"I radmn\"}]}";
//                    String jsondata ="{\"appName\":\"Credit Application Form\",\"controls\":[{\"controlType\":\"TextInput\",\"displayName\":\"Postal Addvou\"},{\"controlType\":\"FileUpload\",\"displayName\":\"nenwsry Address\"},{\"controlType\":\"TextInput\",\"displayName\":\"Phone\"},{\"controlType\":\"TextInput\",\"displayName\":\"Emanl\"},{\"controlType\":\"TextInput\",\"displayName\":\"Year E slublnshcd\"},{\"controlType\":\"TextInput\",\"displayName\":\"Contact Name\"},{\"controlType\":\"TextInput\",\"displayName\":\"I radmn\"}]}";
//                                        openDataCollectionActivity(jsondata);
//                    response.body().close();
                } catch (IOException e) {
                    Log.d(TAG, "response()ioerror: " + e.toString());
                    ImproveHelper.showToast(context,e.toString());
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
//                serviceResponse = e.getMessage();
                Log.d(TAG, "response()error: " + e.toString());
                ImproveHelper.showToast(context,e.toString());
            }
            return serviceResponse;
        }

        @Override
        protected void onPostExecute(String s) {

//            progDailog.dismiss();

            try {
                JSONObject jsonObjResponse=new JSONObject();


                for (int i = 0; i < OutParam_Names.size(); i++) {
                    paths.add(OutputPaths.get(OutParam_Names.get(i)));
                }


                DataProcessingJSON processingJson = new DataProcessingJSON();
                String responcestr = serviceResponse;
                if (isJSONArrayValid(responcestr)) {
                    JSONObject jobjresponce = new JSONObject();
                    jobjresponce.put("ArrayObject", responcestr);
                    responcestr = jobjresponce.toString();
                }
                jsonObjResponse = processingJson.processData(hdOutputSaveFlow, responcestr, paths);

                ConvertJSontoHashMapObject(jsonObjResponse,responcestr);
//                        Convert_JSONA(response.body().toString());

            }catch (Exception e){
                Log.e("SK_WebAPI_interpreter", "Result is: "+serviceResponse.toString());

//                progDailog.dismiss();
                apiResultListener.onResult("",null);
            }

        }
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

    public void ConvertJSontoHashMapObject(JSONObject Jsonobject,String responcestr){
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
        apiResultListener.onResult(responcestr,"json");
    }


}
