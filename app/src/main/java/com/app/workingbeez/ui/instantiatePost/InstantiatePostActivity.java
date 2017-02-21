package com.app.workingbeez.ui.instantiatePost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.workingbeez.R;
import com.app.workingbeez.ui.BaseActivity;
import com.app.workingbeez.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstantiatePostActivity extends BaseActivity {

    // --- edited by Jalpesh --- //
    @BindView(R.id.tvHeaderNext)
    public TextView tvHeaderTitle;

    @BindView(R.id.tvHeaderTitle)
    public TextView tvHeaderNext;

    @BindView(R.id.ibHeaderBack)
    public ImageButton ibHeaderBack;
    // --- edited by Jalpesh --- //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instantiate_post);
        ButterKnife.bind(this);

        CategoryFragment categoryFragment = new CategoryFragment();
        categoryFragment.setArguments(InstantiatePostActivity.this);

        addFragment(categoryFragment, Constants.FRAGMENT_CATEGORY);
    }

    public void setTitle(int title) {
        tvHeaderTitle.setText(title);
    }

    public void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flContainer, fragment)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void onBackPressed() {

        android.support.v4.app.Fragment categoryFragment = getSupportFragmentManager().findFragmentById(R.id.flContainer);
        if (categoryFragment instanceof CategoryFragment) {
            finish();
        } else {
            getFragmentManager().popBackStack();
        }

    }
}
