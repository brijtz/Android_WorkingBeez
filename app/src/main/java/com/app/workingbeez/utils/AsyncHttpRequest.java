package com.app.workingbeez.utils;

import android.app.Activity;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;

public class AsyncHttpRequest extends AsyncHttpClient {

    Activity activity;
    Context context;

    public AsyncHttpRequest(Activity activity) {
        this.activity = activity;
    }

    public AsyncHttpRequest(Context context) {
        this.context = context;
    }

    public static AsyncHttpClient newRequest() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(Constants.TIMEOUT);
        return client;
    }
}
