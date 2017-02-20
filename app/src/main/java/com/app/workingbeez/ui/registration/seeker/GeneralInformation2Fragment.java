package com.app.workingbeez.ui.registration.seeker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.app.workingbeez.R;

import butterknife.ButterKnife;

/**
 * Created by root on 17/2/17.
 */

public class GeneralInformation2Fragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_seeker_general_information_2, container, false);
        ButterKnife.bind(this, v);

        ((SeekerRegistrationActivity) getActivity()).tvTitle.setText(getString(R.string.title_general_information));
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_seeker_reg_general_info_2, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_gen2_next) {

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flSeekerRegContainer, new JobTitleFragment(), "job_title").addToBackStack("job_title").commit();
        }

        return super.onOptionsItemSelected(item);
    }


}
