package com.bhargo.user.pojos.firebase;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;

@IgnoreExtraProperties
public class UserDetails implements Serializable {


    private String Designation;
    private String ID;
    private String ImagePath;
    private String IsActive;
    private String NameLowerCase;
    private String Mobile;
    private String Name;
    private String Role;
    private String Status;
    private String userid;
    private boolean isChecked = false;
    private int AppStatus;
    private int eLearningStatus;
    private int TaskStatus;
    private int Logout;

    public UserDetails() {
    }

    public UserDetails(String designation, String ID, String imageUrl, String mobile, String name, String role, String status,int logout) {
        this.Designation = designation;
        this.ID = ID;
        this.ImagePath = imageUrl;
        this.Mobile = mobile;
        this.Name = name;
        this.Role = role;
        this.Status = status;
        this.Logout = logout;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @PropertyName("Designation")
    public String getDesignation() {
        return Designation;
    }

    @PropertyName("Designation")
    public void setDesignation(String designation) {
        Designation = designation;
    }

    @PropertyName("ID")
    public String getID() {
        return ID;
    }

    @PropertyName("ID")
    public void setID(String ID) {
        this.ID = ID;
    }

    @PropertyName("Mobile")
    public String getMobile() {
        return Mobile;
    }

    @PropertyName("Mobile")
    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    @PropertyName("Name")
    public String getName() {
        return Name;
    }

    @PropertyName("Name")
    public void setName(String name) {
        Name = name;
    }

    @PropertyName("Role")
    public String getRole() {
        return Role;
    }

    @PropertyName("Role")
    public void setRole(String role) {
        Role = role;
    }

    @PropertyName("Status")
    public String getStatus() {
        return Status;
    }

    @PropertyName("Status")
    public void setStatus(String status) {
        Status = status;
    }

    @PropertyName("ImagePath")
    public String getImagePath() {
        return ImagePath;
    }

    @PropertyName("ImagePath")
    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    @PropertyName("IsActive")
    public String getIsActive() {
        return IsActive;
    }

    @PropertyName("IsActive")
    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    @PropertyName("NameLowerCase")
    public String getNameLowerCase() {
        return NameLowerCase;
    }

    @PropertyName("NameLowerCase")
    public void setNameLowerCase(String nameLowerCase) {
        NameLowerCase = nameLowerCase;
    }

    public int getAppStatus() {
        return AppStatus;
    }

    public void setAppStatus(int appStatus) {
        AppStatus = appStatus;
    }

    public int geteLearningStatus() {
        return eLearningStatus;
    }

    public void seteLearningStatus(int eLearningStatus) {
        this.eLearningStatus = eLearningStatus;
    }

    public int getTaskStatus() {
        return TaskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        TaskStatus = taskStatus;
    }

    public int getLogout() {
        return Logout;
    }

    public void setLogout(int logout) {
        Logout = logout;
    }
}
