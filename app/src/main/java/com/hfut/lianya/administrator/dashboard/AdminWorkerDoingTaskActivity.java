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
import com.hfut.lianya.adapters.WorkerTaskDetailAdapter;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.Fkpb;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminWorkerDoingTaskActivity extends RxBaseActivity implements View.OnClickListener{
    Toolbar toolbar;
    String workerNo;
    String workerName;
    RecyclerView rvWorkerDoingTask;
    NiceSpinner spWorker;
    List<Fkpb> list = new ArrayList<>();
    List<String>workers = Arrays.asList("胡琪伟", "何聪", "徐浩");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        workerNo = intent.getStringExtra("workerNo");
        workerName = intent.getStringExtra("workerName");
        initToolBar();
        initViews(savedInstanceState);
        loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_admin_worker_doing_task;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        spWorker = findViewById(R.id.sp_select_worker);
        spWorker.attachDataSource(workers);
        spWorker.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {

        });
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
                .getWorkerDoingTask(workerNo)
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
        rvWorkerDoingTask = findViewById(R.id.rv_worker_doing_task);
        WorkerTaskDetailAdapter adapter = new WorkerTaskDetailAdapter(list, packageCirculation -> {

        });
        rvWorkerDoingTask.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvWorkerDoingTask.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}