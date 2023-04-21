package com.hfut.lianya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hfut.lianya.abnormalitymanager.AbnormalityManagerMainActivity;
import com.hfut.lianya.administrator.AdminMainActivity;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.bean.User;
import com.hfut.lianya.courier.CourierMainActivity;
import com.hfut.lianya.net.RetrofitUtil;
import com.hfut.lianya.worker.WorkerMainActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends RxBaseActivity {
    private Button mLogin;
    SharedPreferences sharedPreferences = GlobalApplication.getInstance().getSharedPreferences("user", MODE_PRIVATE);
    TextInputLayout tilUserNo;
    TextInputLayout tilPassword;
    TextInputEditText etUserNo;
    TextInputEditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        tilUserNo = findViewById(R.id.til_user_no);
        tilPassword = findViewById(R.id.til_password);
        etUserNo = findViewById(R.id.et_user_no);
        etPassword = findViewById(R.id.et_password);
        tilPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(tilPassword.getEditText().getText().toString().length() < 6) {
                    tilPassword.setError("密码不能少于6位！");
                } else {
                    tilPassword.setError(null);
                }
            }
        });
        mLogin = findViewById(R.id.login);
        mLogin.setOnClickListener(v -> login());
    }

    @Override
    public void initToolBar() {

    }

    private void login() {
        User user = new User();
        user.setUserNo(tilUserNo.getEditText().getText().toString());
        user.setPassword(tilPassword.getEditText().getText().toString());
        RetrofitUtil.getUserAPI()
                .login(user)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respond -> {
                    if(respond.getCode() == 200) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("userType", respond.getData().getUserType());
                        editor.putString("token", respond.getData().getToken());
                        editor.putBoolean("isLogged", true);
                        editor.putString("userNo", respond.getData().getUserNo());
                        editor.putString("userName", respond.getData().getUserName());
                        editor.putString("workstation", respond.getData().getWorkstation());
                        editor.apply();
                        redirect(respond.getData().getUserType());
                    } else {
                        Toast.makeText(this, respond.getMsg(), Toast.LENGTH_LONG).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, "通信失败！", Toast.LENGTH_LONG).show();
                });
    }

    private void redirect(int userType) {
        switch (userType){
            case 0:{
                startWorker();
                finish();
                break;
            }
            case 1: {
                startCourier();
                finish();
                break;
            }
            case 2: {
                startAdministrator();
                finish();
                break;
            }
            case 3: {
                startAbnormalityManager();
                finish();
                break;
            }
            default:
                break;
        }
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
}