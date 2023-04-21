package com.hfut.lianya.administrator.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.cretin.www.cretinautoupdatelibrary.interfaces.AppDownloadListener;
import com.cretin.www.cretinautoupdatelibrary.interfaces.MD5CheckListener;
import com.cretin.www.cretinautoupdatelibrary.model.DownloadInfo;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUpdateUtils;
import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.LoginActivity;
import com.hfut.lianya.R;
import com.hfut.lianya.administrator.dashboard.AdminHistoryTaskActivity;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.courier.dashboard.CourierBedContinuousCaptureActivity;
import com.hfut.lianya.databinding.FragmentAdminHomeBinding;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;
import com.hfut.lianya.worker.daily.HistoryAbnormalityActivity;
import com.hfut.lianya.worker.daily.HistoryLeaveActivity;
import com.hfut.lianya.worker.home.WorkerHistoryTaskActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminHomeFragment extends RxLazyFragment implements View.OnClickListener {

    private FragmentAdminHomeBinding binding;
    private CardView cvPersonalInfo;
    private CardView cvHistoryTask;
    private CardView cvHistoryAbnormality;
    private CardView cvHistoryLeave;
    private CardView cvLogout;
    private CardView cvBedDelivered;
    private CardView cvCheckUpdate;
    private CardView cvHistoryEfficiency;
    private TextView tvUserNo;
    private TextView tvUserName;
    SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences("user", Context.MODE_PRIVATE);
    int userType = sp.getInt("userType", 0);
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_admin_home;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdminHomeBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }

    @Override
    public void finishCreateView(Bundle state) {

    }

    public void initView() {
        cvPersonalInfo = binding.cvPersonalInfo;
        cvHistoryAbnormality = binding.cvHistoryAbnormality;
        cvHistoryLeave = binding.cvHistoryLeave;
        cvHistoryTask = binding.cvHistoryTask;
        cvLogout = binding.cvLogout;
        cvBedDelivered = binding.cvBedDelivered;
        cvCheckUpdate = binding.cvCheckUpdate;
        cvHistoryEfficiency = binding.cvHistoryEfficiency;
        tvUserNo = binding.tvWorkerNo;
        tvUserName = binding.tvWorkerName;
        tvUserName.setText(sp.getString("userName", ""));
        tvUserNo.setText(sp.getString("userNo", ""));
        cvPersonalInfo.setOnClickListener(this);
        cvHistoryAbnormality.setOnClickListener(this);
        cvHistoryLeave.setOnClickListener(this);
        cvHistoryTask.setOnClickListener(this);
        cvLogout.setOnClickListener(this);
        cvBedDelivered.setOnClickListener(this);
        cvCheckUpdate.setOnClickListener(this);
        cvHistoryEfficiency.setOnClickListener(this);
        cvBedDelivered.setVisibility(View.GONE);
        if(userType == 0) {
            cvBedDelivered.setVisibility(View.GONE);
        } else if(userType == 1) {
            cvHistoryAbnormality.setVisibility(View.GONE);
            cvHistoryLeave.setVisibility(View.GONE);
            cvHistoryTask.setVisibility(View.GONE);
            cvHistoryEfficiency.setVisibility(View.GONE);
        } else if(userType == 2) {
            cvBedDelivered.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_personal_info:
                startActivity(new Intent(getActivity(), PersonalDetailInfoActivity.class));
                break;
            case R.id.cv_history_abnormality:
                startActivity(new Intent(getActivity(), HistoryAbnormalityActivity.class));
                break;
            case R.id.cv_history_task: {
                if(userType == 0) {
                    startActivity(new Intent(getActivity(), WorkerHistoryTaskActivity.class));
                } else if (userType == 2) {
                    startActivity(new Intent(getActivity(), AdminHistoryTaskActivity.class));
                }
            }
                break;
            case R.id.cv_history_leave:
                startActivity(new Intent(getActivity(), HistoryLeaveActivity.class));
                break;
            case R.id.cv_logout:
                logout();
                break;
            case R.id.cv_bed_delivered:
                postTest();
                break;
            case R.id.cv_check_update:
                update();
                break;
        }
    }

    private void postTest() {
        RetrofitUtil.getDashboardAPI().postTest("aaaaaa")
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respond -> {
                    Toast.makeText(getApplicationContext(), respond.toString(), Toast.LENGTH_LONG).show();
                    finishTask();
                }, throwable -> {});
    }

    private void bedDelivered() {
        startActivity(new Intent(getApplicationContext(), CourierBedContinuousCaptureActivity.class));
    }

    private void logout() {
        SharedPreferences sharedPreferences = GlobalApplication.getInstance().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        getActivity().finish();
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
}