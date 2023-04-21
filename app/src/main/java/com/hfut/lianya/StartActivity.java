package com.hfut.lianya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.cretin.www.cretinautoupdatelibrary.model.TypeConfig;
import com.cretin.www.cretinautoupdatelibrary.model.UpdateConfig;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUpdateUtils;
import com.hfut.lianya.abnormalitymanager.AbnormalityManagerMainActivity;
import com.hfut.lianya.administrator.AdminMainActivity;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.courier.CourierMainActivity;
import com.hfut.lianya.utils.OkHttp3Connection;
import com.hfut.lianya.worker.WorkerMainActivity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class StartActivity extends RxBaseActivity {

    SharedPreferences sharedPreferences = GlobalApplication.getInstance().getSharedPreferences("user", MODE_PRIVATE);
    void updateInit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30_000, TimeUnit.SECONDS)
                .readTimeout(30_000, TimeUnit.SECONDS)
                .writeTimeout(30_000, TimeUnit.SECONDS)
                //如果你需要信任所有的证书，可解决根证书不被信任导致无法下载的问题 start
//                .sslSocketFactory(SSLUtils.createSSLSocketFactory())
//                .hostnameVerifier(new SSLUtils.TrustAllHostnameVerifier())
                //如果你需要信任所有的证书，可解决根证书不被信任导致无法下载的问题 end
                .retryOnConnectionFailure(true);
        UpdateConfig updateConfig = new UpdateConfig()
                .setDebug(true)//是否是Debug模式
                .setDataSourceType(TypeConfig.DATA_SOURCE_TYPE_MODEL)//设置获取更新信息的方式
                .setShowNotification(true)//配置更新的过程中是否在通知栏显示进度
//            .setNotificationIconRes(R.mipmap.download_icon)//配置通知栏显示的图标
                .setUiThemeType(TypeConfig.UI_THEME_AUTO)//配置UI的样式，一种有12种样式可供选择
                .setAutoDownloadBackground(false)//是否需要后台静默下载，如果设置为true，则调用checkUpdate方法之后会直接下载安装，不会弹出更新页面。当你选择UI样式为TypeConfig.UI_THEME_CUSTOM，静默安装失效，您需要在自定义的Activity中自主实现静默下载，使用这种方式的时候建议setShowNotification(false)，这样基本上用户就会对下载无感知了
//            .setCustomActivityClass(CustomActivity.class)//如果你选择的UI样式为TypeConfig.UI_THEME_CUSTOM，那么你需要自定义一个Activity继承自RootActivity，并参照demo实现功能，在此处填写自定义Activity的class
                .setNeedFileMD5Check(false)//是否需要进行文件的MD5检验，如果开启需要提供文件本身正确的MD5校验码，DEMO中提供了获取文件MD5检验码的工具页面，也提供了加密工具类Md5Utils
            .setCustomDownloadConnectionCreator(new OkHttp3Connection.Creator(builder));//如果你想使用okhttp作为下载的载体，可以使用如下代码创建一个OkHttpClient，并使用demo中提供的OkHttp3Connection构建一个ConnectionCreator传入，在这里可以配置信任所有的证书，可解决根证书不被信任导致无法下载apk的问题
        AppUpdateUtils.init(getApplication(), updateConfig);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Permission.REQUEST_CODE) {
            for(int grantResult : grantResults) {
                if(grantResult != PackageManager.PERMISSION_GRANTED) {
                    this.finish();
                    return;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        updateInit();
        checkUpdate();
        initViews(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        if(sharedPreferences.getBoolean("isLogged", false)) {
            int userType = sharedPreferences.getInt("userType", -1);
            switch (userType) {
                case 0:
                    startWorker();
                    break;
                case 1:
                    startCourier();
                    break;
                case 2:
                    startAdministrator();
                    break;
                case 3:
                    startAbnormalityManager();
                    break;
                default:
                    startLogin();
                    break;
            }
            finish();
        } else {
            startLogin();
            finish();
        }
    }

    @Override
    public void initToolBar() {

    }

    private void startAdministrator() {
        Intent intent = new Intent(this, AdminMainActivity.class);
        startActivity(intent);
    }
    private void startCourier() {
        Intent intent = new Intent(this, CourierMainActivity.class);
        startActivity(intent);
    }

    private void startWorker() {
        Intent intent = new Intent(this, WorkerMainActivity.class);
        startActivity(intent);
    }
    private void startAbnormalityManager() {
        Intent intent = new Intent(this, AbnormalityManagerMainActivity.class);
        startActivity(intent);
    }
    private void startLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    void checkUpdate() {
//        RetrofitUtil.getCommonAPI()
//                .getVersion()
//                .compose(bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(respondBody->{
//                    if(respondBody.getData() > getLocalVersion()) {
//                        update();
//                    }
//                }, throwable -> {});
    }


}