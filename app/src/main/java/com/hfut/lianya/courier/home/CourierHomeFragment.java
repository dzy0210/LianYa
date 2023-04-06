package com.hfut.lianya.courier.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hfut.lianya.R;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.databinding.FragmentCourierHomeBinding;

public class CourierHomeFragment extends RxLazyFragment {
    FragmentCourierHomeBinding binding;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_courier_home;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCourierHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void finishCreateView(Bundle state) {

    }

}