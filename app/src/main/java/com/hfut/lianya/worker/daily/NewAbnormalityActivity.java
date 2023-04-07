package com.hfut.lianya.worker.daily;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import com.hfut.lianya.bean.Abnormality;
import com.hfut.lianya.global.GlobalVariable;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;
import com.hfut.lianya.utils.DateUtil;


import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewAbnormalityActivity extends RxBaseActivity implements View.OnClickListener {
    Toolbar toolbar;
    NiceSpinner spAbnormalityType;
    TextView tvReceiver;
    TextView tvAbnormalityTime;
    Button btnSubmit;
    LinearLayout llPackageId;
    Abnormality abnormality = new Abnormality();
    TextView tvSender;
    TextView tvBlemishPackageId;
    EditText etAbnormalityDesc;
    TextView tvCancelAbnormality;
    TextView tvAbnormalityManage;
    Calendar now= Calendar.getInstance();
    String dateTime = DateUtil.dateToString(now.getTime(), DateUtil.FORMAT_TIMESTAMP);
    TimePickerView pvAbnormalityTime;
    int id = 0;
    SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences("user", MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        initViews(savedInstanceState);
        initTimePicker();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_worker_new_abnormality;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        tvSender = findViewById(R.id.tv_abnormality_sender);
        tvBlemishPackageId = findViewById(R.id.tv_blemish_package_id);
        spAbnormalityType = findViewById(R.id.sp_abnormality_type);
        tvAbnormalityTime = findViewById(R.id.tv_abnormality_time);
        tvReceiver = findViewById(R.id.tv_abnormality_receiver);
        tvAbnormalityManage = findViewById(R.id.tv_abnormality_manage);
        btnSubmit = findViewById(R.id.btn_submit);
        llPackageId = findViewById(R.id.ll_package_id);
        tvCancelAbnormality = findViewById(R.id.tv_cancel_abnormality);
        etAbnormalityDesc = findViewById(R.id.et_abnormality_desc);
        tvAbnormalityTime.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        tvCancelAbnormality.setOnClickListener(this);
        tvAbnormalityTime.setText(dateTime);
        tvSender.setText(GlobalVariable.USERNAME);
        tvBlemishPackageId.setText(GlobalVariable.PACKAGEID);

        initSpinner();
        Intent intent = getIntent();
        if(intent.getStringExtra("sendTime") != null) {
            id = intent.getIntExtra("id", 0);
            int abnormalityState = intent.getIntExtra("abnormalityState", 0);
            String receiverNo = intent.getStringExtra("receiverNo");
            String receiver = intent.getStringExtra("receiver");
            int type = intent.getIntExtra("abnormalityType", 0);
            String abnormalityDesc = intent.getStringExtra("abnormalityDesc");
            String abnormalityTime = intent.getStringExtra("sendTime");
            String solveTime = intent.getStringExtra("solveTime");

            spAbnormalityType.setSelectedIndex(type-'0');
            tvReceiver.setText(receiver);
            tvAbnormalityTime.setText(abnormalityTime);
            tvSender.setText(sp.getString("userName", ""));
            btnSubmit.setVisibility(View.GONE);
            etAbnormalityDesc.setText(abnormalityDesc);
            tvAbnormalityManage.setText("异常详情");

            spAbnormalityType.setEnabled(false);
            tvReceiver.setEnabled(false);
            tvAbnormalityTime.setEnabled(false);
            tvSender.setEnabled(false);
            etAbnormalityDesc.setEnabled(false);
            if(abnormalityState == '0') {
                tvCancelAbnormality.setVisibility(View.VISIBLE);
                spAbnormalityType.setEnabled(true);
                tvReceiver.setEnabled(true);
                tvAbnormalityTime.setEnabled(true);
                tvSender.setEnabled(true);
                etAbnormalityDesc.setEnabled(true);
                btnSubmit.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("新增异常");
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

    public void selectTime(View view) {
        pvAbnormalityTime.show(view);
    }

    private void submit() {
        abnormality.setState(0);
        abnormality.setAbnormalityType(spAbnormalityType.getSelectedIndex());
        abnormality.setAbnormalityDesc(etAbnormalityDesc.getText().toString());
        abnormality.setSendTime(tvAbnormalityTime.getText().toString());
        abnormality.setSender(sp.getString("userNo", ""));
        abnormality.setSenderNo(sp.getString("userName", ""));
        abnormality.setReceiver(tvReceiver.getText().toString());
        abnormality.setReceiverNo(GlobalVariable.ABNORMALITY_RESPONDER_No[spAbnormalityType.getSelectedIndex()]);

        RetrofitUtil.getAbnormalityAPI()
                .submit(abnormality)
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(response -> response.getCode())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                    if(code == 200){
                        Toast.makeText(this, "提交成功", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                    finishTask();
                }, throwable -> {});

    }
    private void initTimePicker() {
        pvAbnormalityTime = new TimePickerBuilder(this, (date, v) -> tvAbnormalityTime.setText(DateUtil.dateToString(date, DateUtil.FORMAT_TIMESTAMP)))
                .setType(new boolean[]{true, true, true, true, true, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setCancelText("取消")
                .setSubmitText("确认")
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();

        Dialog mDialog = pvAbnormalityTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvAbnormalityTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }
    private void initSpinner() {
        List<String> exceptionType = Arrays.asList(GlobalVariable.ABNORMALITY_TYPE);
        spAbnormalityType.attachDataSource(exceptionType);
        tvReceiver.setText(GlobalVariable.ABNORMALITY_RESPONDER[0]);
        spAbnormalityType.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                tvReceiver.setText(GlobalVariable.ABNORMALITY_RESPONDER[position]);
                if(position != 0) {
                    llPackageId.setVisibility(View.GONE);
                } else {
                    llPackageId.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_abnormality_time:
                selectTime(view);
                break;
            case R.id.btn_submit:
                submit();
                break;
            case R.id.tv_cancel_abnormality:
                cancelAbnormality();
                break;
        }
    }

    private void cancelAbnormality() {
        abnormality.setId(id);
        RetrofitUtil.getAbnormalityAPI()
                .cancelAbnormality(abnormality)
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
    public void finishTask() {
        super.finishTask();
        onBackPressed();
    }
}