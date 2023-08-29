package com.bhargo.user.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit = null;
    private static Retrofit retrofit_D = null;
    private static Retrofit retrofit_ml = null;
    private static Retrofit retrofit_cg = null;

    public static Retrofit getClient(String url){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(okClient())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        return retrofit;
    }

    public static Retrofit getClient_D(String url){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if(retrofit_D == null){
            retrofit_D = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(okClient())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit_D;
    }
    private static OkHttpClient okClient() {
       return new OkHttpClient.Builder()
               .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS).build();
    }
    public static Retrofit getMLClient(String url){
        if(retrofit_ml == null){
            retrofit_ml = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit_ml;
    }
    public static Retrofit getChatGPTClient(String url){
        if(retrofit_cg == null){
            retrofit_cg = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit_cg;
    }
}
