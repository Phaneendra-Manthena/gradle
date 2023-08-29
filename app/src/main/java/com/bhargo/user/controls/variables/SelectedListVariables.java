package com.bhargo.user.controls.variables;

import com.bhargo.user.Java_Beans.Item;

import java.util.List;

//CheckList
public interface SelectedListVariables {
    //listOfSelectedItems(Object-ID,Name),visible,enable,clear
    int VISIBLE = 0;
    int GONE = 1;

    List<Item> getListOfSelectedItems();

    void setListOfSelectedItems(List<Item> listOfSelectedItems);

    void loadItems(List<Item> items);

    int getVisibility();

    void setVisibility(boolean visibility);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    void clean();

    boolean isSortByAlphabetOrder();

    void setSortByAlphabetOrder(boolean enabled);

    boolean isAllowOtherChoices();

    void setAllowOtherChoices(boolean enabled);

    void showMessageBelowControl(String msg);


}
