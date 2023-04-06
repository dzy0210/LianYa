package com.hfut.lianya.courier.dashboard;

//import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.hfut.lianya.LoginActivity;
import com.hfut.lianya.R;
import com.hfut.lianya.adapters.PagerAdapter;
import com.hfut.lianya.base.RxLazyFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hfut.lianya.databinding.FragmentCourierDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class CourierDashboardFragment extends RxLazyFragment implements View.OnClickListener{
    private FragmentCourierDashboardBinding binding;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private LinearLayout llScan;
    private LinearLayout llPause;
    private LinearLayout llStop;
    private LinearLayout llBegScan;
    int flag = 0;
    PagerAdapter adapter;
    String []tabs = {"待派送", "派送中"};
    List<Fragment>list;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_courier_dashboard;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCourierDashboardBinding.inflate(inflater, container, false);
        initView();

        return binding.getRoot();
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
    private void initView() {
        list = new ArrayList<>();
        llScan = binding.llStartScan;
        llScan.setOnClickListener(this);
        tabLayout = binding.tab;
        viewPager2 = binding.vp;
        llPause = binding.llPause;
        llBegScan = binding.llBedScan;
        llBegScan.setOnClickListener(this);
        llPause.setOnClickListener(this);
        llStop = binding.llStop;
        llStop.setOnClickListener(this);
        list.add(CourierReadyFragment.newInstance());
        list.add(CourierDeliveringFragment.newInstance());
        adapter = new PagerAdapter(getActivity(), list);
        viewPager2.setOffscreenPageLimit(1);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(tabs[position])).attach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_start_scan:
                scanContinuous();//连续扫码功能
                break;
            case R.id.ll_stop:
                dialog2();
                break;
            case R.id.ll_pause:
                break;
            case R.id.ll_bed_scan:
                bedScan();
                break;

        }
    }
    protected void dialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("确认下班？");
        builder.setPositiveButton("确认", (dialog, arg1) -> {
            leave();
            dialog.dismiss();
        });
        //添加取消
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
    private void leave() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        getActivity().finish();
    }
    public void scanContinuous() {//连续扫码功能
        Intent intent = new Intent(getContext(), CourierContinuousCaptureActivity.class);
        startActivity(intent);
    }
    public void bedScan() {//连续扫码功能
        Intent intent = new Intent(getContext(), CourierBedContinuousCaptureActivity.class);
        startActivity(intent);
    }
}