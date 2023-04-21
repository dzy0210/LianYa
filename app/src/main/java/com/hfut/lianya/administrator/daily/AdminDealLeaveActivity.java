package com.hfut.lianya.administrator.daily;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.R;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.Leave;
import com.hfut.lianya.net.RetrofitUtil;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminDealLeaveActivity extends RxBaseActivity implements View.OnClickListener {
    Toolbar toolbar;
    TextView tvWorkerNo;
    TextView tvWorkerName;
    TextView tvDesc;
    TextView tvStartTime;
    TextView tvEndTime;
    TextView tvLeaveType;

    Button btnAcceptLeave;
    Button btnRejectLeave;
    Leave leave;
    String workerName;
    String workerNo;
    String desc;
    String startTime;
    String endTime;
    int id;
    int type;
    GlobalApplication application = GlobalApplication.getInstance();
    SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences("user", MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        initViews(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_admin_deal_leave;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        btnAcceptLeave = findViewById(R.id.btn_accept_leave);
        btnRejectLeave = findViewById(R.id.btn_reject_leave);
        tvDesc = findViewById(R.id.tv_leave_reason);
        tvLeaveType = findViewById(R.id.tv_leaveType);
        tvStartTime = findViewById(R.id.tv_begin_time);
        tvEndTime = findViewById(R.id.tv_end_time);
        tvWorkerName = findViewById(R.id.tv_employee_name);
        Intent intent = getIntent();
        workerName = intent.getStringExtra("workerName");
        workerNo = intent.getStringExtra("workerNo");
        desc = intent.getStringExtra("desc");
        startTime = intent.getStringExtra("startTime");
        endTime = intent.getStringExtra("endTime");
        id = intent.getIntExtra("id", 0);
        type = intent.getIntExtra("type", 0);
        btnAcceptLeave.setOnClickListener(this);
        btnRejectLeave.setOnClickListener(this);
        tvWorkerName.setText(workerName);
        tvLeaveType.setText(application.leaveType.get(type));
        tvStartTime.setText(startTime);
        tvEndTime.setText(endTime);
        tvDesc.setText(desc);
        leave = new Leave();
        leave.setId(id);
        leave.setSubmitterNo(workerNo);
        leave.setSubmitter(workerName);
        leave.setSenderNo(workerNo);
        leave.setSender(workerName);
        leave.setDealer(sp.getString("userName", ""));
        leave.setDealerNo(sp.getString("userNo", ""));
        leave.setStartTime(startTime);
        leave.setEndTime(endTime);
        leave.setLeaveType(type);

    }

    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("处理请假");
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_accept_leave:
                dealLeave(1);
                break;
            case R.id.btn_reject_leave:
                dealLeave(2);
                break;
        }
    }

    private void dealLeave(int operate) {
        leave.setState(operate);
        String s = "";
        if (operate == 1) {
            s="同意请假";
        } else {
            s="拒绝请假";
        }
        String finalS = s;
        RetrofitUtil.getLeaveAPI()
                .dealLeave(leave)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respondBody -> {
                    if (respondBody.getCode() == 200) {
                        Toast.makeText(getApplicationContext(), finalS +"成功！", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                }, throwable -> {});
    }
}