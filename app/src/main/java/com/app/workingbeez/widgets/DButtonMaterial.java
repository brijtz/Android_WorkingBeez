package com.app.workingbeez.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.app.workingbeez.R;


public class DButtonMaterial extends com.rey.material.widget.Button {

    public DButtonMaterial(Context context) {
        super(context);

        ButtonHelper.setTypeface(context, this);
    }

    public DButtonMaterial(Context context, AttributeSet attrs) {
        super(context, attrs);

        ButtonHelper.setTypeface(context, this, attrs);
    }

    public DButtonMaterial(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        ButtonHelper.setTypeface(context, this, attrs);
    }

    public static class ButtonHelper {

        private static Typeface typeface = null;
        private static int type = 1;

        public static void setTypeface(Context context, TextView textView, AttributeSet attrs) {

            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DButtonMaterial);
            try {
                type = ta.getInt(R.styleable.DButtonMaterial_buttonTextFontFace, 1);

                switch (type) {

                    case 1:
//                        if (typeface == null) {

                            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/opensans_regular.ttf");
//                        }
                        break;

                    case 2:
//                        if (typeface == null) {

                            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/opensans_semi_bold.ttf");
//                        }
                        break;

                    case 3:
//                        if (typeface == null) {

                            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/opensans_light.ttf");
//                        }
                        break;

                    case 4:
//                        if (typeface == null) {

                            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/opensans_bold.ttf");
//                        }
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

};