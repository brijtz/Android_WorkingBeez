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
import android.widget.AutoCompleteTextView;

import com.app.workingbeez.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 17/2/17.
 */

public class CategoryFragment extends Fragment {

    @BindView(R.id.tvPosterRegCategory)
    AutoCompleteTextView tvPosterRegCategory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void initArgument() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_poster_reg_category, container, false);
        ButterKnife.bind(this, v);

        ((PosterRegistrationActivity) getActivity()).tvTitle.setText(getString(R.string.title_poster_category));
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_poster_reg_category, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_finish) {
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flPosterRegContainer, new (), "general_information2").addToBackStack("general_information").commit();
        }

        return super.onOptionsItemSelected(item);
    }
}
