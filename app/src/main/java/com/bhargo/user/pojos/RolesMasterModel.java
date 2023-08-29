package com.bhargo.user.pojos;

import java.io.Serializable;

public class RolesMasterModel implements Serializable {
    
    String UserID;
    String LoginTypeID;
    String ID;
    String Name;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getLoginTypeID() {
        return LoginTypeID;
    }

    public void setLoginTypeID(String loginTypeID) {
        LoginTypeID = loginTypeID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
