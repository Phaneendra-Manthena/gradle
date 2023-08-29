package com.bhargo.user.utils;

import com.bhargo.user.Java_Beans.ActionWithoutCondition_Bean;
import com.bhargo.user.Java_Beans.Param;
import com.bhargo.user.Java_Beans.SetProperty;

import java.util.List;

public class GetPropertyValuesFromControl {
    ActionWithoutCondition_Bean actionObj;
    String controlName;
    String propertyName;

    public GetPropertyValuesFromControl(ActionWithoutCondition_Bean actionObj, String controlName, String propertyName) {
        this.actionObj = actionObj;
        this.controlName = controlName;
        this.propertyName = propertyName;
    }

    public String getterPropertyValues() {

        String strGetPropertyValue = null;

        if (actionObj.getSetPropertyActionObject() != null) {
            SetProperty setProperties = actionObj.getSetPropertyActionObject();
            if (setProperties != null) {
                String controlNameSP = setProperties.getControlName();
                String controlTypeSP = setProperties.getControlType();
                List<Param> propertiesList = null;
                if (setProperties.getPropertiesList() != null && setProperties.getPropertiesList().size() > 0) {
                    propertiesList = setProperties.getPropertiesList();
                }
                if (controlNameSP.equalsIgnoreCase(controlName)) {
                    for (int i = 0; i < propertiesList.size(); i++) {
                        Param property = propertiesList.get(i);
                        if (property.getValue().equalsIgnoreCase(propertyName)) {
                            strGetPropertyValue = property.getText();
                        }
                    }
                }
            }
        }

        return strGetPropertyValue;


    }

}
