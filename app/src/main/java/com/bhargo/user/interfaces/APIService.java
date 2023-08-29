package com.bhargo.user.interfaces;


import com.bhargo.user.pojos.firebase.MyResponse;
import com.bhargo.user.pojos.firebase.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAuyaqB9Q:APA91bHxkIu8kwNCHyoJTjqsNHGE7lli6PPmCIQWK53Il1CYPFRtG8afx7wVubtl8mRdA7RpZ7seKkRPpF224qxT4gdSnyO2TGOvg4dGJyMZwUKzBMTpBHZ0zKTbSFZCBsrjP4P6sIN3"


            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
