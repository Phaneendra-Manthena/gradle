package com.bhargo.user.ui_form;

import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

public class SubLayoutsModelClass implements Serializable {

    public Float fWidth = 0f;
    String ControlName;
    int position;
    int subLayoutId;
    int tag;
    int count;
    int childCount;
    int parentPosition = -1;
    int LayoutWidth = ViewGroup.LayoutParams.MATCH_PARENT;
    int LayoutHeight = ViewGroup.LayoutParams.MATCH_PARENT;
    int LayoutWidthPreView = ViewGroup.LayoutParams.MATCH_PARENT;
    int LayoutHeightPreView = ViewGroup.LayoutParams.MATCH_PARENT;
    String aliasName;
    boolean isEqualColumnChecked;
    int nooFColumns;
    String backgroundType;
    String backgroundColorHex;
    int backgroundColor;
    String backgroundImage;
    String columnBackgroundType;
    String columnBackgroundColorHex;
    int columnBackgroundColor;
    String columnBackgroundImage;
    boolean isDefaultColor;
    boolean isColumnDefaultColor;

    int paddingLeft = 0;
    int paddingRight = 0;
    int paddingTop = 0;
    int paddingBottom = 0;

    int marginLeft = 0;
    int marginRight = 0;
    int marginTop = 0;
    int marginBottom = 0;

    int paddingLeftColumn = 0;
    int paddingRightColumn = 0;
    int paddingTopColumn = 0;
    int paddingBottomColumn = 0;

    int marginLeftColumn = 0;
    int marginRightColumn = 0;
    int marginTopColumn = 0;
    int marginBottomColumn = 0;

    List<MappingControlModel> subMappedControlsList;//Mapped Controls
    List<SubLayoutsModelClass> columnsModelList;//sub control
    List<MappingControlModel> controlNamesList;


    String gradientType;
    String gradientOneColorHex;
    String gradientTwoColorHex;
    int gradientOneBackgroundColor;
    int gradientTwoBackgroundColor;
    Float gradientCornerRadius;
    int stroke = 0;
    String strokeColor = "#FFFFFF";
    String subLayoutAlignment;

    String gradientTypeColumn;
    String gradientOneColorHexColumn;
    String gradientTwoColorHexColumn;
    int gradientOneBackgroundColorColumn;
    int gradientTwoBackgroundColorColumn;
    Float gradientCornerRadiusColumn;
    int strokeColumn = 0;
    String strokeColorColumn = "#FFFFFF";
    String ColumnLayoutAlignment;
    String layoutDataAppearance = "only_wrap_content";

//    List<ColumnsModel> columnsModelList;

    public SubLayoutsModelClass() {
    }

    public SubLayoutsModelClass(int position, int tag, int childCount) {
        this.position = position;
        this.tag = tag;
        this.childCount = childCount;
    }

    public String getControlName() {
        return ControlName;
    }

