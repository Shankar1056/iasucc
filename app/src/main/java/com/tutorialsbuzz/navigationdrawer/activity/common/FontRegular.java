package com.tutorialsbuzz.navigationdrawer.activity.common;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by igcs-27 on 20/2/16.
 */
    public class FontRegular extends TextView{

    public FontRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/raleway.regular.ttf"));
    }
}
