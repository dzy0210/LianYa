package com.hfut.lianya.administrator.daily;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hfut.lianya.R;
import com.hfut.lianya.adapters.LackAdapter;
import com.hfut.lianya.adapters.LeaveRequestAdapter;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.Lack;
import com.hfut.lianya.bean.Leave;
import com.hfut.lianya.global.GlobalVariable;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminLackManagementActivity extends RxBaseActivity {
    Toolbar toolbar;
    RecyclerView rvLack;
    List<Lack> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        loadData();
        initViews(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_admin_lack_management;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("缺少管理");
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
        RetrofitUtil.getCommonAPI()
                .getLack()
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lacks -> {
                    list.addAll(lacks);
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
        LackAdapter adapter = new LackAdapter();
        adapter.submitList(list);
        rvLack = findViewById(R.id.rv_lack);
        rvLack.setLayoutManager(new LinearLayoutManager(this));
        rvLack.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvLack.setAdapter(adapter);
    }
}