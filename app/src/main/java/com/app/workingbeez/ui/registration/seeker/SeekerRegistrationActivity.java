package com.app.workingbeez.ui.registration.seeker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.app.workingbeez.R;
import com.app.workingbeez.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by root on 17/2/17.
 */

public class SeekerRegistrationActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.flSeekerRegContainer)
    FrameLayout flSeekerRegContainer;

    @BindView(R.id.tvTitle)
    public TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_registration);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setTitle("");
        getSupportFragmentManager().beginTransaction().add(R.id.flSeekerRegContainer, new GeneralInformationFragment(), "general_information").commit();

    }

    @OnClick(R.id.flBack)
    public void onBack(View v) {
        getActivity().onBackPressed();
    }

}
