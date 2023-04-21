package com.hfut.lianya.worker.daily;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.R;
import com.hfut.lianya.adapters.LeaveRequestAdapter;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.Leave;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HistoryLeaveActivity extends RxBaseActivity {
    Toolbar toolbar;
    RecyclerView rvLeaveRequest;
    List<Leave>list = new ArrayList<>();
    SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences("user", MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_worker_history_leave;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("历史请假");
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
        RetrofitUtil.getLeaveAPI()
                .getHistoryLeaves(sp.getString("userNo", ""))
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(leaves -> {
                    list.addAll(leaves);
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
        rvLeaveRequest = findViewById(R.id.rv_history_leave);
        LeaveRequestAdapter adapter = new LeaveRequestAdapter();
        adapter.submitList(list);
        adapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            Intent intent = new Intent(getApplicationContext(), NewLeaveActivity.class);
            intent.putExtra("id", list.get(i).getId());
            intent.putExtra("leaveType", list.get(i).getLeaveType());
            intent.putExtra("leaveState", list.get(i).getState());
            intent.putExtra("startTime", list.get(i).getStartTime());
            intent.putExtra("endTime", list.get(i).getEndTime());
            intent.putExtra("leaveDesc", list.get(i).getLeaveDesc());
            intent.putExtra("isComplete", list.get(i).getLeaveType());
            startActivity(intent);
        });
        rvLeaveRequest.setLayoutManager(new LinearLayoutManager(this));
        rvLeaveRequest.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rvLeaveRequest.setAdapter(adapter);
    }
}