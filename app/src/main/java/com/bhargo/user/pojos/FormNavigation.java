package com.bhargo.user.pojos;

public class FormNavigation {

    private String formName;
    private boolean restoreForm;

    public FormNavigation() {
    }

    public FormNavigation(String formName, boolean restoreForm) {
        this.formName = formName;
        this.restoreForm = restoreForm;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formNam) {
        this.formName = formNam;
    }

    public boolean isRestoreForm() {
        return restoreForm;
    }

    public void setRestoreForm(boolean restoreForm) {
        this.restoreForm = restoreForm;
    }
}
