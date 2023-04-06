package com.hfut.lianya.administrator.daily;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.hfut.lianya.R;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.databinding.FragmentAdminDailyManagementBinding;

public class AdminDailyManagementFragment extends RxLazyFragment implements View.OnClickListener {

    private FragmentAdminDailyManagementBinding binding;
    private CardView cvLeaveManagement;
    private CardView cvOvertimeManagement;
    private CardView cvAbnormalityManagement;
    private CardView cvSecondmentManagement;
    private CardView cvLeaveForWorker;
    private CardView cvLackManagement;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_admin_daily_management;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminDailyManagementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initView();

        return root;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }

    public void initView() {
        cvLeaveManagement = binding.cvLeaveManagement;
        cvOvertimeManagement = binding.cvOvertimeManagement;
        cvAbnormalityManagement = binding.cvAbnormalityManagement;
        cvSecondmentManagement=binding.cvSecondmentManagement;
        cvLeaveForWorker = binding.cvLeaveForWorker;
        cvLackManagement = binding.cvLackManagement;
        cvLeaveManagement.setOnClickListener(this);
        cvOvertimeManagement.setOnClickListener(this);
        cvAbnormalityManagement.setOnClickListener(this);
        cvSecondmentManagement.setOnClickListener(this);
        cvLeaveForWorker.setOnClickListener(this);
        cvLackManagement.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cv_abnormality_management:
                startActivity(new Intent(getActivity(), AdminAbnormalityManagementActivity.class));
                break;
            case R.id.cv_leave_management:
                startActivity(new Intent(getActivity(), AdminLeaveManagementActivity.class));
                break;
            case R.id.cv_secondment_management:
                startActivity(new Intent(getActivity(), AdminSecondmentManagementActivity.class));
                break;
            case R.id.cv_leave_for_worker:
                startActivity(new Intent(getActivity(), AdminLeaveForWorkerActivity.class));
                break;
            case R.id.cv_lack_management:
                startActivity(new Intent(getActivity(), AdminLackManagementActivity.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}