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

import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

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
        checkUpdate();
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
                        dialog();
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
    public void downUrl() {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载安装包，请稍等...");
        pd.setCancelable(false);
        pd.setMax(100);
        pd.setProgress(0);
        pd.show();
        DownloadUtil.get().download("", getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), "",new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                pd.dismiss();
                installAPK(file);
            }

            @Override
            public void onDownloading(int progress) {
                pd.setProgress(progress);
            }

            @Override
            public void onDownloadFailed(Exception e) {

            }
        });
    }

    private void installAPK(File apk) {
        Intent intent=new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri apkFileUri;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            apkFileUri= FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID+".provider",apk);
        }else{
            apkFileUri=Uri.fromFile(apk);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(apkFileUri,"application/vnd.android.package-archive");
        Log.i("tag","开始安装");
        try {
            this.startActivity(intent);
//            finish();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setMessage("确认退出？");
        builder.setMessage("发现新版本，点击确认开始下载");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                downUrl();
            }
        });
        builder.create().show();
    }
}

