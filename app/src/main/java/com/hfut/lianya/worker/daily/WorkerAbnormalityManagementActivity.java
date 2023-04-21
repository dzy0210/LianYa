package com.hfut.lianya.worker.daily;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.R;
import com.hfut.lianya.adapters.AbnormalityAdapter;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.Abnormality;
import com.hfut.lianya.net.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WorkerAbnormalityManagementActivity extends RxBaseActivity implements View.OnClickListener {
    private Button btnNewAbnormality;
    private RecyclerView rvAbnormality;
    private Toolbar toolbar;
    private TextView tvHistoryAbnormality;
    List<Abnormality> list = new ArrayList<>();
    SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences("user", MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("testtest", "onCreate");
//        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("testtest", "onResume");
        loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_worker_abnormality_management;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        btnNewAbnormality = findViewById(R.id.btn_new_abnormality);
        rvAbnormality = findViewById(R.id.rv_abnormality);
        tvHistoryAbnormality = findViewById(R.id.tv_history_abnormality);
        btnNewAbnormality.setOnClickListener(this);
        tvHistoryAbnormality.setOnClickListener(this);
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

    public void loadData() {
        super.loadData();
        list.clear();
        RetrofitUtil.getAbnormalityAPI()
                .getDealingAbnormality(sp.getString("userNo", ""))
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(respond -> respond.getData())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(abnormalityList -> {
                    list.addAll(abnormalityList);
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
        adapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            Intent intent = new Intent(getApplicationContext(), NewAbnormalityActivity.class);
            intent.putExtra("id", list.get(i).getId());
            intent.putExtra("abnormalityState", list.get(i).getState());
            intent.putExtra("receiverNo", list.get(i).getReceiverNo());
            intent.putExtra("receiver", list.get(i).getReceiver());
            intent.putExtra("abnormalityType", list.get(i).getAbnormalityType());
            intent.putExtra("abnormalityDesc", list.get(i).getAbnormalityDesc());
            intent.putExtra("sendTime", list.get(i).getSendTime());
            intent.putExtra("solveTime", list.get(i).getSolveTime());
            startActivity(intent);
        });
        rvAbnormality.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvAbnormality.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rvAbnormality.setAdapter(adapter);
    }

    public void newAbnormality() {
        Intent intent = new Intent(this, NewAbnormalityActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_new_abnormality:
                newAbnormality();
                break;
            case R.id.tv_history_abnormality:
                startHistoryAbnormality();
                break;
        }
    }

    public void getAbnormalityDetails(Abnormality abnormality) {
        Intent intent = new Intent(this, NewAbnormalityActivity.class);
        startActivity(intent);
    }

    public void startHistoryAbnormality() {
        Intent intent = new Intent(this, HistoryAbnormalityActivity.class);
        startActivity(intent);
    }
}