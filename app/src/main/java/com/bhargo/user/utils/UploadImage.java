package com.bhargo.user.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bhargo.user.R;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadImage extends AsyncTask<String,Void,String> {

    private String title;
    private FileInputStream fileInputStream = null;
    private Context context;
    private String response;

    private OnImageUploaded onImageUploaded;
    private String description;

    public  interface OnImageUploaded {
        void response(String imageUrl);
    }


    public UploadImage(Context context, String title, String description, OnImageUploaded onImageUploaded){

        this.title = title;
        this.description = description;
        this.context = context;
        this.onImageUploaded = onImageUploaded;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            System.out.println("strings[0]=="+strings[0]);
            fileInputStream = new FileInputStream(strings[0]);

            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            String Tag = "fSnd";

            System.out.println("here");
            Log.e(Tag, "Starting Http File Sending to URL");
            // Open a HTTP connection to the URL
            HttpURLConnection conn = (HttpURLConnection)new URL("http://182.18.157.124/improve/FileUpload.aspx")
                    .openConnection();
//            HttpURLConnection conn = (HttpURLConnection)new URL("http://164.52.203.113/SRUSHTI_V3.2/FileUpload.aspx")
//                    .openConnection();
            // Allow Inputs
            conn.setDoInput(true);
            // Allow Outputs
            conn.setDoOutput(true);
            // Don't use a cached copy.
            conn.setUseCaches(false);
            // Use a post method.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            conn.setChunkedStreamingMode(1024);
            // conn.setFixedLengthStreamingMode(1024);
            DataOutputStream dos = new DataOutputStream(
                    conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"description\""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(description);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"title\""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(title);
            dos.writeBytes(lineEnd);

            System.out.println("here1");
            Log.e(Tag, "Headers are written");
            // create a buffer of maximum size
            int bytesAvailable = fileInputStream.available();
            int maxBufferSize = 1 * 1024 * 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];
            // read file and write it into form...
            // int bytesRead = fileInputStream.read(buffer, 0,
            // buffer.length);
            int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                try {
                    dos.write(buffer, 0, bufferSize);
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    // response = "outofmemoryerror";
                    // return response;
                }
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);


            }
                System.out.println("here after writing");
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // close streams
                fileInputStream.close();
                dos.flush();
                Log.e(Tag,
                        "File Sent, Response: "
                                + conn.getResponseCode());

                if (conn.getResponseCode() == 200) {
                    InputStream is = conn.getInputStream();
                    // retrieve the response from server
                    int ch;
                    StringBuffer b = new StringBuffer();
                    while ((ch = is.read()) != -1) {
                        b.append((char) ch);
                    }
                    response = b.toString();

                }else{
                    response ="";
                }

                dos.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("File value =="+s);
    }
}
