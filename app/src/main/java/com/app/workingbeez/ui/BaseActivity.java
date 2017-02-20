package com.app.workingbeez.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.workingbeez.R;
import com.app.workingbeez.ui.adapters.SpinnerAdapter;
import com.app.workingbeez.ui.models.CategoriesListRequest;
import com.app.workingbeez.ui.models.CategoriesListResponse;
import com.app.workingbeez.ui.models.Spinner;
import com.app.workingbeez.utils.AsyncHttpRequest;
import com.app.workingbeez.utils.AsyncProgressDialog;
import com.app.workingbeez.utils.AsyncResponseHandler;
import com.app.workingbeez.utils.Constants;
import com.app.workingbeez.utils.Debug;
import com.app.workingbeez.utils.URLs;
import com.app.workingbeez.utils.Utils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by divyeshshani on 21/06/16.
 */
public class BaseActivity extends AppCompatActivity {

    AsyncProgressDialog ad;
    private Toast toast;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
    }


    public void showToast(final int text, final int duration) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                toast.setText(getString(text).toString());
                toast.setDuration(duration);
                toast.show();
            }
        });
    }

    public void showToast(final String text, final int duration) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                toast.setText(text);
                toast.setDuration(duration);
                toast.show();
            }
        });
    }

    public void showProgress(String msg) {
        try {
            if (ad != null && ad.isShowing()) {
                return;
            }

            ad = AsyncProgressDialog.getInstant(getActivity());
            ad.show(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public BaseActivity getActivity() {
        return this;
    }

    public void dismissProgress() {
        try {
            if (ad != null) {
                ad.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void confirmLogout() {

        AlertDialog builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.logout_title)
                .setMessage(R.string.logout_msg)
                .setPositiveButton(R.string.hint_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.clearLoginCredetials(getActivity());
//                        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
//                        loginIntent.putExtra("finish", true);
//                        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
//                                Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(loginIntent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.hint_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        builder.show();

    }

    public void showNoInternetDialog() {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.internet_err))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.hint_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).create();
        dialog.show();
    }

    public void showPermissionExplaination(Context c, String msg, final String permission, final int reqCode) {

        AlertDialog dialog = new AlertDialog.Builder(c)
                .setMessage(msg + getString(R.string.msg_permission_expln_dialog))
                .setPositiveButton(R.string.hint_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{permission},
                                reqCode);
                    }
                })
                .setNegativeButton(R.string.hint_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .create();
        dialog.setCancelable(false);
        dialog.show();

    }

    public void showDialog(String msg, String title) {

        AlertDialog builder = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.hint_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                }).create();

        builder.show();
    }

    public void showSpinner(String title, final EditText tv,
                            final ArrayList<Spinner> data) {

        final Dialog a = new Dialog(this);
        Window w = a.getWindow();
        a.requestWindowFeature(Window.FEATURE_NO_TITLE);
        a.setContentView(R.layout.spinner);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        TextView lblselect = (TextView) w.findViewById(R.id.dialogtitle);
        lblselect.setTypeface(Utils.getRobotoBold(getActivity()));
        lblselect.setText(title.replace("*", "").trim());

        SpinnerAdapter adapter = new SpinnerAdapter(getActivity());
        ListView lv = (ListView) w.findViewById(R.id.lvSpinner);
        lv.setAdapter(adapter);
        adapter.addAll(data);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterview, View view,
                                    int position, long l) {
                tv.setText(data.get(position).title);
                tv.setTag(data.get(position).ID);
                a.dismiss();
//                etTotalPrice.setText(Integer.parseInt(etQuantity.getText().toString())*Integer.parseInt(etUnitPrice.getText().toString()));
            }
        });

        a.show();
    }

    public void getAllCategories() {

        if (Utils.isInternetConnected(getActivity())) {

            try {

                showProgress("");

                CategoriesListRequest request = new CategoriesListRequest();
                request.deviceID = "";
                request.token = Utils.getToken(getActivity());
                request.pageIndex = -1;

                StringEntity entity = new StringEntity(new Gson().toJson(request));

                AsyncHttpClient categoriesRequest = AsyncHttpRequest.newRequest();
                categoriesRequest.post(getActivity(), URLs.GETALLCATEGORY, entity, "application/json",
                        new CategoryListResponseHandler(getActivity()));

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            showToast(R.string.internet_err, Toast.LENGTH_SHORT);
        }
    }

    public class CategoryListResponseHandler extends AsyncResponseHandler {

        public CategoryListResponseHandler(Activity context) {
            super(context);
        }

        @Override
        public void onFinish() {
            super.onFinish();

            dismissProgress();
        }

        @Override
        public void onSuccess(String content) {

            Debug.e("CategoryListResponse", content);
            CategoriesListResponse response = new Gson().fromJson(content, CategoriesListResponse.class);

            dismissProgress();

            if (response.returnCode.equalsIgnoreCase("1")) {

                Utils.setPref(getActivity(), Constants.CATEGORY_LIST_RESPONSE, content);

            } else {

                Utils.showSingleDialog(getActivity(), "", response.returnMsg);
            }

        }

        @Override
        public void onFailure(Throwable e, String content) {

            dismissProgress();
        }
    }

}
