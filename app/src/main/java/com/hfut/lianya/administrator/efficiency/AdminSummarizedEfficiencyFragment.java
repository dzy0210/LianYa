package com.hfut.lianya.administrator.efficiency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.R;
import com.hfut.lianya.adapters.SummarizedEfficiencyAdapter;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.bean.SummarizedEfficiency;
import com.hfut.lianya.databinding.FragmentAdminSummarizedEfficiencyBinding;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminSummarizedEfficiencyFragment extends RxLazyFragment {
    private FragmentAdminSummarizedEfficiencyBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private RecyclerView rvSummarizedEfficiency;
    List<SummarizedEfficiency> list = new ArrayList<>();

    public static AdminSummarizedEfficiencyFragment newInstance() {
        return new AdminSummarizedEfficiencyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_admin_summarized_efficiency;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminSummarizedEfficiencyBinding.inflate(inflater, container, false);
        loadData();
        return binding.getRoot();
    }

    @Override
    protected void loadData() {
        super.loadData();
        RetrofitUtil.getEfficiencyAPI()
                .getSummarizedEfficiency()
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(doingTaskList -> {
                    list.addAll(doingTaskList);
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
        rvSummarizedEfficiency = binding.rvSummarizedEfficiency;
        SummarizedEfficiencyAdapter adapter = new SummarizedEfficiencyAdapter();
        adapter.submitList(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        rvSummarizedEfficiency.setLayoutManager(linearLayoutManager);
        rvSummarizedEfficiency.setAdapter(adapter);
    }

    @Override
    public void finishCreateView(Bundle state) {

    }

}