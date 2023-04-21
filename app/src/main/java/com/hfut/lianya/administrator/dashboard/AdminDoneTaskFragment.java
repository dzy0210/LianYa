package com.hfut.lianya.administrator.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hfut.lianya.R;
import com.hfut.lianya.adapters.WorkerDoneTaskAdapter;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.bean.WorkerDoneTask;
import com.hfut.lianya.databinding.FragmentAdminDoneTaskBinding;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;
import com.hfut.lianya.worker.home.WorkerHistoryTaskActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminDoneTaskFragment extends RxLazyFragment {
    private FragmentAdminDoneTaskBinding binding;
    private RecyclerView rvDoneTask;
    List<WorkerDoneTask> list= new ArrayList<>();
    public static AdminDoneTaskFragment newInstance() {
        return new AdminDoneTaskFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_admin_done_task;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminDoneTaskBinding.inflate(inflater, container, false);
        loadData();
        return binding.getRoot();
    }


    @Override
    protected void loadData() {
        super.loadData();
        RetrofitUtil.getDashboardAPI()
                .getDoneTask()
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(doneTaskList -> {
                    list.addAll(doneTaskList);
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
        rvDoneTask = binding.rvDoneTask;
        WorkerDoneTaskAdapter adapter = new WorkerDoneTaskAdapter();
        adapter.submitList(list);
        adapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            Intent intent = new Intent(getActivity(), AdminWorkerDoneTaskActivity.class);
            intent.putExtra("workerNo", list.get(i).getWorkerNo());
            intent.putExtra("workerName", list.get(i).getWorkerName());
            startActivity(intent);
        });
        rvDoneTask.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvDoneTask.setAdapter(adapter);
    }

    @Override
    public void finishCreateView(Bundle state) {

    }

}