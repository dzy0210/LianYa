package com.hfut.lianya.administrator.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.R;
import com.hfut.lianya.adapters.WorkerDoneTaskDetailAdapter;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.Fkpb;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminWorkerDoneTaskActivity extends RxBaseActivity implements View.OnClickListener{
    Toolbar toolbar;
    String workerNo;
    String workerName;
    RecyclerView rvWorkerDoneTask;
    List<Fkpb> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        workerNo = intent.getStringExtra("workerNo");
        workerName = intent.getStringExtra("workerName");
        initToolBar();
        loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_admin_worker_done_task;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }
    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(workerName);
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
    public void loadData() {
        super.loadData();
        RetrofitUtil.getDashboardAPI()
                .getTask(workerNo, '4')
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskList -> {
                    list.addAll(taskList);
                    finishTask();
                }, throwable -> {});
    }

    @Override
    public void finishTask() {
        super.finishTask();
        initRecyclerView();
    }

    @Override
    public void initRecyclerView() {
        super.initRecyclerView();
        rvWorkerDoneTask = findViewById(R.id.rv_worker_done_task);
        WorkerDoneTaskDetailAdapter adapter = new WorkerDoneTaskDetailAdapter(list, packageCirculation -> {

        });
        rvWorkerDoneTask.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvWorkerDoneTask.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}