package com.hfut.lianya.courier.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.R;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.Fkpb;
import com.hfut.lianya.bean.Moldqrcode;
import com.hfut.lianya.net.RetrofitUtil;
import com.hfut.lianya.utils.QRCodeUtil;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CourierCompleteContinuousCaptureActivity extends RxBaseActivity {
    private DecoratedBarcodeView barcodeView;
    private BeepManager beepManager;
    private Toolbar toolbar;
    Fkpb fkpb = new Fkpb();
    GlobalApplication application;
    private String currentWorkstation = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = GlobalApplication.getInstance();
        barcodeView = findViewById(R.id.barcode_scanner);
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
        barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeView.initializeFromIntent(getIntent());
        barcodeView.decodeContinuous(callback);//一个语句控制连续扫描
        beepManager = new BeepManager(this);
        initToolBar();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_courier_complete_continuous_capture;
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
                checkResult(result.getText());
            }
            barcodeView.setStatusText(result.getText());
            beepManager.playBeepSoundAndVibrate();
        }
        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };
    private void checkResult(String result) {
        if(result.toCharArray()[0] >= '0' && result.toCharArray()[0] <= '9') {
            getPackageInfo(result);
        } else if(result.startsWith("MQ")){
            getModelInfo(result);
        }
    }
    public void dialog(Fkpb packageInfo) {
        if(packageInfo.getState() != 6) {
            Toast.makeText(this, "状态有误！", Toast.LENGTH_SHORT).show();
        }
        else if(packageInfo.getState() == 6 && currentWorkstation.equals(packageInfo.getNextWorkStation())) {
            new MaterialAlertDialogBuilder(this).setMessage("确认送达？").setPositiveButton("确认", (dialogInterface, i) -> {
            delivered(packageInfo.getPackageId(), currentWorkstation);
            barcodeView.resume();
            dialogInterface.dismiss();}).setNegativeButton("取消", (dialogInterface, i) -> {
            barcodeView.resume();
            dialogInterface.dismiss();
            }).show();
        } else if(packageInfo.getStatus() == 6 && !currentWorkstation.equals(packageInfo.getNextStation())) {
            String s = "本包由" + packageInfo.getCurrentWorkstation() + "派送到" + packageInfo.getNextStation() + "若已到达指定位置，请先扫描目标工作台上的二维码。";
            new MaterialAlertDialogBuilder(this).setMessage(s).setPositiveButton("确认", (dialogInterface, i) -> {
                barcodeView.resume();
                dialogInterface.dismiss();
            }).show();
        } else {
            Toast.makeText(this, "状态有误", Toast.LENGTH_LONG).show();
        }
    }
    public void dialog1(Moldqrcode modelInfo) {
        if(modelInfo.getState() != 6) {
            Toast.makeText(this, "状态有误！", Toast.LENGTH_SHORT).show();
        }
        else if(modelInfo.getState() == 6 && currentWorkstation.equals(modelInfo.getNextPosition())) {
            new MaterialAlertDialogBuilder(this).setMessage("确认送达？").setPositiveButton("确认", (dialogInterface, i) -> {
                deliveredModel(modelInfo.getBarCodeN(), currentWorkstation);
                barcodeView.resume();
                dialogInterface.dismiss();}).setNegativeButton("取消", (dialogInterface, i) -> {
                barcodeView.resume();
                dialogInterface.dismiss();
            }).show();
        } else if(modelInfo.getState() == 6 && !currentWorkstation.equals(modelInfo.getNextPosition())) {
            String s = "本包由" + modelInfo.getCurrentPosition() + "派送到" + modelInfo.getNextPosition() + "若已到达指定位置，请先扫描目标工作台上的二维码。";
            new MaterialAlertDialogBuilder(this).setMessage(s).setPositiveButton("确认", (dialogInterface, i) -> {
                barcodeView.resume();
                dialogInterface.dismiss();
            }).show();
        } else {
            Toast.makeText(this, "状态有误", Toast.LENGTH_LONG).show();
        }
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
                    dialog(respondBody.getData());
                }, throwable -> {});
    }
    public void getModelInfo(String modelId) {
        RetrofitUtil.getDashboardAPI()
                .getModelInfo(modelId)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respondBody->{
                    dialog1(respondBody.getData());
                }, throwable -> {});
    }
    private void delivered(String packageId, String currentWorkstation) {
        RetrofitUtil.getDashboardAPI()
                .delivered(packageId, currentWorkstation)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respondBody->{
                    if (respondBody.getCode() != 200) {
                        Toast.makeText(getApplicationContext(), "系统错误！", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "已送达！", Toast.LENGTH_LONG).show();
                    }
                }, throwable -> {});
    }
    private void deliveredModel(String modelId, String currentWorkstation) {
        RetrofitUtil.getDashboardAPI()
                .deliveredModel(modelId, currentWorkstation)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respondBody->{
                    if (respondBody.getCode() != 200) {
                        Toast.makeText(getApplicationContext(), "系统错误！", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "已送达！", Toast.LENGTH_LONG).show();
                    }
                }, throwable -> {});
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
