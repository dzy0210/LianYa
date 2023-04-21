package com.hfut.lianya.administrator.daily;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.R;
import com.hfut.lianya.abnormalitymanager.dashboard.DealAbnormalityActivity;
import com.hfut.lianya.adapters.AbnormalityAdapter;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.Abnormality;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminAbnormalityManagementActivity extends RxBaseActivity {
    Toolbar toolbar;
    List<Abnormality> list = new ArrayList<>();
    RecyclerView rvAbnormality;

    SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences("user", MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_admin_abnormality_management;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("异常管理");
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
        RetrofitUtil.getAbnormalityAPI()
                .getDealingAbnormalityByDealer(sp.getString("userNo", ""))
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(abnormalities -> {
                    list.addAll(abnormalities);
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
        AbnormalityAdapter adapter = new AbnormalityAdapter();
        adapter.submitList(list);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener<Abnormality>() {
            @Override
            public void onClick(@NonNull BaseQuickAdapter<Abnormality, ?> baseQuickAdapter, @NonNull View view, int i) {
                Intent intent = new Intent(getApplicationContext(), DealAbnormalityActivity.class);
                intent.putExtra("sender", list.get(i).getSender());
                intent.putExtra("senderNo", list.get(i).getSenderNo());
                intent.putExtra("workstation", list.get(i).getWorkstation());
                intent.putExtra("packageId", list.get(i).getPackageId());
                intent.putExtra("desc", list.get(i).getAbnormalityDesc());
                intent.putExtra("time", list.get(i).getSendTime());
                intent.putExtra("type", list.get(i).getAbnormalityType());
                intent.putExtra("id", list.get(i).getId());
                intent.putExtra("state", list.get(i).getState());
                startActivity(intent);
            }
        });
        rvAbnormality = findViewById(R.id.rv_abnormality_request);
        rvAbnormality.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvAbnormality.setLayoutManager(new LinearLayoutManager(this));
        rvAbnormality.setAdapter(adapter);
    }
}