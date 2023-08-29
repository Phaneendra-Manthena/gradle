package com.bhargo.user.controls.standard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.bhargo.user.R;

public class TextEditor {

    private final Context context;

    public TextEditor(Context context) {

        this.context = context;
    }

    private void initView(){

        LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rView = lInflater.inflate(R.layout.control_text_editor, null);

    }
}
