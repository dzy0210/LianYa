package com.hfut.lianya.administrator.daily;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.R;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.Leave;
import com.hfut.lianya.bean.WorkStation;
import com.hfut.lianya.bean.WorkerInfo;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;
import com.hfut.lianya.utils.DateUtil;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminLeaveForWorkerActivity extends RxBaseActivity implements View.OnClickListener {
    Toolbar toolbar;
    Button btnSubmitLeave;

    TextView tvBeginTime;
    TextView tvEndTime;
    NiceSpinner spWorkerName;
    NiceSpinner spWorkstation;
    NiceSpinner spLeaveType;
    NiceSpinner spIsComplete;
    TimePickerView pvBeginTime, pvEndTime;
    List<WorkerInfo> workerInfoList = new ArrayList<>();
    List<WorkStation> workStationList = new ArrayList<>();
    GlobalApplication application = GlobalApplication.getInstance();
    SharedPreferences sp = application.getSharedPreferences("user", MODE_PRIVATE);
    Calendar now= Calendar.getInstance();
    String dateTime = DateUtil.dateToString(now.getTime(), DateUtil.FORMAT_TIMESTAMP);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        initViews(savedInstanceState);
        initTimePicker1();
        initTimePicker2();
        initSpinner();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_admin_leave_for_worker;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        spWorkstation = findViewById(R.id.sp_workstation);
        spWorkerName = findViewById(R.id.sp_worker_name);
        spLeaveType = findViewById(R.id.sp_leave_type);
        spIsComplete = findViewById(R.id.sp_is_complete);
        tvBeginTime = findViewById(R.id.tv_begin_time);
        tvEndTime = findViewById(R.id.tv_end_time);
        btnSubmitLeave = findViewById(R.id.btn_submit_leave);
        Log.d("test", dateTime);
        tvBeginTime.setText(dateTime);
        tvEndTime.setText(dateTime);
        btnSubmitLeave.setOnClickListener(this);
        tvBeginTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
    }

    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("代请假管理");
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

    private void loadWorkerNameSpinner() {
//        spWorkerName.attachDataSource(Arrays.asList(application.WORKER_NAME));
    }
    private void initTimePicker1() {//Dialog 模式下，在底部弹出
        pvBeginTime = new TimePickerBuilder(this, (date, v) -> tvBeginTime.setText(DateUtil.dateToString(date, DateUtil.FORMAT_TIMESTAMP)))
                .setType(new boolean[]{true, true, true, true, true, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setCancelText("取消")
                .setSubmitText("确认")
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();

        Dialog mDialog = pvBeginTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvBeginTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }
    private void initTimePicker2() {//Dialog 模式下，在底部弹出
        pvEndTime = new TimePickerBuilder(this, (date, v) -> tvEndTime.setText(DateUtil.dateToString(date, DateUtil.FORMAT_TIMESTAMP)))
                .setType(new boolean[]{true, true, true, true, true, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setCancelText("取消")
                .setSubmitText("确认")
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();

        Dialog mDialog = pvEndTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvEndTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }
    private void initSpinner() {
//        spWorkerName.attachDataSource(Arrays.asList(application.WORKER_NAME));
//        spWorkstation.attachDataSource(Arrays.asList(application.AREA));
//        spLeaveType.attachDataSource(Arrays.asList(application.LEAVE_TYPE));
//        spIsComplete.attachDataSource(Arrays.asList(application.IS_COMPLETE));
    }

    private void newLeave() {
        Leave leave = new Leave();
        RetrofitUtil.getLeaveAPI()
                .submitLeave(leave)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                    if(code == 200){
                        Toast.makeText(this, "提交成功", Toast.LENGTH_LONG);
                        onBackPressed();
                    }
                    finishTask();
                }, throwable -> {});
    }

    private void selectStartTime(View view) {
        pvBeginTime.show(view);
    }

    private void selectEndTime(View view) {
        pvEndTime.show(view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_leave:
                newLeave();
                break;
            case R.id.tv_begin_time:
                selectStartTime(view);
                break;
            case R.id.tv_end_time:
                selectEndTime(view);
                break;
        }
    }

}