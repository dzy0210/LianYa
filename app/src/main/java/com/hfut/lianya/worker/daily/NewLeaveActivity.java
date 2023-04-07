package com.hfut.lianya.worker.daily;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.R;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.global.GlobalVariable;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;
import com.hfut.lianya.bean.Leave;
import com.hfut.lianya.utils.DateUtil;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class NewLeaveActivity extends RxBaseActivity implements  View.OnClickListener {
    Toolbar toolbar;
    NiceSpinner spLeaveType;
    NiceSpinner spIsComplete;
    TextView tvLeaveStartTime;
    TextView tvLeaveEndTime;
    Button btnSubmit;
    TextView tvLeaveSender;
    Leave leave = new Leave();
    TextView tvCancelLeave;
    TextView tvLeaveManage;
    EditText etLeaveDesc;
    Calendar now= Calendar.getInstance();
    String dateTime = DateUtil.dateToString(now.getTime(), DateUtil.FORMAT_TIMESTAMP);
    List<String> leaveType = new LinkedList<>(Arrays.asList(GlobalVariable.LEAVE_TYPE));
    TimePickerView pvStartTime, pvEndTime;
    GlobalApplication application = GlobalApplication.getInstance();
    SharedPreferences sp = application.getSharedPreferences("user", MODE_PRIVATE);
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        initViews(savedInstanceState);
        initTimePicker1();
        initTimePicker2();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_worker_new_leave;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        spLeaveType = findViewById(R.id.sp_leave_type);
        spIsComplete = findViewById(R.id.sp_is_complete);
        tvLeaveSender = findViewById(R.id.tv_leave_sender);
        tvLeaveStartTime = findViewById(R.id.tv_leave_start_time);
        tvLeaveEndTime = findViewById(R.id.tv_leave_end_time);
        btnSubmit = findViewById(R.id.btn_submit);
        tvCancelLeave = findViewById(R.id.tv_cancel_leave);
        tvLeaveManage = findViewById(R.id.tv_leave_manage);
        etLeaveDesc = findViewById(R.id.et_leave_desc);
        tvLeaveStartTime.setOnClickListener(this);
        tvLeaveEndTime.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        tvCancelLeave.setOnClickListener(this);
        tvLeaveStartTime.setText(dateTime);
        tvLeaveEndTime.setText(dateTime);
        tvLeaveSender.setText(GlobalVariable.USERNAME);
        spLeaveType.attachDataSource(leaveType);
        spLeaveType.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
            }
        });
        spIsComplete.attachDataSource(Arrays.asList(GlobalVariable.IS_COMPLETE));
        Intent intent = getIntent();
        if(intent.getStringExtra("startTime") != null) {
            id = intent.getIntExtra("id", 0);
            int leaveState = intent.getIntExtra("leaveState", 0);
            int type = intent.getIntExtra("leaveType", 0);
            String leaveStartTime = intent.getStringExtra("startTime");
            String leaveEndTime = intent.getStringExtra("endTime");
            int isComplete = intent.getIntExtra("isComplete", 0);
            String leaveDesc = intent.getStringExtra("leaveDesc");
            spLeaveType.setSelectedIndex(type - '0');
            spIsComplete.setSelectedIndex(isComplete-'0');
            tvLeaveStartTime.setText(leaveStartTime);
            tvLeaveEndTime.setText(leaveEndTime);
            etLeaveDesc.setText(leaveDesc);
            tvLeaveManage.setText("请假详情");
            tvLeaveSender.setText(sp.getString("userName", ""));
            btnSubmit.setVisibility(View.GONE);

            spLeaveType.setEnabled(false);
            spIsComplete.setEnabled(false);
            tvLeaveStartTime.setEnabled(false);
            tvLeaveEndTime.setEnabled(false);
            etLeaveDesc.setEnabled(false);
            if(leaveState == '0') {
                tvCancelLeave.setVisibility(View.VISIBLE);
                spLeaveType.setEnabled(true);
                spIsComplete.setEnabled(true);
                tvLeaveStartTime.setEnabled(true);
                tvLeaveEndTime.setEnabled(true);
                etLeaveDesc.setEnabled(true);
                btnSubmit.setVisibility(View.VISIBLE);
            }
        }
    }

    private void cancelLeave() {
        leave.setId(id);
        RetrofitUtil.getLeaveAPI().cancelLeave(leave)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                    if(code == 200) {
                        Toast.makeText(this, "撤回成功", Toast.LENGTH_LONG).show();
                    }
                    finishTask();
                }, throwable -> {});
    }

    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("新增请假");
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

    private void initTimePicker1() {//Dialog 模式下，在底部弹出
        pvStartTime = new TimePickerBuilder(this, (date, v) -> tvLeaveStartTime.setText(DateUtil.dateToString(date, DateUtil.FORMAT_TIMESTAMP)))
                .setType(new boolean[]{true, true, true, true, true, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setCancelText("取消")
                .setSubmitText("确认")
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();

        Dialog mDialog = pvStartTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvStartTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }
    private void initTimePicker2() {//Dialog 模式下，在底部弹出
        pvEndTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvLeaveEndTime.setText(DateUtil.dateToString(date, DateUtil.FORMAT_TIMESTAMP));
            }
        }).setType(new boolean[]{true, true, true, true, true, false})
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
    private void submit() {
        leave.setLeaveDesc(etLeaveDesc.getText().toString());
        leave.setLeaveType(spLeaveType.getSelectedIndex());
        leave.setSender(tvLeaveSender.getText().toString());
        leave.setSenderNo(sp.getString("userNo", ""));
        leave.setStartTime(tvLeaveStartTime.getText().toString());
        leave.setEndTime(tvLeaveEndTime.getText().toString());
        leave.setSubmitter(sp.getString("userName", ""));
        leave.setSubmitterNo(sp.getString("userNo", ""));
        leave.setState(0);

        RetrofitUtil.getLeaveAPI()
                .submitLeave(leave)
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(response -> response.getCode())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                    if(code == 200){
                        Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_LONG);
                    }
                    finishTask();
                }, throwable -> {});

    }

    @Override
    public void finishTask() {
        super.finishTask();
        onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_leave_start_time:
                selectStartTime(view);
                break;
            case R.id.tv_leave_end_time:
                selectEndTime(view);
                break;
            case R.id.btn_submit:
                submit();
                break;
            case R.id.tv_cancel_leave:
                cancelLeave();
                break;
        }
    }

    private void selectStartTime(View view) {
        pvStartTime.show(view);
    }

    private void selectEndTime(View view) {
        pvEndTime.show(view);
    }

}