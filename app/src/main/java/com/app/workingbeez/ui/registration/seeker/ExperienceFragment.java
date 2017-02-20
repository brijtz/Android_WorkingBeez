package com.app.workingbeez.ui.registration.seeker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.workingbeez.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by root on 17/2/17.
 */

public class ExperienceFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_seeker_reg_experience, container, false);
        ButterKnife.bind(this, v);

        ((SeekerRegistrationActivity) getActivity()).tvTitle.setText(getString(R.string.title_experience));
        return v;
    }

    @OnClick(R.id.flNext)
    public void onNext(View v) {

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flSeekerRegContainer, new GeneralInformation2Fragment(), "generalInformation2").addToBackStack("generalInformation2").commit();
    }

    @OnClick(R.id.flBottomBack)
    public void onflBottomBack(View v) {

        getActivity().onBackPressed();
    }
}
