package com.hfut.lianya.administrator.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hfut.lianya.databinding.FragmentAdminNotificationsBinding;

public class AdminNotificationsFragment extends Fragment implements View.OnClickListener {

    private FragmentAdminNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdminNotificationsBinding.inflate(inflater, container, false);

        initView();
        return binding.getRoot();
    }

    public void initView() {

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}