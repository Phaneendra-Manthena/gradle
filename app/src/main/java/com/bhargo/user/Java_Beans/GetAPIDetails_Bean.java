package com.bhargo.user.Java_Beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetAPIDetails_Bean implements Serializable {

    @SerializedName("Status")
    public String Status;

    @SerializedName("ServiceData")
    APIDetails ServiceData ;








    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


    public APIDetails getServiceData() {
        return ServiceData;
    }

    public void setServiceData(APIDetails serviceData) {
        ServiceData = serviceData;
    }

    public APIDetails NewAPIDetails(){
        return new APIDetails();
    }

    public  class APIDetails implements Serializable{
        @SerializedName("ServiceName")
        private String ServiceName;

        @SerializedName("ServiceDesc")
        private String ServiceDesc;

        @SerializedName("ServiceSource")
        private String ServiceSource;

        @SerializedName("ServiceType")
        private String ServiceType;

        @SerializedName("ServiceCallsAt")
        private String ServiceCallsAt;

        @SerializedName("ServiceResult")
        private String ServiceResult;

        @SerializedName("ServiceURL")
        private String ServiceURl;

        @SerializedName("ServiceURl_Mask")
        private String ServiceURl_Mask;

        @SerializedName("InputParameters")
        private String InputParameters;

        @SerializedName("Outputparameters")
        private String OutputParameters;

        @SerializedName("OutputType")
        private String OutputType;

        @SerializedName("serviceMethod")
        private String MethodName;

        @SerializedName("NameSpace")
        private String NameSpace;

        @SerializedName("MethodType")
        private String MethodType;

        @SerializedName("QueryType")
        private String QueryType;

        @SerializedName("SuccessCaseDetails")
        private String SuccessCaseDetails;



        public String getServiceName() {
            return ServiceName;
        }

        public void setServiceName(String serviceName) {
            ServiceName = serviceName;
        }

        public String getServiceDesc() {
            return ServiceDesc;
        }

        public void setServiceDesc(String serviceDesc) {
            ServiceDesc = serviceDesc;
        }

        public String getServiceSource() {
            return ServiceSource;
        }

        public void setServiceSource(String serviceSource) {
            ServiceSource = serviceSource;
        }

        public String getServiceType() {
            return ServiceType;
        }

        public void setServiceType(String serviceType) {
            ServiceType = serviceType;
        }

        public String getServiceCallsAt() {
            return ServiceCallsAt;
        }

        public void setServiceCallsAt(String serviceCallsAt) {
            ServiceCallsAt = serviceCallsAt;
        }

        public String getServiceResult() {
            return ServiceResult;
        }

        public void setServiceResult(String serviceResult) {
            ServiceResult = serviceResult;
        }

        public String getServiceURl() {
            return ServiceURl;
        }

        public void setServiceURl(String serviceURl) {
            ServiceURl = serviceURl;
        }

        public String getServiceURl_Mask() {
            return ServiceURl_Mask;
        }

        public void setServiceURl_Mask(String serviceURl_Mask) {
            ServiceURl_Mask = serviceURl_Mask;
        }

        public String getOutputParameters() {
            return OutputParameters;
        }

        public void setOutputParameters(String outputParameters) {
            OutputParameters = outputParameters;
        }

        public String getOutputType() {
            return OutputType;
        }

        public void setOutputType(String outputType) {
            OutputType = outputType;
        }

        public String getMethodName() {
            return MethodName;
        }

        public void setMethodName(String methodName) {
            MethodName = methodName;
        }

        public String getNameSpace() {
            return NameSpace;
        }

        public void setNameSpace(String nameSpace) {
            NameSpace = nameSpace;
        }

        public String getMethodType() {
            return MethodType;
        }

        public void setMethodType(String methodType) {
            MethodType = methodType;
        }

        public String getQueryType() {
            return QueryType;
        }

        public void setQueryType(String queryType) {
            QueryType = queryType;
        }

        public String getSuccessCaseDetails() {
            return SuccessCaseDetails;
        }

        public void setSuccessCaseDetails(String successCaseDetails) {
            SuccessCaseDetails = successCaseDetails;
        }
    }
}
