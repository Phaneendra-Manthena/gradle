package com.bhargo.user.pojos;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class FormLevelTranslation implements Serializable {

    private LinkedHashMap<String,String> translatedAppNames;
    private LinkedHashMap<String,String> translatedAppDescriptions;
    private LinkedHashMap<String,String> translatedAppTitleMap;
    private boolean noTranslationsExist;

    public LinkedHashMap<String, String> getTranslatedAppNames() {
        return translatedAppNames;
    }

    public void setTranslatedAppNames(LinkedHashMap<String, String> translatedAppNames) {
        this.translatedAppNames = translatedAppNames;
    }

    public LinkedHashMap<String, String> getTranslatedAppDescriptions() {
        return translatedAppDescriptions;
    }

    public void setTranslatedAppDescriptions(LinkedHashMap<String, String> translatedAppDescriptions) {
        this.translatedAppDescriptions = translatedAppDescriptions;
    }

    public LinkedHashMap<String, String> getTranslatedAppTitleMap() {
        return translatedAppTitleMap;
    }

    public void setTranslatedAppTitleMap(LinkedHashMap<String, String> translatedAppTitleMap) {
        this.translatedAppTitleMap = translatedAppTitleMap;
    }

    public boolean isNoTranslationsExist() {
        return noTranslationsExist;
    }

    public void setNoTranslationsExist(boolean noTranslationsExist) {
        this.noTranslationsExist = noTranslationsExist;
    }
}
