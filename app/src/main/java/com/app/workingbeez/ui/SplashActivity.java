package com.app.workingbeez.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.workingbeez.R;
import com.app.workingbeez.ui.registration.LoginActivity;
import com.app.workingbeez.utils.Constants;
import com.app.workingbeez.utils.Utils;

/**
 * Created by root on 16/2/17.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Utils.isInternetConnected(getActivity())) {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (Utils.getPref(getActivity(), Constants.LOGIN_RESPONSE, "").equals("")) {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
//                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                        startActivity(intent);
//                        finish();
                    }

                }
            }, 2000);

        } else {

            Utils.showSingleDialog(getActivity(), "", "No Internet Connection", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();
                }
            });
        }
    }
}
