package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by SantoshB on 30/10/2019.
 */

public class GO_Query_Objects implements Serializable {

    private String Query_ID,Form_Title;
    private LinkedHashMap<String,String> Query_Input_Controls;
    private LinkedHashMap<String,List<String>> Query_Output_Control_Value;

    public String getQuery_ID() {
        return Query_ID;
    }

    public void setQuery_ID(String query_ID) {
        Query_ID = query_ID;
    }

    public String getForm_Title() {
        return Form_Title;
    }

    public void setForm_Title(String form_Title) {
        Form_Title = form_Title;
    }

    public LinkedHashMap<String, String> getQuery_Input_Controls() {
        return Query_Input_Controls;
    }

    public void setQuery_Input_Controls(LinkedHashMap<String, String> query_Input_Controls) {
        Query_Input_Controls = query_Input_Controls;
    }

    public LinkedHashMap<String, List<String>> getQuery_Output_Control_Value() {
        return Query_Output_Control_Value;
    }

    public void setQuery_Output_Control_Value(LinkedHashMap<String, List<String>> query_Output_Control_Value) {
        Query_Output_Control_Value = query_Output_Control_Value;
    }


    




}
