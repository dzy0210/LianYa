package com.hfut.lianya.worker.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.R;
import com.hfut.lianya.adapters.DoingTaskAdapter;
import com.hfut.lianya.adapters.LackAdapter;
import com.hfut.lianya.adapters.WorkerDoingTaskAdapter;
import com.hfut.lianya.adapters.WorkerDoneTaskDetailAdapter;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.Finish;
import com.hfut.lianya.bean.Fkpb;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WorkerHistoryTaskActivity extends RxBaseActivity {

    Toolbar toolbar;
    RecyclerView rv;
    String workerNo;
    List<Finish> list = new ArrayList<>();
    SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences("user", MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews(savedInstanceState);
        loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_worker_history_task;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        rv = findViewById(R.id.rv_history_task);
        Intent intent = getIntent();
        if (intent.getStringExtra("workerNo") != null) {
            workerNo = intent.getStringExtra("workerNo");
        } else {
            workerNo = sp.getString("userNo", "");
        }
    }

    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("历史任务");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
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
        list.clear();
        RetrofitUtil.getDashboardAPI()
                .getHistoryTask(workerNo)
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lacks -> {
                    list.addAll(lacks);
                    finishTask();
                }, throwable -> {});
    }

    @Override
    public void initRecyclerView() {
        super.initRecyclerView();
        WorkerDoneTaskDetailAdapter adapter = new WorkerDoneTaskDetailAdapter();
        adapter.submitList(list);
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