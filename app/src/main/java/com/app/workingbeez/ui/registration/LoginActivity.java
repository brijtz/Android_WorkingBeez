package com.app.workingbeez.ui.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.app.workingbeez.BuildConfig;
import com.app.workingbeez.R;
import com.app.workingbeez.ui.BaseActivity;
import com.app.workingbeez.ui.dashboard.seeker.SeekerDashboardActivity;
import com.app.workingbeez.utils.AsyncResponseHandler;
import com.app.workingbeez.utils.Constants;
import com.app.workingbeez.utils.Debug;
import com.app.workingbeez.utils.Utils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by root on 9/7/16.
 */

public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {


    private LoginButton fbLoginBtn;
    private SignInButton gPlusLoginButton;

    private CallbackManager callbackManager;
    private String EMAIL_ID;
    private String FB_LOGIN_TAG = "FBLogin";

    private boolean isFbLogin;
    private boolean isGplusLogin;

    private static final int RC_SIGN_IN = 9001;

    Bundle fbData;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.printHashKey(getActivity());
        FacebookSdk.sdkInitialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(getActivity());

        initFbLogin();
        initGplusLogin();
    }

    @OnClick(R.id.btnLogin)
    public void onLogin(View v) {

        Intent intent = new Intent(getActivity(), SeekerDashboardActivity.class);
        startActivity(intent);
    }

    private void initGplusLogin() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        gPlusLoginButton = (SignInButton) findViewById(R.id.gplusLoginBtn);
        gPlusLoginButton.setScopes(gso.getScopeArray());


    }

    public void initFbLogin() {

        fbLoginBtn = (LoginButton) findViewById(R.id.fbLoginBtn);
        fbLoginBtn.setReadPermissions("email", "public_profile");
        fbLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                System.out.println("onSuccess");

                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        fbData = getFacebookData(object);
//                        Utils.setPref(getActivity(), RequestParamsUtils.EMAIL, fbData.getString("email"));
                        Utils.setPref(getActivity(), Constants.NAME, "" + fbData.get("first_name") + fbData.get("last_name"));
                        userRegistration(fbData.getString("email"), fbData.getString("gender"), 1);
                        LoginManager.getInstance().logOut();
                    }

                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (isFbLogin) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Utils.setPref(getActivity(), Constants.NAME, acct.getDisplayName());
            userRegistration(acct.getEmail(), "", 2);
        }

        signOut();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }

    @Keep
    @OnClick(R.id.tvSignUp)
    public void signUpBtn(View v) {


    }

    @Keep
    @OnClick(R.id.tvForgotPassword)
    public void tvForgotPassword(View v) {


    }

    @Keep
    @OnClick(R.id.fabFB)
    public void facebookLogin() {

        isFbLogin = true;
        isGplusLogin = false;
        fbLoginBtn.performClick();

    }

    @Keep
    @OnClick(R.id.fabGPlus)
    public void gplusLogin() {

        isFbLogin = false;
        isGplusLogin = true;
        signIn();

    }

    private Bundle getFacebookData(JSONObject object) {

        Bundle bundle = null;
        try {
            bundle = new Bundle();
            String id = object.getString("id");
//            try {
//                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
//                Log.i("profile_pic", profile_pic + "");
//                bundle.putString("profile_pic", profile_pic.toString());
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                return null;
//            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        } catch (Exception e) {

            Debug.e("FBDATABUNDLE ERR", e.toString());
        }

        return bundle;
    }

    private void userRegistration(String email, String gender, int type) {
        try {
            showProgress("");
//            RequestParams params = RequestParamsUtils.newRequestParams(getActivity());
//            params.put(RequestParamsUtils.EMAIL, email);
//            params.put(RequestParamsUtils.GENDER, gender);
//            params.put(RequestParamsUtils.REGISTRATIONMODE, type);
//            params.put(RequestParamsUtils.ISANDROIDNOTIFYKEY, Utils.getPref(getActivity(), Constants.GCM_KEY, ""));
//            params.put(RequestParamsUtils.ISIOSNOTIFYKEY, "");
//            Debug.e("userRegistrationParams", params.toString());
//            AsyncHttpClient clientPhotos = AsyncHttpRequest.newRequest();
//            clientPhotos.post(new URLs().USERREGISTRATION, params, new UserRegHandler(getActivity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class UserRegHandler extends AsyncResponseHandler {

        public UserRegHandler(Activity context) {
            super(context);
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onFinish() {
            super.onFinish();
            try {
                dismissProgress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSuccess(String response) {
            try {
                dismissProgress();
                Debug.e("", "LoginResponse# " + response);
                if (response != null && response.length() > 0) {


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
        }

    }

    public void redirect() {

        if (Utils.getPref(getActivity(), Constants.LOGIN_RESPONSE, "").equals("")) {


            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } else {

            if (Utils.isInternetConnected(getActivity())) {


            } else {

                showNoInternetDialog();
            }

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}


//9FA0D4162E13A2AF9F572FE9C6850C764AB28B4E