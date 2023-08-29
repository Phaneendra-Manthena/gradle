package com.bhargo.user.pojos;

import java.io.Serializable;

public class LanguageTypeModel implements Serializable {
    public String language;
    public String languageHint;
    public Boolean isSelected;

    public LanguageTypeModel(){}

    public LanguageTypeModel(String language, String languageHint, Boolean isSelected) {
        this.language = language;
        this.languageHint = languageHint;
        this.isSelected = isSelected;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageHint() {
        return languageHint;
    }

    public void setLanguageHint(String languageHint) {
        this.languageHint = languageHint;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
