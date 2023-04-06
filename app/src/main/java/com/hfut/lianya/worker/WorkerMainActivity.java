package com.hfut.lianya.worker;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.R;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.InitData;
import com.hfut.lianya.net.RetrofitUtil;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WorkerMainActivity extends RxBaseActivity {

    BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(navView, navController);
        initData();
        BadgeDrawable badgeDrawable = navView.getOrCreateBadge(R.id.navigation_notifications);
        badgeDrawable.setNumber(100);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_worker_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }
    public void initData() {
        RetrofitUtil.getCommonAPI()
                .getInitData()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respondBody -> {
                    InitData data = respondBody.getData();
                    GlobalApplication.getInstance().abnormalityNap.clear();
                    GlobalApplication.getInstance().abnormalityNap.putAll(data.getAbnormalityMap());
                    GlobalApplication.getInstance().leaveType.clear();
                    GlobalApplication.getInstance().leaveType.addAll(data.getLeaveType());
                }, throwable -> {});
    }
}