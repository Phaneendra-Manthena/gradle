package com.bhargo.user.utils;


import com.bhargo.user.interfaces.GetChatGPTService;
import com.bhargo.user.interfaces.GetMLService;
import com.bhargo.user.interfaces.GetServices;

public class RetrofitUtils {
//        public static final String BASE_URL = "http://182.18.157.124/ImproveApplications/api/";
//    public static final String BASE_URL = "http://18218..157.124/";
public static final String BASE_URL = "http://182.18.157.124"; // MainBhargo
//public static final String BASE_URL = "http://43.254.41.176"; // DLMS
//    public static final String BASE_URL = "http://emms2.ap.gov.in"; // APPR
//public static final String BASE_URL = "http://43.254.41.198/"; // UP IMPACT
//    public static final String BASE_URL = "http://164.52.203.113"; // SRUSHTI
//   public static final String BASE_URL = "http://103.117.174.17"; // Select Ev
    public static final String ML_URL = "http://182.18.157.124:8008/";
    public static final String CHAT_GPT_URL = "https://api.openai.com/";
//   public static final String BASE_URL = "http://43.254.41.198"; // UP IMPACT

    public static GetServices getUserService() {
        return RetrofitClientInstance.getClient_D(BASE_URL).create(GetServices.class);
    }

    public static GetServices getUserServiceA(String Path) {
        return RetrofitClientInstance.getClient(Path + "/").create(GetServices.class);
    }

    public static GetMLService getMLService(){
        return RetrofitClientInstance.getMLClient(ML_URL).create(GetMLService.class);
    }
    public static GetChatGPTService getChatGPTService(){
        return RetrofitClientInstance.getChatGPTClient(CHAT_GPT_URL).create(GetChatGPTService.class);
    }
}
