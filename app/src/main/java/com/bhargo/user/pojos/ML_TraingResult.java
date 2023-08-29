package com.bhargo.user.pojos;

import com.google.gson.annotations.SerializedName;

public class ML_TraingResult {

    @SerializedName("label")
    String label;
    @SerializedName("confidence")
    String confidence;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
}
