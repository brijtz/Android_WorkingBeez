package com.app.workingbeez.ui.registration.poster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.app.workingbeez.R;
import com.app.workingbeez.ui.BaseActivity;
import com.app.workingbeez.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by root on 17/2/17.
 */

public class PosterRegistrationActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.flPosterRegContainer)
    FrameLayout flSeekerRegContainer;

    @BindView(R.id.tvTitle)
    public TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_registration);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setTitle("");

        Utils.clearPosterRegistrationData(getActivity());

        getSupportFragmentManager().beginTransaction().add(R.id.flPosterRegContainer, new GeneralInformationFragment(), "general_information").commit();

        getAllCategories();
    }

    @OnClick(R.id.flBack)
    public void onBack(View v) {
        getActivity().onBackPressed();
    }

}
