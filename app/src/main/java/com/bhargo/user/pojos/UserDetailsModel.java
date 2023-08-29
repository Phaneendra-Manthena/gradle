package com.bhargo.user.pojos;

import java.util.List;

public class UserDetailsModel {

    private String Status;
    private String Message;
    /* New Version Start*/
    private UserData UserData;
    List<RolesMasterModel> RolesMaster;
    List<UserTypesMasterModel> UserUserTypes;
    List<PostsMasterModel> UserPosts;
    /* New Version End*/
    private UserDetails UserDeatils;
    private List<UserPostDetails> UserPostDetails;
    private ReportingUserDeatils ReportingUserDeatils;






    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public UserDetails getUserDeatils() {
        return UserDeatils;
    }

    public void setUserDeatils(UserDetails userDeatils) {
        UserDeatils = userDeatils;
    }

    public UserDetailsModel.ReportingUserDeatils getReportingUserDeatils() {
        return ReportingUserDeatils;
    }

    public void setReportingUserDeatils(UserDetailsModel.ReportingUserDeatils reportingUserDeatils) {
        ReportingUserDeatils = reportingUserDeatils;
    }

//
    public class ReportingUserDeatils
    {
        private String Role;

        private String Designation;

        private String Department;

        private String Email;

        private String PhoneNo;

        private String UserId;

        private String LocationLevel;

        private String ProfilePIc;

        private String LocationStructure;

        private String Name;

        public String getRole ()
        {
            return Role;
        }

        public void setRole (String Role)
        {
            this.Role = Role;
        }

        public String getDesignation ()
        {
            return Designation;
        }

        public void setDesignation (String Designation)
        {
            this.Designation = Designation;
        }

        public String getDepartment ()
        {
            return Department;
        }

        public void setDepartment (String Department)
        {
            this.Department = Department;
        }

        public String getEmail ()
        {
            return Email;
        }

        public void setEmail (String Email)
        {
            this.Email = Email;
        }

        public String getPhoneNo ()
        {
            return PhoneNo;
        }

        public void setPhoneNo (String PhoneNo)
        {
            this.PhoneNo = PhoneNo;
        }

        public String getUserId ()
        {
            return UserId;
        }

        public void setUserId (String UserId)
        {
            this.UserId = UserId;
        }

        public String getLocationLevel ()
        {
            return LocationLevel;
        }

        public void setLocationLevel (String LocationLevel)
        {
            this.LocationLevel = LocationLevel;
        }

        public String getProfilePIc ()
        {
            return ProfilePIc;
        }

        public void setProfilePIc (String ProfilePIc)
        {
            this.ProfilePIc = ProfilePIc;
        }

        public String getLocationStructure ()
        {
            return LocationStructure;
        }

        public void setLocationStructure (String LocationStructure)
        {
            this.LocationStructure = LocationStructure;
        }

        public String getName ()
        {
            return Name;
        }

        public void setName (String Name)
        {
            this.Name = Name;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [Role = "+Role+", Designation = "+Designation+", Department = "+Department+", Email = "+Email+", PhoneNo = "+PhoneNo+", UserId = "+UserId+", LocationLevel = "+LocationLevel+", ProfilePIc = "+ProfilePIc+", LocationStructure = "+LocationStructure+", Name = "+Name+"]";
        }
    }

    public List<com.bhargo.user.pojos.UserPostDetails> getUserPostDetails() {
        return UserPostDetails;
    }

    public void setUserPostDetails(List<com.bhargo.user.pojos.UserPostDetails> userPostDetails) {
        UserPostDetails = userPostDetails;
    }
    public List<RolesMasterModel> getRolesMaster() {
        return RolesMaster;
    }

    public void setRolesMaster(List<RolesMasterModel> rolesMaster) {
        RolesMaster = rolesMaster;
    }

    public com.bhargo.user.pojos.UserData getUserData() {
        return UserData;
    }

    public void setUserData(com.bhargo.user.pojos.UserData userData) {
        UserData = userData;
    }

    public List<UserTypesMasterModel> getUserUserTypes() {
        return UserUserTypes;
    }

    public void setUserUserTypes(List<UserTypesMasterModel> userUserTypes) {
        UserUserTypes = userUserTypes;
    }

    public List<PostsMasterModel> getUserPosts() {
        return UserPosts;
    }

    public void setUserPosts(List<PostsMasterModel> userPosts) {
        UserPosts = userPosts;
    }


}
