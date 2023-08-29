package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by SantoshB on 30/10/2019.
 */

public class GO_Form_Objects implements Serializable {

    private String Form_ID,Form_Title;
    private LinkedHashMap<String,String> Form_Input_Controls;
    private LinkedHashMap<String,List<String>> Form_Output_Control_Value;

    public String getForm_ID() {
        return Form_ID;
    }

    public void setForm_ID(String form_ID) {
        Form_ID = form_ID;
    }

    public String getForm_Title() {
        return Form_Title;
    }

    public void setForm_Title(String form_Title) {
        Form_Title = form_Title;
    }

    public LinkedHashMap<String, String> getForm_Input_Controls() {
        return Form_Input_Controls;
    }

    public void setForm_Input_Controls(LinkedHashMap<String, String> form_Input_Controls) {
        Form_Input_Controls = form_Input_Controls;
    }

    public LinkedHashMap<String, List<String>> getForm_Output_Control_Value() {
        return Form_Output_Control_Value;
    }

    public void setForm_Output_Control_Value(LinkedHashMap<String, List<String>> form_Output_Control_Value) {
        Form_Output_Control_Value = form_Output_Control_Value;
    }




}
