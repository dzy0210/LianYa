package com.hfut.lianya.administrator.efficiency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hfut.lianya.R;
import com.hfut.lianya.adapters.PagerAdapter;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.databinding.FragmentAdminEfficiencyBinding;

import java.util.ArrayList;
import java.util.List;

public class AdminEfficiencyFragment extends RxLazyFragment {

    private FragmentAdminEfficiencyBinding binding;
    private TabLayout tabLayout;
    String []tabs = {"总效率", "单包效率"};
    private List<Fragment> list = new ArrayList<>();
    private ViewPager2 vpEfficiency;

    PagerAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_admin_efficiency;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdminEfficiencyBinding.inflate(inflater, container, false);
        tabLayout = binding.tab;

        initView();
        initView2();
        return binding.getRoot();
    }

    @Override
    public void finishCreateView(Bundle state) {

    }

    public void initView() {
        vpEfficiency = binding.vpEfficiency;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void initView2(){
        list.add(AdminSummarizedEfficiencyFragment.newInstance());
        list.add(AdminSingleEfficiencyFragment.newInstance());
        adapter = new PagerAdapter(getSupportActivity(), list);
        vpEfficiency.setOffscreenPageLimit(1);
        vpEfficiency.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, vpEfficiency, (tab, position) -> tab.setText(tabs[position])).attach();
    }
}