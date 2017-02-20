package com.app.workingbeez.ui.instantiatePost;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.workingbeez.R;
import com.app.workingbeez.ui.BaseActivity;
import com.app.workingbeez.utils.Constants;

public class InstantiatePostActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instantiate_post);

        ibHeaderBack = (ImageButton) findViewById(R.id.ibHeaderBack);
        tvHeaderNext = (TextView) findViewById(R.id.tvHeaderNext);
        tvHeaderTitle = (TextView) findViewById(R.id.tvHeaderTitle);

        CategoryFragment categoryFragment = new CategoryFragment();
        categoryFragment.setArguments(InstantiatePostActivity.this);
        
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flContainer, categoryFragment)
                .addToBackStack(Constants.FRAGMENT_CATEGORY)
                .commit();
    }

    public void setTitle(int title) {
        tvHeaderTitle.setText(title);
    }
}
