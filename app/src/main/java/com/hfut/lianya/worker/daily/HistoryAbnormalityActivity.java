package com.hfut.lianya.worker.daily;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.R;
import com.hfut.lianya.adapters.AbnormalityAdapter;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.Abnormality;
import com.hfut.lianya.global.GlobalVariable;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HistoryAbnormalityActivity extends RxBaseActivity {
    Toolbar toolbar;
    RecyclerView rvHistoryAbnormality;
    private List<Abnormality> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_worker_history_abnormality;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("历史异常");
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
                .getHistoryAbnormality(GlobalVariable.USERNO)
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(historyAbnormalities -> {
                    list.addAll(historyAbnormalities);
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
        rvHistoryAbnormality = findViewById(R.id.rv_history_abnormality);
        AbnormalityAdapter adapter = new AbnormalityAdapter();
        adapter.submitList(list);
        rvHistoryAbnormality.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rvHistoryAbnormality.setLayoutManager(new LinearLayoutManager(this));
        rvHistoryAbnormality.setAdapter(adapter);
    }
}