package com.hfut.lianya.administrator.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hfut.lianya.R;
import com.hfut.lianya.adapters.DoingTaskAdapter;
import com.hfut.lianya.adapters.WorkerDoneTaskAdapter;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.WorkerDoneTask;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;
import com.hfut.lianya.worker.home.WorkerHistoryTaskActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminHistoryTaskActivity extends RxBaseActivity implements View.OnClickListener{
    Toolbar toolbar;
    RecyclerView rv;
    List<WorkerDoneTask> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews(savedInstanceState);
        initToolBar();
        loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_admin_history_task;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        rv = findViewById(R.id.rv_history_task);
    }

    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("历史任务");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public void loadData() {
        super.loadData();
        list.clear();
        RetrofitUtil.getDashboardAPI()
                .getWorkerHistoryTask()
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respond -> {
                    list.addAll(respond.getData());
                    finishTask();
                }, throwable -> {});
    }

    @Override
    public void initRecyclerView() {
        super.initRecyclerView();
        WorkerDoneTaskAdapter adapter = new WorkerDoneTaskAdapter();
        adapter.submitList(list);
        adapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            Intent intent = new Intent(this, WorkerHistoryTaskActivity.class);
            intent.putExtra("workerNo", list.get(i).getWorkerNo());
            intent.putExtra("workerName", list.get(i).getWorkerName());
            startActivity(intent);
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.setAdapter(adapter);
    }

    @Override
    public void finishTask() {
        super.finishTask();
        initRecyclerView();
    }
}