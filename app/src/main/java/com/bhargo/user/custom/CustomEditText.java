package com.bhargo.user.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

import com.bhargo.user.R;


/**
 * Created by Peritus on 10/24/2016.
 */
public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText {
    private static final String TAG = "EditText";

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String customFont = a.getString(R.styleable.CustomTextView_customFont);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface tf = null;
        try {
            if(asset.length()>0){
                tf = Typeface.createFromAsset(ctx.getAssets(), asset);
            }else{
                tf = Typeface.createFromAsset(ctx.getAssets(), ctx.getString(R.string.font_name));
            }
        } catch (Exception e) {
            Log.e(TAG, "Could not get typeface: "+e.getMessage());
            return false;
        }

        setTypeface(tf);
        return true;
    }



}
