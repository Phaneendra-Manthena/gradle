package com.bhargo.user.pojos;

import java.util.List;

public class UserDetails {

    private String Role;

    private String DesignationID;
    private String Designation;

    private String DepartmentID;
    private String Department;

    private String Email;

    private String PhoneNo;

    private String UserId;

    private String LocationLevel;

    private String ProfilePic;

    private String Name;

    private String LocatonLevel;

    private String LocationLevelName;

    private String LocationCode;

    private String LocationCodeName;

    List<SublocationsModel> Sublocations;

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getLocationLevel() {
        return LocationLevel;
    }

    public void setLocationLevel(String locationLevel) {
        LocationLevel = locationLevel;
    }

    public String getProfilePIc() {
        return ProfilePic;
    }

    public void setProfilePIc(String profilePIc) {
        ProfilePic = profilePIc;
    }

    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }

    public String getLocatonLevel() {
        return LocatonLevel;
    }

    public void setLocatonLevel(String locatonLevel) {
        LocatonLevel = locatonLevel;
    }

    public String getLocationLevelName() {
        return LocationLevelName;
    }

    public void setLocationLevelName(String locationLevelName) {
        LocationLevelName = locationLevelName;
    }

    public String getLocationCode() {
        return LocationCode;
    }

    public void setLocationCode(String locationCode) {
        LocationCode = locationCode;
    }

    public String getLocationCodeName() {
        return LocationCodeName;
    }

    public void setLocationCodeName(String locationCodeName) {
        LocationCodeName = locationCodeName;
    }

    public List<SublocationsModel> getSublocations() {
        return Sublocations;
    }

    public void setSublocations(List<SublocationsModel> sublocations) {
        Sublocations = sublocations;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public String getDesignationID() {
        return DesignationID;
    }

    public void setDesignationID(String designationID) {
        DesignationID = designationID;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String departmentID) {
        DepartmentID = departmentID;
    }


}
