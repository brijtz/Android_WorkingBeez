package com.app.workingbeez.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.app.workingbeez.R;

/**
 * Created by divyeshshani on 12/09/16.
 */
public class DTextView extends TextView {

    public DTextView(Context context) {
        super(context);

//        TextViewHelper.setTypeface(context, this);
    }

    public DTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TextViewHelper.setTypeface(context, this, attrs);
    }

    public DTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TextViewHelper.setTypeface(context, this, attrs);
    }

    public static class TextViewHelper {

        private static Typeface typeface = null;
        private static int type;

        public static void setTypeface(Context context, TextView textView, AttributeSet attrs) {

            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DTextView);
            try {
                type = ta.getInt(R.styleable.DTextView_textFontFace, 1);

                switch (type) {

                    case 1:

                        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/opensans_regular.ttf");
                        break;

                    case 2:

                        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/opensans_semi_bold.ttf");
                        break;

                    case 3:


                        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/opensans_light.ttf");
                        break;

                    case 4:

                        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/opensans_bold.ttf");
                        break;

                    default:
                        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
                        break;

                }


            } finally {
                ta.recycle();
            }


            textView.setTypeface(typeface);
        }

        public static void setTypeface(Context context, TextView textView) {

            if (typeface == null) {

                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
            }

            textView.setTypeface(typeface);
        }

    }
}