    public void setControlName(String controlName) {
        ControlName = controlName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public List<SubLayoutsModelClass> getColumnsModelList() {
        return columnsModelList;
    }

    public void setColumnsModelList(List<SubLayoutsModelClass> columnsModelList) {
        this.columnsModelList = columnsModelList;
    }

    public int getParentPosition() {
        return parentPosition;
    }

    public void setParentPosition(int parentPosition) {
        this.parentPosition = parentPosition;
    }

    public int getLayoutWidth() {
        return LayoutWidth;
    }

    public void setLayoutWidth(int layoutWidth) {
        LayoutWidth = layoutWidth;
    }

    public int getLayoutHeight() {
        return LayoutHeight;
    }

    public void setLayoutHeight(int layoutHeight) {
        LayoutHeight = layoutHeight;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

//    public List<ColumnsModel> getColumnsModelList() {
//        return columnsModelList;
//    }
//
//    public void setColumnsModelList(List<ColumnsModel> columnsModelList) {
//        this.columnsModelList = columnsModelList;
//    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isEqualColumnChecked() {
        return isEqualColumnChecked;
    }

    public void setEqualColumnChecked(boolean equalColumnChecked) {
        isEqualColumnChecked = equalColumnChecked;
    }

    public Float getfWidth() {
        return fWidth;
    }

    public void setfWidth(Float fWidth) {
        this.fWidth = fWidth;
    }

    public int getNooFColumns() {
        return nooFColumns;
    }

    public void setNooFColumns(int nooFColumns) {
        this.nooFColumns = nooFColumns;
    }

    public String getBackgroundType() {
        return backgroundType;
    }

    public void setBackgroundType(String backgroundType) {
        this.backgroundType = backgroundType;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getBackgroundColorHex() {
        return backgroundColorHex;
    }

    public void setBackgroundColorHex(String backgroundColorHex) {
        this.backgroundColorHex = backgroundColorHex;
    }

    public List<MappingControlModel> getControlNamesList() {
        return controlNamesList;
    }

    public void setControlNamesList(List<MappingControlModel> controlNamesList) {
        this.controlNamesList = controlNamesList;
    }

    public String getColumnBackgroundType() {
        return columnBackgroundType;
    }

    public void setColumnBackgroundType(String columnBackgroundType) {
        this.columnBackgroundType = columnBackgroundType;
    }

    public String getColumnBackgroundColorHex() {
        return columnBackgroundColorHex;
    }

    public void setColumnBackgroundColorHex(String columnBackgroundColorHex) {
        this.columnBackgroundColorHex = columnBackgroundColorHex;
    }

    public int getColumnBackgroundColor() {
        return columnBackgroundColor;
    }

    public void setColumnBackgroundColor(int columnBackgroundColor) {
        this.columnBackgroundColor = columnBackgroundColor;
    }

    public String getColumnBackgroundImage() {
        return columnBackgroundImage;
    }

    public void setColumnBackgroundImage(String columnBackgroundImage) {
        this.columnBackgroundImage = columnBackgroundImage;
    }

    public List<MappingControlModel> getSubMappedControlsList() {
        return subMappedControlsList;
    }

    public void setSubMappedControlsList(List<MappingControlModel> subMappedControlsList) {
        this.subMappedControlsList = subMappedControlsList;
    }

    public int getLayoutWidthPreView() {
        return LayoutWidthPreView;
    }

    public void setLayoutWidthPreView(int layoutWidthPreView) {
        LayoutWidthPreView = layoutWidthPreView;
    }

    public int getLayoutHeightPreView() {
        return LayoutHeightPreView;
    }

    public void setLayoutHeightPreView(int layoutHeightPreView) {
        LayoutHeightPreView = layoutHeightPreView;
    }

    public boolean isDefaultColor() {
        return isDefaultColor;
    }

    public void setDefaultColor(boolean defaultColor) {
        isDefaultColor = defaultColor;
    }

    public boolean isColumnDefaultColor() {
        return isColumnDefaultColor;
    }

    public void setColumnDefaultColor(boolean columnDefaultColor) {
        isColumnDefaultColor = columnDefaultColor;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public String getSubLayoutAlignment() {
        return subLayoutAlignment;
    }

    public void setSubLayoutAlignment(String subLayoutAlignment) {
        this.subLayoutAlignment = subLayoutAlignment;
    }

    public String getGradientType() {
        return gradientType;
    }

    public void setGradientType(String gradientType) {
        this.gradientType = gradientType;
    }

    public String getGradientOneColorHex() {
        return gradientOneColorHex;
    }

    public void setGradientOneColorHex(String gradientOneColorHex) {
        this.gradientOneColorHex = gradientOneColorHex;
    }

    public String getGradientTwoColorHex() {
        return gradientTwoColorHex;
    }

    public void setGradientTwoColorHex(String gradientTwoColorHex) {
        this.gradientTwoColorHex = gradientTwoColorHex;
    }

    public int getGradientOneBackgroundColor() {
        return gradientOneBackgroundColor;
    }

    public void setGradientOneBackgroundColor(int gradientOneBackgroundColor) {
        this.gradientOneBackgroundColor = gradientOneBackgroundColor;
    }

    public int getGradientTwoBackgroundColor() {
        return gradientTwoBackgroundColor;
    }

    public void setGradientTwoBackgroundColor(int gradientTwoBackgroundColor) {
        this.gradientTwoBackgroundColor = gradientTwoBackgroundColor;
    }

    public Float getGradientCornerRadius() {
        return gradientCornerRadius;
    }

    public void setGradientCornerRadius(Float gradientCornerRadius) {
        this.gradientCornerRadius = gradientCornerRadius;
    }

    public int getStroke() {
        return stroke;
    }

    public void setStroke(int stroke) {
        this.stroke = stroke;
    }

    public String getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(String strokeColor) {
        this.strokeColor = strokeColor;
    }

    public int getPaddingLeftColumn() {
        return paddingLeftColumn;
    }

    public void setPaddingLeftColumn(int paddingLeftColumn) {
        this.paddingLeftColumn = paddingLeftColumn;
    }

    public int getPaddingRightColumn() {
        return paddingRightColumn;
    }

    public void setPaddingRightColumn(int paddingRightColumn) {
        this.paddingRightColumn = paddingRightColumn;
    }

    public int getPaddingTopColumn() {
        return paddingTopColumn;
    }

    public void setPaddingTopColumn(int paddingTopColumn) {
        this.paddingTopColumn = paddingTopColumn;
    }

    public int getPaddingBottomColumn() {
        return paddingBottomColumn;
    }

    public void setPaddingBottomColumn(int paddingBottomColumn) {
        this.paddingBottomColumn = paddingBottomColumn;
    }

    public int getMarginLeftColumn() {
        return marginLeftColumn;
    }

    public void setMarginLeftColumn(int marginLeftColumn) {
        this.marginLeftColumn = marginLeftColumn;
    }

    public int getMarginRightColumn() {
        return marginRightColumn;
    }

    public void setMarginRightColumn(int marginRightColumn) {
        this.marginRightColumn = marginRightColumn;
    }

    public int getMarginTopColumn() {
        return marginTopColumn;
    }

    public void setMarginTopColumn(int marginTopColumn) {
        this.marginTopColumn = marginTopColumn;
    }

    public int getMarginBottomColumn() {
        return marginBottomColumn;
    }

    public void setMarginBottomColumn(int marginBottomColumn) {
        this.marginBottomColumn = marginBottomColumn;
    }

    public String getGradientTypeColumn() {
        return gradientTypeColumn;
    }

    public void setGradientTypeColumn(String gradientTypeColumn) {
        this.gradientTypeColumn = gradientTypeColumn;
    }

    public String getGradientOneColorHexColumn() {
        return gradientOneColorHexColumn;
    }

    public void setGradientOneColorHexColumn(String gradientOneColorHexColumn) {
        this.gradientOneColorHexColumn = gradientOneColorHexColumn;
    }

    public String getGradientTwoColorHexColumn() {
        return gradientTwoColorHexColumn;
    }

    public void setGradientTwoColorHexColumn(String gradientTwoColorHexColumn) {
        this.gradientTwoColorHexColumn = gradientTwoColorHexColumn;
    }

    public int getGradientOneBackgroundColorColumn() {
        return gradientOneBackgroundColorColumn;
    }

    public void setGradientOneBackgroundColorColumn(int gradientOneBackgroundColorColumn) {
        this.gradientOneBackgroundColorColumn = gradientOneBackgroundColorColumn;
    }

    public int getGradientTwoBackgroundColorColumn() {
        return gradientTwoBackgroundColorColumn;
    }

    public void setGradientTwoBackgroundColorColumn(int gradientTwoBackgroundColorColumn) {
        this.gradientTwoBackgroundColorColumn = gradientTwoBackgroundColorColumn;
    }

    public Float getGradientCornerRadiusColumn() {
        return gradientCornerRadiusColumn;
    }

    public void setGradientCornerRadiusColumn(Float gradientCornerRadiusColumn) {
        this.gradientCornerRadiusColumn = gradientCornerRadiusColumn;
    }

    public int getStrokeColumn() {
        return strokeColumn;
    }

    public void setStrokeColumn(int strokeColumn) {
        this.strokeColumn = strokeColumn;
    }

    public String getStrokeColorColumn() {
        return strokeColorColumn;
    }

    public void setStrokeColorColumn(String strokeColorColumn) {
        this.strokeColorColumn = strokeColorColumn;
    }

    public String getColumnLayoutAlignment() {
        return ColumnLayoutAlignment;
    }

    public void setColumnLayoutAlignment(String columnLayoutAlignment) {
        ColumnLayoutAlignment = columnLayoutAlignment;
    }

    public int getSubLayoutId() {
        return subLayoutId;
    }

    public void setSubLayoutId(int subLayoutId) {
        this.subLayoutId = subLayoutId;
    }

    public String getLayoutDataAppearance() {
        return layoutDataAppearance;
    }

    public void setLayoutDataAppearance(String layoutDataAppearance) {
        this.layoutDataAppearance = layoutDataAppearance;
    }
}
