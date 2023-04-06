package com.hfut.lianya.administrator.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.LoginActivity;
import com.hfut.lianya.R;
import com.hfut.lianya.administrator.dashboard.AdminHistoryTaskActivity;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.bean.Deliveries;
import com.hfut.lianya.courier.dashboard.CourierBedContinuousCaptureActivity;
import com.hfut.lianya.databinding.FragmentAdminHomeBinding;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;
import com.hfut.lianya.worker.daily.HistoryAbnormalityActivity;
import com.hfut.lianya.worker.daily.HistoryLeaveActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminHomeFragment extends RxLazyFragment implements View.OnClickListener {

    private FragmentAdminHomeBinding binding;
    private CardView cvPersonalInfo;
    private CardView cvHistoryTask;
    private CardView cvHistoryAbnormality;
    private CardView cvHistoryLeave;
    private CardView cvLogout;
    private CardView cvBedDelivered;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_admin_home;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdminHomeBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }

    @Override
    public void finishCreateView(Bundle state) {

    }

    public void initView() {
        cvPersonalInfo = binding.cvPersonalInfo;
        cvHistoryAbnormality = binding.cvHistoryAbnormality;
        cvHistoryLeave = binding.cvHistoryLeave;
        cvHistoryTask = binding.cvHistoryTask;
        cvLogout = binding.cvLogout;
        cvBedDelivered = binding.cvBedDelivered;
        cvPersonalInfo.setOnClickListener(this);
        cvHistoryAbnormality.setOnClickListener(this);
        cvHistoryLeave.setOnClickListener(this);
        cvHistoryTask.setOnClickListener(this);
        cvLogout.setOnClickListener(this);
        cvBedDelivered.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_personal_info:
                startActivity(new Intent(getActivity(), PersonalDetailInfoActivity.class));
                break;
            case R.id.cv_history_abnormality:
                startActivity(new Intent(getActivity(), HistoryAbnormalityActivity.class));
                break;
            case R.id.btn_history_task:
                startActivity(new Intent(getActivity(), AdminHistoryTaskActivity.class));
                break;
            case R.id.cv_history_leave:
                startActivity(new Intent(getActivity(), HistoryLeaveActivity.class));
                break;
            case R.id.cv_logout:
                logout();
                break;
            case R.id.cv_bed_delivered:
                postTest();
                break;
        }
    }

    private void postTest() {
        RetrofitUtil.getDashboardAPI().postTest("aaaaaa")
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respond -> {
                    Toast.makeText(getApplicationContext(), respond.toString(), Toast.LENGTH_LONG).show();
                    finishTask();
                }, throwable -> {});
    }

    private void bedDelivered() {
        startActivity(new Intent(getApplicationContext(), CourierBedContinuousCaptureActivity.class));
    }

    private void logout() {
        SharedPreferences sharedPreferences = GlobalApplication.getInstance().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        getActivity().finish();
    }
}