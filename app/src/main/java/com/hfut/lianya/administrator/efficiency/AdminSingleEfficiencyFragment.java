package com.hfut.lianya.administrator.efficiency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.R;
import com.hfut.lianya.adapters.SingleEfficiencyAdapter;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.bean.SingleEfficiency;
import com.hfut.lianya.databinding.FragmentAdminSingleEfficiencyBinding;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminSingleEfficiencyFragment extends RxLazyFragment {
    private FragmentAdminSingleEfficiencyBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private RecyclerView rvSingleEfficiency;
    List<SingleEfficiency> list = new ArrayList<>();

    public static AdminSingleEfficiencyFragment newInstance() {
        return new AdminSingleEfficiencyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_admin_single_efficiency;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminSingleEfficiencyBinding.inflate(inflater, container, false);
        loadData();
        return binding.getRoot();
    }

    @Override
    protected void loadData() {
        super.loadData();
        RetrofitUtil.getEfficiencyAPI()
                .getSingleEfficiency()
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleEfficiencyList -> {
                    list.addAll(singleEfficiencyList);
                    finishTask();
                }, throwable -> {});
    }
    @Override
    protected void finishTask() {
        super.finishTask();
        initRecyclerView();
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        SingleEfficiencyAdapter adapter = new SingleEfficiencyAdapter();
        adapter.submitList(list);
        rvSingleEfficiency = binding.rvSingleEfficiency;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        rvSingleEfficiency.setLayoutManager(linearLayoutManager);
        rvSingleEfficiency.setAdapter(adapter);
    }

    @Override
    public void finishCreateView(Bundle state) {

    }

}