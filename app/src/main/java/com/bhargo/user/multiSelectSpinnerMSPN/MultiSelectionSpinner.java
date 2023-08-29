package com.bhargo.user.multiSelectSpinnerMSPN;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;

import com.bhargo.user.pojos.TaskAppDataModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MultiSelectionSpinner  extends AppCompatSpinner implements DialogInterface.OnMultiChoiceClickListener {


    List<TaskAppDataModel> items;

    boolean[] selection = null;
    ArrayAdapter adapter;
    Context context;



    public MultiSelectionSpinner(Context context) {

        super(context);



        adapter = new ArrayAdapter(context,

                android.R.layout.simple_spinner_item);

        super.setAdapter(adapter);

    }



    public MultiSelectionSpinner(Context context, AttributeSet attrs) {

        super(context, attrs);



        adapter = new ArrayAdapter(context,

                android.R.layout.simple_spinner_item);

        super.setAdapter(adapter);

    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (selection != null && which < selection.length) {

            selection[which] = isChecked;

            adapter.clear();

            adapter.add(buildSelectedItemString());

        } else {

            throw new IllegalArgumentException(

                    "Argument 'which' is out of bounds.");

        }
    }

    @Override

    public boolean performClick() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if(items != null) {
            String[] itemNames = new String[items.size()];


            for (int i = 0; i < items.size(); i++) {

                itemNames[i] = items.get(i).getACName();

            }


            builder.setMultiChoiceItems(itemNames, selection, this);

        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface arg0, int arg1)

            {

                // Do nothing

            }

        });



        builder.show();



        return true;

    }
    @Override

    public void setAdapter(SpinnerAdapter adapter) {

        throw new RuntimeException(

                "setAdapter is not supported by MultiSelectSpinner.");

    }



    public void setItems(List<TaskAppDataModel> items) {

        this.items = items;

        selection = new boolean[this.items.size()];

        adapter.clear();

        adapter.add("");

        Arrays.fill(selection, false);

    }



    public void setSelection(List<TaskAppDataModel> selection) {

        for (int i = 0; i < this.selection.length; i++) {

            this.selection[i] = false;

        }



        for (TaskAppDataModel sel : selection) {

            for (int j = 0; j < items.size(); ++j) {

                if (items.get(j).getACName().equals(sel.getACName())) {

                    this.selection[j] = true;

                }

            }

        }



        adapter.clear();

        adapter.add(buildSelectedItemString());

    }



    private String buildSelectedItemString() {

        StringBuilder sb = new StringBuilder();

        boolean foundOne = false;

        for (int i = 0; i < items.size(); ++i) {

            if (selection[i]) {

                if (foundOne) {

                    sb.append(", ");

                }

                foundOne = true;

                sb.append(items.get(i).getACName());

            }

        }

        removeDuplicates(items);

        return sb.toString();

    }



    public List<TaskAppDataModel> getSelectedItems() {

        List<TaskAppDataModel> selectedItems = new ArrayList<>();



        for (int i = 0; i < items.size(); ++i) {

            if (selection[i]) {

                selectedItems.add(items.get(i));

            }

        }



        return selectedItems;

    }
    public static <T> List<T> removeDuplicates(List<T> list) {

        // Create a new LinkedHashSet
        Set<T> set = new LinkedHashSet<>();

        // Add the elements to set
        set.addAll(list);

        // Clear the list
        list.clear();

        // add the elements of set
        // with no duplicates to the list
        list.addAll(set);

        // return the list
        return list;
    }

}

