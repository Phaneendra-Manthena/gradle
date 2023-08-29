package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class AssignControl_Bean implements Serializable {

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    private String dataSource;
    private String ControlName;
    private boolean isTwovaluecontrol;
    private boolean isExpression;
    private String ControlValue;
    private String controlIdValue;
    private boolean idExpression;
    private String Activity;//Append_Replace_if_Control_is_Dropdown_checklist_Radio
    private List<Boolean> MultipleExpressionID;
    private List<Boolean> MultipleExpressionText;
    private List<String> MultiplevalueIDs;
    private List<String> MultiplevalueTexts;

    private String Multiplevalue_Type;
    private String Multiplevalue_Advance_ItemText;
    private String Multiplevalue_Advance_ItemID;


    //LanguageSettings
    public List<LanguageMapping> List_OutParam_Languages;
    //ImageSettings

    private List<ImageAdvanced_Mapped_Item> List_OutParam_ImageAdvanced_Items;
    private String OutParam_ImageAdvanced_ImageorNot;
    private String OutParam_ImageAdvanced_ImageSource;
    private String OutParam_ImageAdvanced_ConditionColumn;
    private String OutParam_ImageAdvanced_Operator;
    //MarkerSettings
    private String OutParam_MarkerAdvanced_ImageSource;
    private String OutParam_MarkerAdvanced_ConditionColumn;
    private String OutParam_MarkerAdvanced_Operator;
    private List<ImageAdvanced_Mapped_Item> List_OutParam_MarkerAdvanced_Items;

    private String OutParam_Marker_defultImage;
    private String OutParam_Marker_RenderingType;
    private List<String> OutParam_Marker_popupData;
    private List<String> OutParam_Marker_popupImages;




    private String twoValueControlType;
    private String twoValue_value1;
    private String twoValue_value2;
    private String twoValue_value3;
    private boolean twoValue_expression1;
    private boolean twoValue_expression2;

    public String getTwoValue_value3() {
        return twoValue_value3;
    }

    public void setTwoValue_value3(String twoValue_value3) {
        this.twoValue_value3 = twoValue_value3;
    }

    public boolean isTwoValue_expression3() {
        return twoValue_expression3;
    }

    public void setTwoValue_expression3(boolean twoValue_expression3) {
        this.twoValue_expression3 = twoValue_expression3;
    }

    private boolean twoValue_expression3;



    private boolean isMultivaluescontrol;
    private String multiValuesControlType;
    private List<String> multiValues;
    private List<Boolean> multiValues_expression;


    public String getControlName() {
        return ControlName;
    }

    public void setControlName(String controlName) {
        ControlName = controlName;
    }

    public boolean isTwovaluecontrol() {
        return isTwovaluecontrol;
    }

    public void setTwovaluecontrol(boolean twovaluecontrol) {
        isTwovaluecontrol = twovaluecontrol;
    }

    public boolean isExpression() {
        return isExpression;
    }

    public void setExpression(boolean expression) {
        isExpression = expression;
    }

    public String getControlValue() {
        return ControlValue;
    }

    public void setControlValue(String controlValue) {
        ControlValue = controlValue;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public List<Boolean> getMultipleExpressionID() {
        return MultipleExpressionID;
    }

    public void setMultipleExpressionID(List<Boolean> multipleExpressionID) {
        MultipleExpressionID = multipleExpressionID;
    }

    public List<Boolean> getMultipleExpressionText() {
        return MultipleExpressionText;
    }

    public void setMultipleExpressionText(List<Boolean> multipleExpressionText) {
        MultipleExpressionText = multipleExpressionText;
    }

    public List<String> getMultiplevalueIDs() {
        return MultiplevalueIDs;
    }

    public void setMultiplevalueIDs(List<String> multiplevalueIDs) {
        MultiplevalueIDs = multiplevalueIDs;
    }

    public List<String> getMultiplevalueTexts() {
        return MultiplevalueTexts;
    }

    public void setMultiplevalueTexts(List<String> multiplevalueTexts) {
        MultiplevalueTexts = multiplevalueTexts;
    }

    public String getControlIdValue() {
        return controlIdValue;
    }

    public void setControlIdValue(String controlIdValue) {
        this.controlIdValue = controlIdValue;
    }

    public boolean isIdExpression() {
        return idExpression;
    }

    public void setIdExpression(boolean idExpression) {
        this.idExpression = idExpression;
    }


    public List<LanguageMapping> getList_OutParam_Languages() {
        return List_OutParam_Languages;
    }

    public void setList_OutParam_Languages(List<LanguageMapping> list_OutParam_Languages) {
        List_OutParam_Languages = list_OutParam_Languages;
    }

    public List<ImageAdvanced_Mapped_Item> getList_OutParam_ImageAdvanced_Items() {
        return List_OutParam_ImageAdvanced_Items;
    }

    public void setList_OutParam_ImageAdvanced_Items(List<ImageAdvanced_Mapped_Item> list_OutParam_ImageAdvanced_Items) {
        List_OutParam_ImageAdvanced_Items = list_OutParam_ImageAdvanced_Items;
    }

    public String getOutParam_ImageAdvanced_ImageorNot() {
        return OutParam_ImageAdvanced_ImageorNot;
    }

    public void setOutParam_ImageAdvanced_ImageorNot(String outParam_ImageAdvanced_ImageorNot) {
        OutParam_ImageAdvanced_ImageorNot = outParam_ImageAdvanced_ImageorNot;
    }

    public String getOutParam_ImageAdvanced_ImageSource() {
        return OutParam_ImageAdvanced_ImageSource;
    }

    public void setOutParam_ImageAdvanced_ImageSource(String outParam_ImageAdvanced_ImageSource) {
        OutParam_ImageAdvanced_ImageSource = outParam_ImageAdvanced_ImageSource;
    }

    public String getOutParam_ImageAdvanced_ConditionColumn() {
        return OutParam_ImageAdvanced_ConditionColumn;
    }

    public void setOutParam_ImageAdvanced_ConditionColumn(String outParam_ImageAdvanced_ConditionColumn) {
        OutParam_ImageAdvanced_ConditionColumn = outParam_ImageAdvanced_ConditionColumn;
    }

    public String getOutParam_ImageAdvanced_Operator() {
        return OutParam_ImageAdvanced_Operator;
    }

    public void setOutParam_ImageAdvanced_Operator(String outParam_ImageAdvanced_Operator) {
        OutParam_ImageAdvanced_Operator = outParam_ImageAdvanced_Operator;
    }

    public String getOutParam_MarkerAdvanced_ImageSource() {
        return OutParam_MarkerAdvanced_ImageSource;
    }

    public void setOutParam_MarkerAdvanced_ImageSource(String outParam_MarkerAdvanced_ImageSource) {
        OutParam_MarkerAdvanced_ImageSource = outParam_MarkerAdvanced_ImageSource;
    }

    public String getOutParam_MarkerAdvanced_ConditionColumn() {
        return OutParam_MarkerAdvanced_ConditionColumn;
    }

    public void setOutParam_MarkerAdvanced_ConditionColumn(String outParam_MarkerAdvanced_ConditionColumn) {
        OutParam_MarkerAdvanced_ConditionColumn = outParam_MarkerAdvanced_ConditionColumn;
    }

    public String getOutParam_MarkerAdvanced_Operator() {
        return OutParam_MarkerAdvanced_Operator;
    }

    public void setOutParam_MarkerAdvanced_Operator(String outParam_MarkerAdvanced_Operator) {
        OutParam_MarkerAdvanced_Operator = outParam_MarkerAdvanced_Operator;
    }

    public List<ImageAdvanced_Mapped_Item> getList_OutParam_MarkerAdvanced_Items() {
        return List_OutParam_MarkerAdvanced_Items;
    }

    public void setList_OutParam_MarkerAdvanced_Items(List<ImageAdvanced_Mapped_Item> list_OutParam_MarkerAdvanced_Items) {
        List_OutParam_MarkerAdvanced_Items = list_OutParam_MarkerAdvanced_Items;
    }

    public String getOutParam_Marker_defultImage() {
        return OutParam_Marker_defultImage;
    }

    public void setOutParam_Marker_defultImage(String outParam_Marker_defultImage) {
        OutParam_Marker_defultImage = outParam_Marker_defultImage;
    }

    public String getOutParam_Marker_RenderingType() {
        return OutParam_Marker_RenderingType;
    }

    public void setOutParam_Marker_RenderingType(String outParam_Marker_RenderingType) {
        OutParam_Marker_RenderingType = outParam_Marker_RenderingType;
    }

    public List<String> getOutParam_Marker_popupData() {
        return OutParam_Marker_popupData;
    }

    public void setOutParam_Marker_popupData(List<String> outParam_Marker_popupData) {
        OutParam_Marker_popupData = outParam_Marker_popupData;
    }

    public List<String> getOutParam_Marker_popupImages() {
        return OutParam_Marker_popupImages;
    }

    public void setOutParam_Marker_popupImages(List<String> outParam_Marker_popupImages) {
        OutParam_Marker_popupImages = outParam_Marker_popupImages;
    }

    public String getMultiplevalue_Type() {
        return Multiplevalue_Type;
    }

    public void setMultiplevalue_Type(String multiplevalue_Type) {
        Multiplevalue_Type = multiplevalue_Type;
    }

    public String getMultiplevalue_Advance_ItemText() {
        return Multiplevalue_Advance_ItemText;
    }

    public void setMultiplevalue_Advance_ItemText(String multiplevalue_Advance_ItemText) {
        Multiplevalue_Advance_ItemText = multiplevalue_Advance_ItemText;
    }

    public String getMultiplevalue_Advance_ItemID() {
        return Multiplevalue_Advance_ItemID;
    }

    public void setMultiplevalue_Advance_ItemID(String multiplevalue_Advance_ItemID) {
        Multiplevalue_Advance_ItemID = multiplevalue_Advance_ItemID;
    }

    public String getTwoValueControlType() {
        return twoValueControlType;
    }

    public void setTwoValueControlType(String twoValueControlType) {
        this.twoValueControlType = twoValueControlType;
    }

    public String getTwoValue_value1() {
        return twoValue_value1;
    }

    public void setTwoValue_value1(String twoValue_value1) {
        this.twoValue_value1 = twoValue_value1;
    }

    public String getTwoValue_value2() {
        return twoValue_value2;
    }

    public void setTwoValue_value2(String twoValue_value2) {
        this.twoValue_value2 = twoValue_value2;
    }

    public boolean isTwoValue_expression1() {
        return twoValue_expression1;
    }

    public void setTwoValue_expression1(boolean twoValue_expression1) {
        this.twoValue_expression1 = twoValue_expression1;
    }

    public boolean isTwoValue_expression2() {
        return twoValue_expression2;
    }

    public void setTwoValue_expression2(boolean twoValue_expression2) {
        this.twoValue_expression2 = twoValue_expression2;
    }

    public boolean isMultivaluescontrol() {
        return isMultivaluescontrol;
    }

    public void setMultivaluescontrol(boolean multivaluescontrol) {
        isMultivaluescontrol = multivaluescontrol;
    }

    public String getMultiValuesControlType() {
        return multiValuesControlType;
    }

    public void setMultiValuesControlType(String multiValuesControlType) {
        this.multiValuesControlType = multiValuesControlType;
    }

    public List<String> getMultiValues() {
        return multiValues;
    }

    public void setMultiValues(List<String> multiValues) {
        this.multiValues = multiValues;
    }

    public List<Boolean> getMultiValues_expression() {
        return multiValues_expression;
    }

    public void setMultiValues_expression(List<Boolean> multiValues_expression) {
        this.multiValues_expression = multiValues_expression;
    }
}
