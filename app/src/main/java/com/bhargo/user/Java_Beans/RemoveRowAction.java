package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class RemoveRowAction implements Serializable {
    private String multiControlName;
    private String multiControlTYpe;
    private boolean selectSubControl;
    private String selectedRowType;
    private String rowPositionValue;
    private boolean rowPositionExpression;
    private List<RemoveControlItem> removeControlItems;

    private String removeOrHide;

    public String getMultiControlName() {
        return multiControlName;
    }

    public void setMultiControlName(String multiControlName) {
        this.multiControlName = multiControlName;
    }

    public String getMultiControlTYpe() {
        return multiControlTYpe;
    }

    public void setMultiControlTYpe(String multiControlTYpe) {
        this.multiControlTYpe = multiControlTYpe;
    }

    public boolean isSelectSubControl() {
        return selectSubControl;
    }

    public void setSelectSubControl(boolean selectSubControl) {
        this.selectSubControl = selectSubControl;
    }

    public String getSelectedRowType() {
        return selectedRowType;
    }

    public void setSelectedRowType(String selectedRowType) {
        this.selectedRowType = selectedRowType;
    }

    public String getRowPositionValue() {
        return rowPositionValue;
    }

    public void setRowPositionValue(String rowPositionValue) {
        this.rowPositionValue = rowPositionValue;
    }

    public boolean isRowPositionExpression() {
        return rowPositionExpression;
    }

    public void setRowPositionExpression(boolean rowPositionExpression) {
        this.rowPositionExpression = rowPositionExpression;
    }

    public List<RemoveControlItem> getRemoveControlItems() {
        return removeControlItems;
    }

    public void setRemoveControlItems(List<RemoveControlItem> removeControlItems) {
        this.removeControlItems = removeControlItems;
    }

    public String getRemoveOrHide() {
        return removeOrHide;
    }

    public void setRemoveOrHide(String removeOrHide) {
        this.removeOrHide = removeOrHide;
    }
}
