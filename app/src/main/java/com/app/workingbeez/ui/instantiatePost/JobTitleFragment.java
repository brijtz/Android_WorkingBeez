package com.app.workingbeez.ui.instantiatePost;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.app.workingbeez.R;
import com.app.workingbeez.widgets.FlowLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by oozee on 20/2/17.
 */

public class JobTitleFragment extends Fragment {

    Context context;

    @BindView(R.id.etSearchCategory)
    EditText etSearchCategory;

    @BindView(R.id.flowLayoutCategory)
    FlowLayout flowLayoutCategory;

    @BindView(R.id.flowLayoutSelectedCategory)
    FlowLayout flowLayoutSelectedCategory;

    ArrayList<String> categoryList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        ButterKnife.bind(this, view);

        ((InstantiatePostActivity) context).setTitle(R.string.header_jobTitle);
        ((InstantiatePostActivity) context).ibHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((InstantiatePostActivity) context).getSupportFragmentManager().popBackStack();
            }
        });

        categoryList.add("Out of School Hours Care (OSHC)");
        categoryList.add("Childcare Worker");
        categoryList.add("Nanny");
        categoryList.add("Childcare Leader");
        categoryList.add("Nanny");
        categoryList.add("Educator");
        categoryList.add("Daycare Teacher");
        categoryList.add("Assistant Educator");
        categoryList.add("Childcare Worker");
        categoryList.add("Nanny");
        categoryList.add("Childcare Leader");
        categoryList.add("Nanny");
        categoryList.add("Childcare Leader");
        categoryList.add("Childcare Worker");

        setFilteredCategory(categoryList);
        setSelectedCategory(categoryList);

        etSearchCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etSearchCategory.getText().toString().trim().length() > 0) {
                    getFilterList(etSearchCategory.getText().toString().trim());
                } else {
                    // dispaly all category
                    setFilteredCategory(categoryList);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((InstantiatePostActivity) context).setTitle(R.string.header_category);
    }

    public void setArguments(Context context) {
        this.context = context;
    }

    void getFilterList(String category) {
        ArrayList<String> tempList = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            String tempCat = categoryList.get(i);
            if (tempCat.contains(category)) {
                tempList.add(tempCat);
            }
        }
        setFilteredCategory(tempList);
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
            TextView tvDeselect = (TextView) view.findViewById(R.id.tvDeselect);

            tvCategoryName.setText(categoryList.get(i));

            tvDeselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            flowLayoutSelectedCategory.addView(view);
        }
    }
}
