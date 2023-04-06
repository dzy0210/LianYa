package com.hfut.lianya.worker.daily;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;

import com.hfut.lianya.R;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.databinding.FragmentWorkerDailyManagementBinding;


public class WorkerDailyManagementFragment extends RxLazyFragment implements View.OnClickListener {

    FragmentWorkerDailyManagementBinding binding;
    CardView cvAbnormalityManagement;
    CardView cvLeaveManagement;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_worker_daily_management;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWorkerDailyManagementBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }
    private void initView() {
        cvAbnormalityManagement = binding.cvAbnormalityManagement;
        cvLeaveManagement = binding.cvLeaveManagement;
        cvAbnormalityManagement.setOnClickListener(this);
        cvLeaveManagement.setOnClickListener(this);
    }


    @Override
    public void finishCreateView(Bundle state) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_abnormality_management:
                startAbnormalityManagement();
                break;
            case R.id.cv_leave_management:
                startLeaveManagement();
        }
    }
    private void startAbnormalityManagement() {
        Intent intent = new Intent(getActivity(), WorkerAbnormalityManagementActivity.class);
        startActivity(intent);
    }
    private void startLeaveManagement() {
        Intent intent = new Intent(getActivity(), WorkerLeaveManagementActivity.class);
        startActivity(intent);
    }
}