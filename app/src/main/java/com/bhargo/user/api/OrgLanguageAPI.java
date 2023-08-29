package com.bhargo.user.api;


import android.content.Context;


import com.bhargo.user.R;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.LanguageDetails;
import com.bhargo.user.pojos.OrgLanguages;
import com.bhargo.user.pojos.UserDetailsData;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.firebase.database.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrgLanguageAPI {
    private final Context context;
    private LanguageListener languageListener;

    public OrgLanguageAPI(Context context) {
        this.context = context;
    }

    public void setLanguageListener(LanguageListener languageListener){
        this.languageListener = languageListener;
        initService();
    }

    private void initService() {
        SessionManager sessionManager = new SessionManager(context);
        GetServices getServices = RetrofitUtils.getUserService();
        UserDetailsData userDetailsData = new UserDetailsData();
        userDetailsData.setOrgId(sessionManager.getOrgIdFromSession());
        Call<OrgLanguages> orgLanguagesCall = getServices.getLanguages(userDetailsData);
        orgLanguagesCall.enqueue(new Callback<OrgLanguages>() {
            @Override
            public void onResponse(@NotNull Call<OrgLanguages> call, @NotNull Response<OrgLanguages> response) {
                if (response.body() != null) {
                    OrgLanguages orgLanguages = response.body();
                    if (orgLanguages.getStatus().equalsIgnoreCase("200")){
                        languageListener.onResponse(orgLanguages.getLanguageDetails());
                    }else {
                        if(orgLanguages.getMessage()!=null){
                            languageListener.onFailure(orgLanguages.getMessage());
                        }else{
                            languageListener.onFailure(orgLanguages.getStatus());
                        }
                    }
                }else{
                    languageListener.onFailure(context.getString(R.string.server_response_null));
                }
            }

            @Override
            public void onFailure(@NotNull Call<OrgLanguages> call, @NotNull Throwable t) {
                languageListener.onFailure(t.toString());

            }
        });

    }

    public interface LanguageListener{
        void onResponse(List<LanguageDetails> languagesList);
        void onFailure(String error);
    }
}
