package com.hfut.lianya.administrator.daily;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.hfut.lianya.R;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.Leave;
import com.hfut.lianya.net.RetrofitUtil;

public class AdminDealLeaveActivity extends RxBaseActivity implements View.OnClickListener {
    Toolbar toolbar;
    Button btnAcceptLeave;
    Button btnRejectLeave;
    Leave leave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_abnormality);
        initToolBar();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_deal_abnormality;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        btnAcceptLeave = findViewById(R.id.btn_accept_leave);
        btnRejectLeave = findViewById(R.id.btn_reject_leave);
        btnAcceptLeave.setOnClickListener(this);
        btnRejectLeave.setOnClickListener(this);
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
                acceptLeave();
                break;
            case R.id.btn_reject_leave:
                rejectLeave();
                break;
        }
    }

    private void acceptLeave() {
        RetrofitUtil.getLeaveAPI().dealLeave(leave, '0');
    }

    private void rejectLeave() {
    }
}