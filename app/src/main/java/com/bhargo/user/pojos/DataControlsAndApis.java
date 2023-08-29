package com.bhargo.user.pojos;

import java.util.List;

public class DataControlsAndApis {

    private String Status;

    private String Message;

    private List<APIDetails> APIDetails;

    private  List<QueryDetails>  QueryDetails;

    private  List<DataControls>  DataControls;

    private  List<DataControlDetails>  DataControlDetails;


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

    public List<com.bhargo.user.pojos.APIDetails> getAPIDetails() {
        return APIDetails;
    }

    public void setAPIDetails(List<com.bhargo.user.pojos.APIDetails> APIDetails) {
        this.APIDetails = APIDetails;
    }

    public List<com.bhargo.user.pojos.QueryDetails> getQueryDetails() {
        return QueryDetails;
    }

    public void setQueryDetails(List<com.bhargo.user.pojos.QueryDetails> queryDetails) {
        QueryDetails = queryDetails;
    }

    public List<com.bhargo.user.pojos.DataControls> getDataControls() {
        return DataControls;
    }

    public void setDataControls(List<com.bhargo.user.pojos.DataControls> dataControls) {
        DataControls = dataControls;
    }

    public List<DataControlsAndApis.DataControlDetails> getDataControlDetails() {
        return DataControlDetails;
    }

    public void setDataControlDetails(List<DataControlsAndApis.DataControlDetails> dataControlDetails) {
        DataControlDetails = dataControlDetails;
    }

    public class DataControlDetails{

        String DataControlID;
        String DataControlName;
        String DependentControlName;
        String Version;
        String AccessingType;
        String Status;
        String TextFilePath;
        String ValueColumn;
        String DisplayColumn;

        public String getDataControlID() {
            return DataControlID;
        }

        public void setDataControlID(String dataControlID) {
            DataControlID = dataControlID;
        }

        public String getDataControlName() {
            return DataControlName;
        }

        public void setDataControlName(String dataControlName) {
            DataControlName = dataControlName;
        }

        public String getDependentControlName() {
            return DependentControlName;
        }

        public void setDependentControlName(String dependentControlName) {
            DependentControlName = dependentControlName;
        }

        public String getVersion() {
            return Version;
        }

        public void setVersion(String version) {
            Version = version;
        }

        public String getAccessingType() {
            return AccessingType;
        }

        public void setAccessingType(String accessingType) {
            AccessingType = accessingType;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getTextFilePath() {
            return TextFilePath;
        }

        public void setTextFilePath(String textFilePath) {
            TextFilePath = textFilePath;
        }

        public String getValueColumn() {
            return ValueColumn;
        }

        public void setValueColumn(String valueColumn) {
            ValueColumn = valueColumn;
        }

        public String getDisplayColumn() {
            return DisplayColumn;
        }

        public void setDisplayColumn(String displayColumn) {
            DisplayColumn = displayColumn;
        }
    }
}
