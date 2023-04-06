package com.hfut.lianya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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
    private EditText mAccountNumber;
    private EditText mPassword;
    SharedPreferences sharedPreferences;
    TextInputLayout tilTest;
    TextInputEditText etTest;

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
        tilTest = findViewById(R.id.til_test);
        etTest = findViewById(R.id.et_test);
        tilTest.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(tilTest.getEditText().getText().toString().length() < 6) {
                    tilTest.setError("密码不能少于6位！");
                } else {
                    tilTest.setError(null);
                }
            }
        });
        mLogin = findViewById(R.id.login);
        mAccountNumber = findViewById(R.id.account_number);
        mPassword = findViewById(R.id.password);
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    @Override
    public void initToolBar() {

    }

    private void login() {
        User user = new User();
        user.setUserNo(mAccountNumber.getText().toString());
        user.setPassword(mPassword.getText().toString());
        RetrofitUtil.getUserAPI()
                .login(user)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respond -> {
                    if(respond.getCode() == 200) {

                        Log.d("login", String.valueOf(respond.getCode()));
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("userType", respond.getData().getUserType());
                        editor.putString("token", respond.getData().getToken());
                        editor.putBoolean("isLogged", true);
                        editor.apply();
                        redirect(respond.getData().getUserType());
                    }
                    else {
                        Toast.makeText(this, respond.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, "通信失败！", Toast.LENGTH_LONG).show();
                });
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        if(mAccountNumber.getText().toString().equals("0")) {
//            editor.putBoolean("isLogged", true);
//            editor.putInt("userType", 0);
//            startWorker();
//        } else if(mAccountNumber.getText().toString().equals("1")) {
//            editor.putBoolean("isLogged", true);
//            editor.putInt("userType", 1);
//            startCourier();
//        } else if(mAccountNumber.getText().toString().equals("2")) {
//            editor.putBoolean("isLogged", true);
//            editor.putInt("userType", 2);
//            startAdministrator();
//        }
//        editor.apply();
//        finish();
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
}