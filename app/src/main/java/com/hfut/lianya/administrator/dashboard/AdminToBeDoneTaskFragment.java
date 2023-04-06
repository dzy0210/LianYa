package com.hfut.lianya.administrator.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.R;
import com.hfut.lianya.adapters.ToBeDoneTaskAdapter;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.bean.Fkpb;
import com.hfut.lianya.databinding.FragmentAdminToBeDoneTaskBinding;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminToBeDoneTaskFragment extends RxLazyFragment {
    private FragmentAdminToBeDoneTaskBinding binding;
    private RecyclerView rvToBeDoneTask;
    List<Fkpb> list = new ArrayList<>();

    public static AdminToBeDoneTaskFragment newInstance() {
        return new AdminToBeDoneTaskFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_admin_to_be_done_task;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminToBeDoneTaskBinding.inflate(inflater, container, false);
        loadData();
        return binding.getRoot();
    }

    @Override
    protected void loadData() {
        super.loadData();
        RetrofitUtil.getDashboardAPI()
                .getToBeDoneTask()
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskList -> {
                    list.addAll(taskList);
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
        rvToBeDoneTask = binding.rvToBeDoneTask;
        ToBeDoneTaskAdapter adapter = new ToBeDoneTaskAdapter();
        adapter.submitList(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        rvToBeDoneTask.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rvToBeDoneTask.setLayoutManager(linearLayoutManager);
        rvToBeDoneTask.setAdapter(adapter);
    }
    @Override
    public void finishCreateView(Bundle state) {

    }

}