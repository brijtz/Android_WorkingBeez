package com.app.workingbeez.utils;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by root on 7/7/16.
 */
public class AnimationUtils {

    public static void animate(RecyclerView.ViewHolder holder, boolean goesDown){

        ObjectAnimator animator = ObjectAnimator.ofFloat(holder.itemView,"translationY",goesDown==true?300:-300,0);
        animator.setDuration(500);
        animator.start();
    }

    public static void animate(View view){

        ObjectAnimator animator = ObjectAnimator.ofFloat(view ,"translationY",0,200);
        animator.setDuration(1000);
        animator.start();
    }
}
