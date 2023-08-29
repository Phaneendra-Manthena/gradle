package com.bhargo.user.controls.variables;

import com.bhargo.user.Java_Beans.Item;

import java.util.List;

//CheckBox,RadioButton,DropDown
public interface SelectVariables {
    //selectedItem(Object-ID,Name),visible,enable,clear
    int VISIBLE = 0;
    int GONE = 8;
    Item getSelectedItem();

    void setSelectedItem(Item selectedItem);

    int getVisibility();

    void setVisibility(boolean visibility);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    void clean();

    void loadItems(List<Item> items);

    void appendItems(List<Item> items);

    boolean isSortByAlphabetOrder();

    void setSortByAlphabetOrder(boolean enabled);

    boolean isAllowOtherChoices();

    void setAllowOtherChoices(boolean enabled);

    void showMessageBelowControl(String msg);

   /* class Item {
        String id;
        String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }*/
}
