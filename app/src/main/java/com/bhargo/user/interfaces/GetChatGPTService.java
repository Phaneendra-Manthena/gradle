package com.bhargo.user.interfaces;


import com.bhargo.user.pojos.ML_TraingResult;
import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


public interface GetChatGPTService {

    //ML_Form
    @POST("v1/chat/completions")
    Call<ResponseBody> chatGptService(@Header ("Authorization")String token,@Body JsonObject data);

}
