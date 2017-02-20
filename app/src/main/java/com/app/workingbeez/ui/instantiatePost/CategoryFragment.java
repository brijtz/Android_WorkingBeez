package com.app.workingbeez.ui.instantiatePost;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.app.workingbeez.R;
import com.app.workingbeez.ui.BaseActivity;
import com.app.workingbeez.widgets.FlowLayout;

import java.util.ArrayList;

/**
 * Created by oozee on 20/2/17.
 */

public class CategoryFragment extends Fragment {

    Context context;
    EditText etSearchCategory;
    FlowLayout flowLayoutCategory, flowLayoutSelectedCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        etSearchCategory = (EditText) view.findViewById(R.id.etSearchCategory);
        flowLayoutCategory = (FlowLayout) view.findViewById(R.id.flowLayoutCategory);
        flowLayoutSelectedCategory = (FlowLayout) view.findViewById(R.id.flowLayoutSelectedCategory);

        ((InstantiatePostActivity) context).setTitle(R.string.header_category);
        ((BaseActivity) context).ibHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) context).finish();
            }
        });

        return view;
    }

    public void setArguments(Context context) {
        this.context = context;
    }

    void setFilteredCategory(ArrayList<String> categoryList) {
        flowLayoutCategory.removeAllViews();
        for (int i = 0; i < categoryList.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_category, null);
            TextView tvCategoryName = (TextView) view.findViewById(R.id.tvCategoryName);
            TextView tvAddCategory = (TextView) view.findViewById(R.id.tvAddCategory);

            tvCategoryName.setText(categoryList.get(i));

            tvAddCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            flowLayoutCategory.addView(view);
        }
    }

    void setSelectedCategory(ArrayList<String> categoryList) {
        flowLayoutSelectedCategory.removeAllViews();
        for (int i = 0; i < categoryList.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_selected_category, null);
            TextView tvCategoryName = (TextView) view.findViewById(R.id.tvCategoryName);
            TextView tvAddCategory = (TextView) view.findViewById(R.id.tvAddCategory);

            tvCategoryName.setText(categoryList.get(i));

            tvAddCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            flowLayoutSelectedCategory.addView(view);
        }
    }
}
