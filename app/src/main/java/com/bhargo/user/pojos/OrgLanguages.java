package com.bhargo.user.pojos;

import java.util.List;

public class OrgLanguages {

    private String Status;
    private String Message;
    private List<LanguageDetails> LanguageDetails;

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

    public List<LanguageDetails> getLanguageDetails() {
        return LanguageDetails;
    }

    public void setLanguageDetails(List<LanguageDetails> languageDetails) {
        LanguageDetails = languageDetails;
    }
}
