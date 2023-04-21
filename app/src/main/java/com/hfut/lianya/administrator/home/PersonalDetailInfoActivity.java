package com.hfut.lianya.administrator.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.R;

public class PersonalDetailInfoActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvWorkerName;
    TextView tvWorkerNo;
    CardView cvModifyPassword;
    Toolbar toolbar;
    SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences("user",MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail_info);
        initView();
        initToolBar();
    }
    public void initView() {
        tvWorkerName = findViewById(R.id.tv_worker_name);
        tvWorkerNo = findViewById(R.id.tv_worker_no);
        cvModifyPassword = findViewById(R.id.cv_modify_password);
        toolbar = findViewById(R.id.toolbar);
        cvModifyPassword.setOnClickListener(this);
        tvWorkerNo.setText(sp.getString("userNo", ""));
        tvWorkerName.setText(sp.getString("userName", ""));
    }
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("个人信息");
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
            case R.id.cv_modify_password:
                startActivity(new Intent(this, ModifyPasswordActivity.class));
                break;
        }
    }
}