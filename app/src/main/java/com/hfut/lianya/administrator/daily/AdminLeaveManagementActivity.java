package com.hfut.lianya.administrator.daily;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
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

public class AdminLeaveManagementActivity extends RxBaseActivity {
    Toolbar toolbar;
    RecyclerView rvLeaveRequest;
    List<Leave> list = new ArrayList<>();
    SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences("user", MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        initViews(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_admin_leave_management;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("请假管理");
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
        RetrofitUtil.getLeaveAPI()
                .getDealingLeavesByDealer(sp.getString("userNo", ""))
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
        LeaveRequestAdapter adapter = new LeaveRequestAdapter();
        adapter.submitList(list);
        adapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            Intent intent = new Intent(getApplicationContext(), AdminDealLeaveActivity.class);
            intent.putExtra("id", list.get(i).getId());
            intent.putExtra("workerNo", list.get(i).getSenderNo());
            intent.putExtra("workerName", list.get(i).getSender());
            intent.putExtra("desc", list.get(i).getLeaveDesc());
            intent.putExtra("startTime", list.get(i).getStartTime());
            intent.putExtra("endTime", list.get(i).getEndTime());
            intent.putExtra("type", list.get(i).getLeaveType());

            startActivity(intent);
        });
        rvLeaveRequest = findViewById(R.id.rv_leave_request);
        rvLeaveRequest.setLayoutManager(new LinearLayoutManager(this));
        rvLeaveRequest.setAdapter(adapter);
    }
}