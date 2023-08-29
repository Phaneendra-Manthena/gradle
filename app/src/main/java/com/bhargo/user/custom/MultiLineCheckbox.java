package com.bhargo.user.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.bhargo.user.R;

import java.util.ArrayList;
import java.util.List;

public class MultiLineCheckbox extends LinearLayout {

    private static final String XML_DEFAULT_BUTTON_PREFIX_INDEX = "index:";
    private static final String XML_DEFAULT_BUTTON_PREFIX_TEXT = "text:";

    private static final int DEF_VAL_MAX_IN_ROW = 2;

    private OnCheckedChangeListener mOnCheckedChangeListener;

    private int mMaxInRow;

    // all buttons are stored in table layout
    private TableLayout mTableLayout;

    // list to store all the buttons
    private List<CheckBox> mRadioButtons;

    // the checked button
    private CheckBox checkedButton;

    /**
     * Creates a new MultiLineRadioGroup for the given context.
     *
     * @param context the application environment
     */
    public MultiLineCheckbox(Context context) {
        super(context);
        init(null);
    }

    /**
     * Creates a new MultiLineRadioGroup for the given context
     * and with the specified set attributes.
     *
     * @param context the application environment
     * @param attrs   a collection of attributes
     */
    public MultiLineCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    // initializes the layout
    private void init(AttributeSet attrs) {
        mRadioButtons = new ArrayList<>();

        mTableLayout = getTableLayout();
        addView(mTableLayout);

        if (attrs != null)
            initAttrs(attrs);
    }

