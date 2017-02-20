package com.app.workingbeez.utils;

import android.app.Activity;
import android.content.Context;

import com.loopj.android.http.RequestParams;

/**
 * Created by divyeshshani on 22/06/16.
 */
public class RequestParamsUtils {


    Activity activity;
    Context context;

    public RequestParamsUtils(Activity activity) {
        this.activity = activity;
    }

    public RequestParamsUtils(Context context) {
        this.context = context;
    }

    public static RequestParams newRequestParams() {
        RequestParams params = new RequestParams();
        return params;
    }

}
