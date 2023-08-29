package com.bhargo.user.pojos;

public class DataControls {

    private String DependentControl;

    private String DataControlType;

    private String CreatedUserID;

    private String ControlName;

    private String ControlStatus;

    private String Accessibility;

    private String Version;

    private String loc_type;

    private String TextFilePath;

    private String Z_Status_flag;

    public String getDependentControl() {
        return DependentControl;
    }

    public void setDependentControl(String dependentControl) {
        DependentControl = dependentControl;
    }

    public String getDataControlType() {
        return DataControlType;
    }

    public void setDataControlType(String dataControlType) {
        DataControlType = dataControlType;
    }

    public String getCreatedUserID() {
        return CreatedUserID;
    }

    public void setCreatedUserID(String createdUserID) {
        CreatedUserID = createdUserID;
    }

    public String getControlName() {
        return ControlName;
    }

    public void setControlName(String controlName) {
        ControlName = controlName;
    }

    public String getControlStatus() {
        return ControlStatus;
    }

    public void setControlStatus(String controlStatus) {
        ControlStatus = controlStatus;
    }

    public String getAccessibility() {
        return Accessibility;
    }

    public void setAccessibility(String accessibility) {
        Accessibility = accessibility;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getLoc_type() {
        return loc_type;
    }

    public void setLoc_type(String loc_type) {
        this.loc_type = loc_type;
    }

    public String getTextFilePath() {
        return TextFilePath;
    }

    public void setTextFilePath(String textFilePath) {
        TextFilePath = textFilePath;
    }

    public String getZ_Status_flag() {
        return Z_Status_flag;
    }

    public void setZ_Status_flag(String z_Status_flag) {
        Z_Status_flag = z_Status_flag;
    }
}
