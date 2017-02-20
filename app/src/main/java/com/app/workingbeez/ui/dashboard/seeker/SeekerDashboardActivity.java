package com.app.workingbeez.ui.dashboard.seeker;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.app.workingbeez.R;
import com.app.workingbeez.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 16/2/17.
 */

public class SeekerDashboardActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
}