    // initializes the layout with the specified attributes
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.multi_line_radio_group,
                0, 0);
        try {
            // gets and sets the max in row.
            setMaxInRow(typedArray.getInt(R.styleable.multi_line_radio_group_max_in_row,
                    DEF_VAL_MAX_IN_ROW));

            // gets and adds the starting buttons
            CharSequence[] radioButtonStrings = typedArray.getTextArray(
                    R.styleable.multi_line_radio_group_radio_buttons);
            addButtons(radioButtonStrings);

            // gets the default button and checks it if presents.
            String string = typedArray.getString(R.styleable.multi_line_radio_group_default_button);
            if (string != null)
                setDefaultButton(string);

        } finally {
            typedArray.recycle();
        }
    }

    // checks the default button based on the passed string
    private void setDefaultButton(String string) {
        final int START_OF_INDEX = 6;
        final int START_OF_TEXT = 5;

        // the text of the button to check
        String buttonToCheck;

        if (string.startsWith(XML_DEFAULT_BUTTON_PREFIX_INDEX)) {
            String indexString = string.substring(START_OF_INDEX, string.length());
            int index = Integer.parseInt(indexString);
            if (index < 0 || index >= mRadioButtons.size())
                throw new IllegalArgumentException("index must be between 0 to getRadioButtonCount() - 1 [" +
                        (getRadioButtonCount() - 1) + "]");
            buttonToCheck = mRadioButtons.get(index).getText().toString();

        } else if (string.startsWith(XML_DEFAULT_BUTTON_PREFIX_TEXT)) {
            buttonToCheck = string.substring(START_OF_TEXT, string.length());

        } else { // when there is no prefix assumes the string is the text of the button
            buttonToCheck = string;
        }

        check(buttonToCheck);
    }

    /**
     * Returns the table layout to set to this layout.
     *
     * @return the table layout
     */
    protected TableLayout getTableLayout() {
        return (TableLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.table_layout, this, false);
    }

    /**
     * Returns the table row to set in this layout.
     *
     * @return the table row
     */
    protected TableRow getTableRow() {
        return (TableRow) LayoutInflater.from(getContext())
                .inflate(R.layout.table_row, mTableLayout, false);
    }

    /**
     * Returns the radio button to set in this layout.
     *
     * @return the radio button
     */
    protected CheckBox getRadioButton() {
        return (CheckBox) LayoutInflater.from(getContext())
                .inflate(R.layout.item_checkbox, null);
    }

    /**
     * Register a callback to be invoked when a radio button is checked.
     *
     * @param onCheckedChangeListener the listener to attach
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    /**
     * Sets the maximum radio buttons in a row, 0 for all in one line
     * and arranges the layout accordingly.
     *
     * @param maxInRow the maximum radio buttons in a row
     * @throws IllegalArgumentException if maxInRow is negative
     */
    public void setMaxInRow(int maxInRow) {
        if (maxInRow < 0)
            throw new IllegalArgumentException("maxInRow must not be negative");
        this.mMaxInRow = maxInRow;
        arrangeButtons();
    }

    /**
     * Adds a view to the layout
     * <p>
     * Consider using addButtons() instead
     *
     * @param child the view to add
     */
    @Override
    public void addView(View child) {
        addView(child, -1, child.getLayoutParams());
    }

    /**
     * Adds a view to the layout in the specified index
     * <p>
     * Consider using addButtons() instead
     *
     * @param child the view to add
     * @param index the index in which to insert the view
     */
    @Override
    public void addView(View child, int index) {
        addView(child, index, child.getLayoutParams());
    }

    /**
     * Adds a view to the layout with the specified width and height.
     * Note that for radio buttons the width and the height are ignored.
     * <p>
     * Consider using addButtons() instead
     *
     * @param child  the view to add
     * @param width  the width of the view
     * @param height the height of the view
     */
    @Override
    public void addView(View child, int width, int height) {
        addView(child, -1, new LinearLayout.LayoutParams(width, height));
    }

    /**
     * Adds a view to the layout with the specified layout params.
     * Note that for radio buttons the params are ignored.
     * <p>
     * Consider using addButtons() instead
     *
     * @param child  the view to add
     * @param params the layout params of the view
     */
    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        addView(child, -1, params);
    }

    /**
     * Adds a view to the layout in the specified index
     * with the specified layout params.
     * Note that for radio buttons the params are ignored.
     * <p>
     * * Consider using addButtons() instead
     *
     * @param child  the view to add
     * @param index  the index in which to insert the view
     * @param params the layout params of the view
     */
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (params == null) {
            params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

        if (child instanceof RadioButton)
            addButtons(index, ((RadioButton) child).getText());
        else
            super.addView(child, index, params);
    }

    /**
     * Adds radio buttons to the layout based on the texts in the radioButtons array.
     * Adds them in the last index.
     * If radioButtons is null does nothing.
     *
     * @param radioButtons the texts of the buttons to add
     */
    public void addButtons(CharSequence... radioButtons) {
        addButtons(-1, radioButtons);
    }

    /**
     * Adds radio buttons to the layout based on the texts in the radioButtons array.
     * Adds them in the specified index, -1 for the last index.
     * If radioButtons is null does nothing.
     *
     * @param index        the index in which to insert the radio buttons
     * @param radioButtons the texts of the buttons to add
     * @throws IllegalArgumentException if index is less than -1 or greater than the number of radio buttons
     */
    public void addButtons(int index, CharSequence... radioButtons) {
        if (index < -1 || index > mRadioButtons.size())
            throw new IllegalArgumentException("index must be between -1 to getRadioButtonCount() [" +
                    getRadioButtonCount() + "]");

        if (radioButtons == null)
            return;

        int realIndex = (index != -1) ? index : mRadioButtons.size();

        // adds the buttons to the list
        for (CharSequence text : radioButtons)
            mRadioButtons.add(realIndex++, createRadioButton(text));

        arrangeButtons();
    }

    // creates a radio button with the specified text
    private CheckBox createRadioButton(CharSequence text) {
        CheckBox radioButton = getRadioButton();
        radioButton.setText(text);
        radioButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton((CheckBox) v);

//                if (mOnCheckedChangeListener != null)
//                    mOnCheckedChangeListener.onCheckedChanged(MultiLineCheckbox.this, checkedButton);
            }
        });
        return radioButton;
    }

    /**
     * Removes a view from the layout.
     * <p>
     * Consider using removeButton().
     *
     * @param view the view to remove
     */
    @Override
    public void removeView(View view) {
        super.removeView(view);
    }

    /**
     * Removes a view from the layout in the specified index.
     * <p>
     * Consider using removeButton().
     *
     * @param index the index from which to remove the view
     */
    @Override
    public void removeViewAt(int index) {
        super.removeViewAt(index);
    }

    /**
     * Removes the specified range of views from the layout.
     * <p>
     * Consider using removeButtons().
     *
     * @param start the start index to remove
     * @param count the number of views to remove
     */
    @Override
    public void removeViews(int start, int count) {
        super.removeViews(start, count);
    }

    /**
     * Removes all the views from the layout.
     * <p>
     * Consider using removeAllButtons().
     */
    @Override
    public void removeAllViews() {
        super.removeAllViews();
    }

    /**
     * Removes a radio button from the layout.
     * If the radio button is null does nothing.
     *
     * @param radioButton the radio button to remove
     */
    public void removeButton(RadioButton radioButton) {
        if (radioButton == null)
            return;

        removeButton(radioButton.getText());
    }

    /**
     * Removes a radio button from the layout based on its text.
     * Removes the first occurrence.
     * If the text is null does nothing.
     *
     * @param text the text of the radio button to remove
     */
    public void removeButton(CharSequence text) {
        if (text == null)
            return;

        int index = -1;

        for (int i = 0, len = mRadioButtons.size(); i < len; i++) {
            // checks if the texts are equal
            if (mRadioButtons.get(i).getText().equals(text)) {
                index = i;
                break;
            }
        }

        // removes just if the index was found
        if (index != -1)
            removeButton(index);
    }

    /**
     * Removes the radio button in the specified index from the layout.
     *
     * @param index the index from which to remove the radio button
     * @throws IllegalArgumentException if index is less than 0
     *                                  or greater than the number of radio buttons - 1
     */
    public void removeButton(int index) {
        removeButtons(index, 1);
    }

    /**
     * Removes all the radio buttons in the specified range from the layout.
     * Count can be any non-negative number.
     *
     * @param start the start index to remove
     * @param count the number of radio buttons to remove
     * @throws IllegalArgumentException if index is less than 0
     *                                  or greater than the number of radio buttons - 1
     *                                  or count is negative
     */
    public void removeButtons(int start, int count) {
        if (start < 0 || start >= mRadioButtons.size())
            throw new IllegalArgumentException("remove index must be between 0 to getRadioButtonCount() - 1 [" +
                    (getRadioButtonCount() - 1) + "]");

        if (count < 0)
            throw new IllegalArgumentException("count must not be negative");

        if (count == 0)
            return;

        int endIndex = start + count - 1;
        // if endIndex is not in the range of the radio buttons sets it to the last index
        if (endIndex >= mRadioButtons.size())
            endIndex = mRadioButtons.size() - 1;

        // iterates over the buttons to remove
        for (int i = endIndex; i >= start; i--) {
            CheckBox radiobutton = mRadioButtons.get(i);
            // if the button to remove is the checked button set checkedButton to null
            if (radiobutton == checkedButton)
                checkedButton = null;
            // removes the button from the list
            mRadioButtons.remove(i);
        }

        arrangeButtons();
    }

    /**
     * Removes all the radio buttons from the layout.
     */
    public void removeAllButtons() {
        removeButtons(0, mRadioButtons.size());
    }

    // arrange the button in the layout
    private void arrangeButtons() {
        // iterates over each button and puts it in the right place
        for (int i = 0, len = mRadioButtons.size(); i < len; i++) {
            CheckBox radioButtonToPlace = mRadioButtons.get(i);
            int rowToInsert = (mMaxInRow != 0) ? i / mMaxInRow : 0;
            int columnToInsert = (mMaxInRow != 0) ? i % mMaxInRow : i;
            // gets the row to insert. if there is no row create one
            TableRow tableRowToInsert = (mTableLayout.getChildCount() <= rowToInsert)
                    ? addTableRow() : (TableRow) mTableLayout.getChildAt(rowToInsert);
            int tableRowChildCount = tableRowToInsert.getChildCount();

            // if there is already a button in the position
            if (tableRowChildCount > columnToInsert) {
                CheckBox currentButton = (CheckBox) tableRowToInsert.getChildAt(columnToInsert);

                // insert the button just if the current button is different
                if (currentButton != radioButtonToPlace) {
                    // removes the current button
                    removeButtonFromParent(currentButton, tableRowToInsert);
                    // removes the button to place from its current position
                    removeButtonFromParent(radioButtonToPlace, (ViewGroup) radioButtonToPlace.getParent());
                    // adds the button to the right place
                    tableRowToInsert.addView(radioButtonToPlace, columnToInsert);
                }

                // if there isn't already a button in the position
            } else {
                // removes the button to place from its current position
                removeButtonFromParent(radioButtonToPlace, (ViewGroup) radioButtonToPlace.getParent());
                // adds the button to the right place
                tableRowToInsert.addView(radioButtonToPlace, columnToInsert);
            }
        }

        removeRedundancies();
    }

    // removes the redundant rows and radio buttons
    private void removeRedundancies() {
        // the number of rows to fit the buttons
        int rows;
        if (mRadioButtons.size() == 0)
            rows = 0;
        else if (mMaxInRow == 0)
            rows = 1;
        else
            rows = (mRadioButtons.size() - 1) / mMaxInRow + 1;

        int tableChildCount = mTableLayout.getChildCount();
        // if there are redundant rows remove them
        if (tableChildCount > rows)
            mTableLayout.removeViews(rows, tableChildCount - rows);

        tableChildCount = mTableLayout.getChildCount();
        int maxInRow = (mMaxInRow != 0) ? mMaxInRow : mRadioButtons.size();

        // iterates over the rows
        for (int i = 0; i < tableChildCount; i++) {
            TableRow tableRow = (TableRow) mTableLayout.getChildAt(i);
            int tableRowChildCount = tableRow.getChildCount();

            int startIndexToRemove;
            int count;

            // if it is the last row removes all redundancies after the last button in the list
            if (i == tableChildCount - 1) {
                startIndexToRemove = (mRadioButtons.size() - 1) % maxInRow + 1;
                count = tableRowChildCount - startIndexToRemove;

                // if it is not the last row removes all the buttons after maxInRow position
            } else {
                startIndexToRemove = maxInRow;
                count = tableRowChildCount - maxInRow;
            }

            if (count > 0)
                tableRow.removeViews(startIndexToRemove, count);
        }
    }

    // adds and returns a table row
    private TableRow addTableRow() {
        TableRow tableRow = getTableRow();
        mTableLayout.addView(tableRow);
        return tableRow;
    }

    // removes a radio button from a parent
    private void removeButtonFromParent(CheckBox radioButton, ViewGroup parent) {
        if (radioButton == null || parent == null)
            return;

        parent.removeView(radioButton);
    }

    /**
     * Returns the number of radio buttons.
     *
     * @return the number of radio buttons
     */
    public int getRadioButtonCount() {
        return mRadioButtons.size();
    }

    /**
     * Returns the radio button in the specified index.
     * If the index is out of range returns null.
     *
     * @param index the index of the radio button
     * @return the radio button
     */
    public CheckBox getRadioButtonAt(int index) {
        if (index < 0 || index >= mRadioButtons.size())
            return null;

        return mRadioButtons.get(index);
    }

    /**
     * Checks the radio button with the specified id.
     * If the specified id is not found does nothing.
     *
     * @param id the radio button's id
     */
   /* @SuppressLint("ResourceType")
    @Override
    public void check(int id) {
        if (id <= 0)
            return;

        for (CheckBox radioButton : mRadioButtons) {
            if (radioButton.getId() == id) {
                checkButton(radioButton);
                return;
            }
        }
    }*/

    /**
     * Checks the radio button with the specified text.
     * If there is more than one radio button associated with this text
     * checks the first radio button.
     * If the specified text is not found does nothing.
     *
     * @param text the radio button's text
     */
    public void check(CharSequence text) {
        if (text == null)
            return;

        for (CheckBox radioButton : mRadioButtons) {
            if (radioButton.getText().equals(text)) {
                checkButton(radioButton);
                return;
            }
        }
    }

    /**
     * Checks the radio button at the specified index.
     * If the specified index is invalid does nothing.
     *
     * @param index the radio button's index
     */
    public void checkAt(int index) {
        if (index < 0 || index >= mRadioButtons.size())
            return;

        checkButton(mRadioButtons.get(index));
    }

    // checks and switches the button with the checkedButton
    private void checkButton(CheckBox button) {
        if (button == null)
            return;

        // if the button to check is different from the current checked button
        if (button != checkedButton) {

            // if exists sets checkedButton to null
            if (checkedButton != null)
                checkedButton.setChecked(false);

            button.setChecked(true);
            checkedButton = button;
        }
    }

  /*  *//**
     * Clears the checked radio button
     *//*
    @Override
    public void clearCheck() {
        checkedButton.setChecked(false);
        checkedButton = null;
    }

    *//**
     * Returns the checked radio button's id.
     * If no radio buttons are checked returns -1.
     *
     * @return the checked radio button's id
     *//*
    @Override
    public int getCheckedRadioButtonId() {
        if (checkedButton == null)
            return -1;

        return checkedButton.getId();
    }*/

    /**
     * Returns the checked radio button's index.
     * If no radio buttons are checked returns -1.
     *
     * @return the checked radio button's index
     */
    public int getCheckedRadioButtonIndex() {
        if (checkedButton == null)
            return -1;

        return mRadioButtons.indexOf(checkedButton);
    }

    /**
     * Returns the checked radio button's text.
     * If no radio buttons are checked returns null.
     *
     * @return the checked radio buttons's text
     */
    public CharSequence getCheckedRadioButtonText() {
        if (checkedButton == null)
            return null;

        return checkedButton.getText();
    }

    /**
     * Interface definition for a callback to be invoked when a radio button is checked.
     */
    public interface OnCheckedChangeListener {
        /**
         * Called when a radio button is checked.
         *
         * @param group  the MultiLineRadioGroup that stores the radio button
         * @param button the radio button that was checked
         */
        void onCheckedChanged(MultiLineCheckbox group, RadioButton button);
    }
}
