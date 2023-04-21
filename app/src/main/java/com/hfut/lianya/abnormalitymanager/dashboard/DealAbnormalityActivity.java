package com.hfut.lianya.abnormalitymanager.dashboard;

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
import com.hfut.lianya.bean.Abnormality;
import com.hfut.lianya.net.RetrofitUtil;
import com.hfut.lianya.utils.DateUtil;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DealAbnormalityActivity extends RxBaseActivity implements View.OnClickListener {
    Abnormality abnormality;
    Toolbar toolbar;
    Button btnAccept;
    Button btnReject;
    Button btnComplete;
    TextView tvSender;
    TextView tvWorkstation;
    TextView tvTime;
    TextView tvType;
    TextView tvPackageId;
    TextView tvDesc;
    int id;
    String sender;
    String senderNo;
    String workstation;
    String packageId;
    String desc;
    int type;
    String time;
    int state;
    GlobalApplication application = GlobalApplication.getInstance();
    SharedPreferences sp = application.getSharedPreferences("user", MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_abnormality);
        initViews(savedInstanceState);
        initToolBar();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_deal_abnormality;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        tvDesc = findViewById(R.id.tv_abnormality_desc);
        tvPackageId = findViewById(R.id.tv_blemish_package_id);
        tvSender = findViewById(R.id.tv_sender_name);
        tvTime = findViewById(R.id.tv_time);
        tvType = findViewById(R.id.tv_abnormality_type);
        tvWorkstation = findViewById(R.id.tv_workstation);
        btnAccept = findViewById(R.id.btn_accept);
        btnReject = findViewById(R.id.btn_reject);
        btnComplete = findViewById(R.id.btn_complete);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        sender = intent.getStringExtra("sender");
        senderNo = intent.getStringExtra("senderNo");
        workstation = intent.getStringExtra("workstation");
        packageId = intent.getStringExtra("packageId");
        desc = intent.getStringExtra("desc");
        time = intent.getStringExtra("time");
        type = intent.getIntExtra("type", 0);
        state = intent.getIntExtra("state", 0);
        abnormality = new Abnormality(id, 0, senderNo, sender, sp.getString("userNo", ""), sp.getString("userName", ""), workstation, packageId, type, desc, time, DateUtil.getCurrentTime(DateUtil.FORMAT_DATE_TIME_SECOND));
        if(state == 0) {
            btnComplete.setVisibility(View.GONE);
        } else if (state == 1) {
            btnAccept.setVisibility(View.GONE);
            btnReject.setVisibility(View.GONE);
        } else {
            btnAccept.setVisibility(View.GONE);
            btnReject.setVisibility(View.GONE);
            btnComplete.setVisibility(View.GONE);
        }
        tvWorkstation.setText(workstation);
        tvType.setText(application.abnormalityType.get(type));
        tvDesc.setText(desc);
        tvPackageId.setText(packageId);
        tvSender.setText(sender);
        tvTime.setText(time);
        btnAccept.setOnClickListener(this);
        btnReject.setOnClickListener(this);
        btnComplete.setOnClickListener(this);
    }

    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("处理异常");
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
            case R.id.btn_accept: {
                abnormality.setState(1);
                deal("接受");
                break;
            }
            case R.id.btn_reject: {
                abnormality.setState(2);
                deal("拒绝");
                break;
            }
            case R.id.btn_complete: {
                abnormality.setState(3);
                deal("解决");
                break;
            }
        }
    }
    private void deal(String operate) {
        RetrofitUtil.getAbnormalityAPI()
                .dealAbnormality(abnormality)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respondBody -> {
                    if (respondBody.getCode() == 200) {
                        Toast.makeText(getApplicationContext(), operate+"成功！", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                }, throwable -> {});
    }
}