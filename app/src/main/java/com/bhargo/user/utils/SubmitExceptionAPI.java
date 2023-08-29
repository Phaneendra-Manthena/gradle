package com.bhargo.user.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.ExceptionPostModel;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubmitExceptionAPI {

    public static void execute(Context context, boolean finishActivity,ExceptionPostModel exceptionPostModel){

        Gson gson = new Gson();
        String json = gson.toJson(exceptionPostModel);

        GetServices getServices =  RetrofitUtils.getUserService();

        Call<ResponseBody> call = getServices.exceptionService(exceptionPostModel);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                try {
                    if(response.body()!=null) {
                        String resp = response.body().string();
                        Log.d("ExceptionSubmitResponse",resp);
                        ImproveHelper.showToast(context,"Some exception occurred please try again");
                        if(finishActivity) {
                            ((Activity) context).finish();
                        }
                    }else{
                        Log.d("ExceptionSubmitResponse","Exception submission failed");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("ExceptionSubmitResponse","Exception service failure");
            }
        });


    }
}
