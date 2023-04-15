package com.hfut.lianya.worker.daily;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.R;
import com.hfut.lianya.adapters.LeaveRequestAdapter;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.Leave;
import com.hfut.lianya.global.GlobalVariable;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WorkerLeaveManagementActivity extends RxBaseActivity implements View.OnClickListener {
    Toolbar toolbar;
    private TextView tvHistoryLeave;
    private RecyclerView rvLeave;
    private Button btnNewLeave;
    List<Leave> list = new ArrayList<>();

    SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences("user", MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews(savedInstanceState);
//        loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_worker_leave_management;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        tvHistoryLeave = findViewById(R.id.tv_history_leave);
        btnNewLeave = findViewById(R.id.btn_new_leave);
        rvLeave = findViewById(R.id.rv_leave);
        tvHistoryLeave.setOnClickListener(this);
        btnNewLeave.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("异常管理");
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_history_leave:
                startHistoryLeave();
                break;
            case R.id.btn_new_leave:
                newLeave();
                break;
        }
    }
    public void startHistoryLeave() {
        Intent intent = new Intent(this, HistoryLeaveActivity.class);
        startActivity(intent);
    }

    private void newLeave() {
        Intent intent = new Intent(this, NewLeaveActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadData() {
        super.loadData();
        list.clear();
        RetrofitUtil.getLeaveAPI()
                .getDealingLeaves(sp.getString("userNo", ""))
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
        rvLeave.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rvLeave.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvLeave.setAdapter(adapter);
    }

    private void cancelLeave(Leave leave) {
        RetrofitUtil.getLeaveAPI().cancelLeave(leave)
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                    if(code == 200) {
                        Toast.makeText(this, "取消成功", Toast.LENGTH_LONG).show();
                    }
                    finishTask();
                }, throwable -> {});
    }
}