package com.hfut.lianya.courier;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.cretin.www.cretinautoupdatelibrary.interfaces.AppDownloadListener;
import com.cretin.www.cretinautoupdatelibrary.interfaces.MD5CheckListener;
import com.cretin.www.cretinautoupdatelibrary.model.DownloadInfo;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUpdateUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hfut.lianya.BuildConfig;
import com.hfut.lianya.DownloadUtil;
import com.hfut.lianya.Permission;
import com.hfut.lianya.R;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.net.RetrofitUtil;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CourierMainActivity extends RxBaseActivity {

    int version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Permission.checkPermission(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(navView, navController);
//        checkUpdate();

    }

    private void update() {
        DownloadInfo info = new DownloadInfo().setApkUrl("http://101.34.129.170:90/lianya.apk")
                .setFileSize(31338250)
                .setProdVersionCode(25)
                .setProdVersionName("2.3.1")
//                .setMd5Check("68919BF998C29DA3F5BD2C0346281AC0")
                .setForceUpdateFlag(1)
                .setUpdateLog("1、优化细节和体验，更加稳定\n2、引入大量优质用户\r\n3、修复已知bug\n4、风格修改");
        AppUpdateUtils.getInstance()
                .addMd5CheckListener(new MD5CheckListener() {
                    @Override
                    public void fileMd5CheckFail(String originMD5, String localMD5) {

                    }

                    @Override
                    public void fileMd5CheckSuccess() {

                    }
                })//添加MD5检查更新
                .addAppDownloadListener(new AppDownloadListener() {
                    @Override
                    public void downloading(int progress) {

                    }

                    @Override
                    public void downloadFail(String msg) {

                    }

                    @Override
                    public void downloadComplete(String path) {

                    }

                    @Override
                    public void downloadStart() {

                    }

                    @Override
                    public void reDownload() {

                    }

                    @Override
                    public void pause() {

                    }
                })//添加文件下载监听
                .checkUpdate(info);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_courier_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

    void checkUpdate() {
        RetrofitUtil.getCommonAPI()
                .getVersion()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respondBody->{
                    version = respondBody.getData();
                    if(version > getLocalVersion()) {

                    }
                }, throwable -> {});
    }

    int getLocalVersion() {
        int localVersion = 0;
        try {
            localVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }


}

