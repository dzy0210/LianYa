package com.hfut.lianya.worker.dashboard;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.Permission;
import com.hfut.lianya.R;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.Fkpb;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WorkerContinuousCaptureActivity extends RxBaseActivity {
    private DecoratedBarcodeView barcodeView;
    private BeepManager beepManager;
    private Toolbar toolbar;
    Fkpb fkpb = new Fkpb();
    GlobalApplication application;
    SharedPreferences sharedPreferences;
    private static final String[] permission = new String[]{
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = GlobalApplication.getInstance();
        sharedPreferences = application.getSharedPreferences("user", MODE_PRIVATE);
        barcodeView = findViewById(R.id.barcode_scanner);
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
        barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeView.initializeFromIntent(getIntent());
        barcodeView.decodeContinuous(callback);//一个语句控制连续扫描
        beepManager = new BeepManager(this);
        initToolBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Permission.checkPermission(this);
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
    public int getLayoutId() {
        return R.layout.activity_worker_continuous_capture;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }
    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar_scan);
        toolbar.setTitle("扫描");
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

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if(result.getText() == null ) {
                return;
            }else{
                barcodeView.pause();
                getPackageInfo(result.getText());
            }
            barcodeView.setStatusText(result.getText());
            beepManager.playBeepSoundAndVibrate();
        }
        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    protected void dialog(Fkpb packageInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String s = "";
        if(packageInfo.getStatus() == 0) {
            s = "本包为" + packageInfo.getLine() +  "确认完成上包并开工？";
        }
        builder.setMessage(s);

        builder.setPositiveButton("确认", (dialog, arg1) -> {
            startWork(packageInfo.getPackageId(), sharedPreferences.getString("userNo", ""), application.totalPauseTime);
            barcodeView.resume();
            dialog.dismiss();
        });
        //添加取消
        builder.setNegativeButton("取消", (dialog, which) -> {
            barcodeView.resume();
            dialog.dismiss();
        });
        builder.create().show();
    }

    private void startWork(String packageId, String userNo, long pauseTime) {
        RetrofitUtil.getDashboardAPI()
                .start(packageId, userNo, pauseTime)
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                    if(code == 200){
                        Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_LONG).show();
                    }
                    finishTask();
                }, throwable -> {});
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    @Override
    public void finishTask() {
        super.finishTask();
        application.totalPauseTime = 0;
        application.pauseTime = 0;
    }

    public void pause(View view) {
        barcodeView.pause();
    }

    public void resume(View view) {
        barcodeView.resume();
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    public void getPackageInfo(String packageId) {
        RetrofitUtil.getDashboardAPI()
                .getPackageInfo(packageId)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respondBody->{
                    fkpb = respondBody.getData();
                    dialog(fkpb);
                }, throwable -> {});
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
