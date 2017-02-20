package com.app.workingbeez.ui.registration.poster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.app.workingbeez.R;
import com.app.workingbeez.ui.models.PosterRegistraionRequestObj;
import com.app.workingbeez.utils.Constants;
import com.app.workingbeez.utils.Utils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 17/2/17.
 */

public class GeneralInformationFragment extends Fragment {

    @BindView(R.id.etPosterRegFname)
    EditText etPosterRegFname;
    @BindView(R.id.etPosterRegLname)
    EditText etPosterRegLname;
    @BindView(R.id.etPosterRegABN)
    EditText etPosterRegABN;
    @BindView(R.id.etPosterRegAboutBusiness)
    EditText etPosterRegAboutBusiness;
    @BindView(R.id.etPosterRegEmailId)
    EditText etPosterRegEmailId;
    @BindView(R.id.etPosterRegNameOfBusiness)
    EditText etPosterRegNameOfBusiness;
    @BindView(R.id.etPosterRegPass)
    EditText etPosterRegPass;
    @BindView(R.id.etPosterRegWebURL)
    EditText etPosterRegWebURL;
    @BindView(R.id.etPosterRegLocation)
    EditText etPosterRegLocation;

    PosterRegistraionRequestObj requestObj;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void initArguments() {

        if (getArguments() != null && getArguments().containsKey(Constants.POSTER_REG_DATA)) {

            requestObj = new Gson().fromJson(getArguments().getString(Constants.POSTER_REG_DATA), PosterRegistraionRequestObj.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_poster_general_information, container, false);
        ButterKnife.bind(this, v);

        ((PosterRegistrationActivity) getActivity()).tvTitle.setText(getString(R.string.title_general_information));

        initArguments();

        if (requestObj != null) {
            setData();
        }

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_poster_reg_general_info, menu);

    }

    public void setData() {

        etPosterRegFname.setText(requestObj.firstName);
        etPosterRegLname.setText(requestObj.lastName);
        etPosterRegEmailId.setText(requestObj.emailID);
        etPosterRegNameOfBusiness.setText(Utils.parseString(requestObj.nameOfBusiness));
        etPosterRegABN.setText(Utils.parseString(requestObj.aBN));
        etPosterRegAboutBusiness.setText(Utils.parseString(requestObj.aboutYourBusiness));
        etPosterRegWebURL.setText(Utils.parseString(requestObj.webSite));
        etPosterRegLocation.setText(Utils.parseString(requestObj.locationName));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_gen_next) {

            if (isValid()) {
                saveData();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flPosterRegContainer, new CategoryFragment(), "category").addToBackStack("category").commit();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isValid() {

        if (etPosterRegFname.length() <= 0) {
            etPosterRegFname.requestFocus();
            etPosterRegFname.setError(getString(R.string.err_fname));
            return false;
        } else if (etPosterRegLname.length() <= 0) {
            etPosterRegLname.requestFocus();
            etPosterRegLname.setError(getString(R.string.err_lname));
            return false;
        } else if (etPosterRegEmailId.length() <= 0) {
            etPosterRegEmailId.requestFocus();
            etPosterRegEmailId.setError(getString(R.string.err_email));
            return false;
        } else if (!Utils.isValidEmail(etPosterRegEmailId.getText().toString())) {
            etPosterRegEmailId.requestFocus();
            etPosterRegEmailId.setError(getString(R.string.err_email_invalid));
            return false;
        } else if (etPosterRegPass.length() <= 0) {
            etPosterRegPass.requestFocus();
            etPosterRegPass.setError(getString(R.string.err_password));
            return false;
        } else if (etPosterRegAboutBusiness.length() <= 0) {
            etPosterRegAboutBusiness.requestFocus();
            etPosterRegAboutBusiness.setError(getString(R.string.err_about_you_business));
            return false;
        } else if (etPosterRegLocation.length() <= 0) {
            etPosterRegLocation.requestFocus();
            etPosterRegLocation.setError(getString(R.string.err_location));
            return false;
        } else {
            return true;
        }
    }

    public void saveData() {

        if (requestObj == null) {
            requestObj = new PosterRegistraionRequestObj();
        }

        requestObj.firstName = etPosterRegFname.getText().toString();
        requestObj.lastName = etPosterRegLname.getText().toString();
        requestObj.emailID = etPosterRegEmailId.getText().toString();
        requestObj.password = etPosterRegPass.getText().toString();
        requestObj.nameOfBusiness = etPosterRegNameOfBusiness.getText().toString();
        requestObj.aboutYourBusiness = etPosterRegAboutBusiness.getText().toString();
        requestObj.aBN = etPosterRegABN.getText().toString();
        requestObj.webSite = etPosterRegWebURL.getText().toString();
        requestObj.locationName = etPosterRegLocation.getText().toString();
        requestObj.latitude = etPosterRegLocation.getTag().toString().split(",")[0];
        requestObj.longitude = etPosterRegLocation.getTag().toString().split(",")[1];

        Utils.setPref(getActivity(), Constants.POSTER_REG_DATA, new Gson().toJson(requestObj));

    }
}
