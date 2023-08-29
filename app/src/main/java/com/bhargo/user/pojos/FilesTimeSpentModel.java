package com.bhargo.user.pojos;

import java.util.List;

public class FilesTimeSpentModel {

    List<InsertUserFileVisitsModel> UserFileVisitsList;

    public List<InsertUserFileVisitsModel> getUserFileVisitsList() {
        return UserFileVisitsList;
    }

    public void setUserFileVisitsList(List<InsertUserFileVisitsModel> userFileVisitsList) {
        UserFileVisitsList = userFileVisitsList;
    }


}
