package com.bhargo.user.interfaces;


import com.bhargo.user.pojos.ML_TraingResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface GetMLService {

    //ML_Form
    @GET("predict")
    Call<ML_TraingResult> InsertMLTesting(@QueryMap Map<String, String> data);

}
