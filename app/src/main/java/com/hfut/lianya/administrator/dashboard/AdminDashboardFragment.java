package com.hfut.lianya.administrator.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hfut.lianya.R;
import com.hfut.lianya.adapters.PagerAdapter;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.databinding.FragmentAdminDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class AdminDashboardFragment extends RxLazyFragment implements View.OnClickListener {

    private FragmentAdminDashboardBinding binding;
    private TabLayout tabLayout;
    String []tabs = {"在做任务", "待做任务", "已做任务"};
    private List<Fragment> list = new ArrayList<>();
    private ViewPager2 vpWorkstation;

    PagerAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_admin_dashboard;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminDashboardBinding.inflate(inflater, container, false);
        tabLayout = binding.tab;

        initView();
        initView2();
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }

    public void initView() {
        vpWorkstation = binding.vpWorkstation;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void initView2(){
        list.clear();
        list.add(AdminDoingTaskFragment.newInstance());
        list.add(AdminToBeDoneTaskFragment.newInstance());
        list.add(AdminDoneTaskFragment.newInstance());
        adapter = new PagerAdapter((FragmentActivity) getContext(), list);
        vpWorkstation.setOffscreenPageLimit(1);
        vpWorkstation.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, vpWorkstation, (tab, position) -> tab.setText(tabs[position])).attach();
    }

    @Override
    public void onClick(View v) {

    }
}