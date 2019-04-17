package com.andela.art.incidentreport.presentation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatEditText;

public class ArtEditText extends AppCompatEditText {


    /**
     * Constructor for Art EditText class.
     *
     * @param context - Context
     * @param attrs - AttributeSet
     */
    public ArtEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setError(CharSequence error, Drawable icon) {
        if (error == null) {
            super.setError(null, icon);
            setCompoundDrawables(null, null, null, null);
        }
        else if (error.toString().equals("")) {
            setCompoundDrawables(null, null, icon, null);
        }
        else {
            super.setError(error, icon);
        }
    }
}
