package com.hfut.lianya.administrator.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.R;
import com.hfut.lianya.adapters.WorkerDoingTaskAdapter;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.bean.WorkerDoingTask;
import com.hfut.lianya.databinding.FragmentAdminDoingTaskBinding;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminDoingTaskFragment extends RxLazyFragment {
    private FragmentAdminDoingTaskBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private RecyclerView rvDoingTask;
    List<WorkerDoingTask> list = new ArrayList<>();

    public static AdminDoingTaskFragment newInstance() {
        AdminDoingTaskFragment fragment = new AdminDoingTaskFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_admin_doing_task;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminDoingTaskBinding.inflate(inflater, container, false);
        loadData();
        return binding.getRoot();
    }

    @Override
    protected void loadData() {
        super.loadData();
        RetrofitUtil.getDashboardAPI()
                .getDoingTask()
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
        rvDoingTask = binding.rvDoingTask;
        WorkerDoingTaskAdapter adapter = new WorkerDoingTaskAdapter(list, doingTask -> {
            Intent intent = new Intent(getActivity(), AdminWorkerDoingTaskActivity.class);
            intent.putExtra("workerNo", doingTask.getWorkerNo());
            intent.putExtra("workerName", doingTask.getWorkerName());
            startActivity(intent);
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        rvDoingTask.setLayoutManager(linearLayoutManager);
        rvDoingTask.setAdapter(adapter);
    }

    @Override
    public void finishCreateView(Bundle state) {

    }

}