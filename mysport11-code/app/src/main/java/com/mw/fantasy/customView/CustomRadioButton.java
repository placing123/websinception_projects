package com.mw.fantasy.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.mw.fantasy.R;

/**
 * Created by mobiweb on 31/7/17.
 */

public class CustomRadioButton extends android.support.v7.widget.AppCompatRadioButton {

    private Typeface tf = null;
    private String customFont;

    public CustomRadioButton(Context context) {
        super(context);
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFontTextView(context, attrs);
    }

    public CustomRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFontTextView(context, attrs);
    }


    public boolean setCustomFontTextView(Context ctx, String asset) {
        try {
            if (asset.equals("bold")) {
                tf = Typeface.createFromAsset(ctx.getAssets(), "Lato-Bold.ttf");
            } else {
                tf = Typeface.createFromAsset(ctx.getAssets(), "ProximaNovaRegular.ttf");
            }
        } catch (Exception e) {
            return false;
        }
        setTypeface(tf);
        return true;
    }

    private void setCustomFontTextView(Context ctx, AttributeSet attrs) {
        final TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomEditText);
        customFont = a.getString(R.styleable.CustomEditText_textfont);
        setCustomFontTextView(ctx, customFont);
        a.recycle();
    }

}
