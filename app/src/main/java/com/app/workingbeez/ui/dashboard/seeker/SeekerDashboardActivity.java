package com.app.workingbeez.ui.dashboard.seeker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

import com.app.workingbeez.R;
import com.app.workingbeez.ui.BaseActivity;
import com.app.workingbeez.ui.instantiatePost.InstantiatePostActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by root on 16/2/17.
 */

public class SeekerDashboardActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.flSeekerDashboardJobHub)
    FrameLayout flSeekerDashboardJobHub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_dashboard);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_seeker_dash_board, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick(R.id.flSeekerDashboardJobHub)
    public void onJobHubClicked(View view) {
        startActivity(new Intent(SeekerDashboardActivity.this, InstantiatePostActivity.class));
    }
}
