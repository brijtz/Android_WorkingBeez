package com.app.workingbeez.utils;

import android.app.Activity;
import android.content.Context;

import com.app.workingbeez.ui.models.Response;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

public abstract class AsyncResponseHandler extends AsyncHttpResponseHandler {

    private Activity context;

    public AsyncResponseHandler(Activity context) {
        this.context = context;
    }

    public AsyncResponseHandler(Context context) {
    }

    abstract public void onSuccess(String content);

    //
    abstract public void onFailure(Throwable e, String content);

    @Override
    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
        try {

            String response = new String(responseBody, "UTF-8");
            Debug.e("Request Response", response);
            try {

                if (response != null && response.length() > 0) {

                    Response res = new Gson().fromJson(response, Response.class);

//                    if (res.st == 505) {
//
//                        Utils.clearLoginCredetials(context);
//
//                        Intent intent = new Intent(context,
//                                LoginActivity.class);
//                        intent.putExtra("finish", true);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
//                                Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
//                        context.finish();
//                        return;
//
//                    }
                }

            } catch (Exception e) {
                Utils.sendExceptionReport(e);
            }

            onSuccess(response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable e) {
        try {
            e.printStackTrace();
            Debug.e("onFailure", "" + new String(responseBody, "UTF-8"));
            onFailure(e, "" + e.getMessage());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
