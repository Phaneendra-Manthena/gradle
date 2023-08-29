package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class APIQuery_Object implements Serializable {

    public String Service_Name,Service_Desc,Service_Source,Service_Type,Service_Result,Service_URL,
    Service_Method,Service_NameSpace,Service_Output_Type,Service_Callsat;
    public List<String> Service_Inparams_list,Service_Outparams_list;

    public  APIQuery_Object(String Service_Name,String Service_Desc,String Service_Source,String Service_Type,
                                String Service_Result,String Service_URL,String Service_Method,
                                String Service_NameSpace,String Service_Output_Type,String Service_Callsat,
                                String[] Service_Inparams_list,String[] Service_Outparams_list){

        this.Service_Name=Service_Name;
        this.Service_Desc=Service_Desc;
        this.Service_Source=Service_Source;
        this.Service_Type=Service_Type;
        this.Service_Result=Service_Result;
        this.Service_URL=Service_URL;
        this.Service_Method=Service_Method;
        this.Service_NameSpace=Service_NameSpace;
        this.Service_Output_Type=Service_Output_Type;
        this.Service_Callsat=Service_Callsat;
        this.Service_Inparams_list=new ArrayList<String >();
        this.Service_Inparams_list= Arrays.asList(Service_Inparams_list);
        this.Service_Outparams_list=new ArrayList<String >();
        this.Service_Outparams_list=Arrays.asList(Service_Outparams_list);


    }
    public String getService_Name() {
        return Service_Name;
    }

    public void setService_Name(String service_Name) {
        Service_Name = service_Name;
    }

    public String getService_Desc() {
        return Service_Desc;
    }

    public void setService_Desc(String service_Desc) {
        Service_Desc = service_Desc;
    }

    public String getService_Source() {
        return Service_Source;
    }

    public void setService_Source(String service_Source) {
        Service_Source = service_Source;
    }

    public String getService_Type() {
        return Service_Type;
    }

    public void setService_Type(String service_Type) {
        Service_Type = service_Type;
    }

    public String getService_Result() {
        return Service_Result;
    }

    public void setService_Result(String service_Result) {
        Service_Result = service_Result;
    }

    public String getService_URL() {
        return Service_URL;
    }

    public void setService_URL(String service_URL) {
        Service_URL = service_URL;
    }

    public String getService_Method() {
        return Service_Method;
    }

    public void setService_Method(String service_Method) {
        Service_Method = service_Method;
    }

    public String getService_NameSpace() {
        return Service_NameSpace;
    }

    public void setService_NameSpace(String service_NameSpace) {
        Service_NameSpace = service_NameSpace;
    }

    public String getService_Output_Type() {
        return Service_Output_Type;
    }

    public void setService_Output_Type(String service_Output_Type) {
        Service_Output_Type = service_Output_Type;
    }

    public String getService_Callsat() {
        return Service_Callsat;
    }

    public void setService_Callsat(String service_Callsat) {
        Service_Callsat = service_Callsat;
    }

    public List<String> getService_Inparams_list() {
        return Service_Inparams_list;
    }

    public void setService_Inparams_list(List<String> service_Inparams_list) {
        Service_Inparams_list = service_Inparams_list;
    }

    public List<String> getService_Outparams_list() {
        return Service_Outparams_list;
    }

    public void setService_Outparams_list(List<String> service_Outparams_list) {
        Service_Outparams_list = service_Outparams_list;
    }
}
