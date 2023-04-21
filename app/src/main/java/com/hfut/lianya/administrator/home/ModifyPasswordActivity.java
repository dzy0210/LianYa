package com.hfut.lianya.administrator.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.R;
import com.hfut.lianya.base.RxBaseActivity;
import com.hfut.lianya.net.RetrofitUtil;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ModifyPasswordActivity extends RxBaseActivity {
    TextInputLayout tilOldPassword;
    TextInputLayout tilNewPassword;
    TextInputLayout tilConfirmNewPassword;
    TextInputEditText etOldPassword;
    TextInputEditText etNewPassword;
    TextInputEditText etConfirmNewPassword;
    Button btnConfirmModify;
    SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences("user", MODE_PRIVATE);

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews(savedInstanceState);
        initToolBar();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_password;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        toolbar = findViewById(R.id.toolbar);
        tilOldPassword = findViewById(R.id.til_old_password);
        tilNewPassword = findViewById(R.id.til_new_password);
        tilConfirmNewPassword = findViewById(R.id.til_confirm_new_password);
        etOldPassword = findViewById(R.id.et_old_password);
        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmNewPassword = findViewById(R.id.et_confirm_new_password);
        btnConfirmModify = findViewById(R.id.btn_confirm_modify);
        btnConfirmModify.setOnClickListener(this::modifyPassword);


        tilNewPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(tilNewPassword.getEditText().getText().toString().length() < 6) {
                    tilNewPassword.setError("密码不能少于6位！");
                } else {
                    tilNewPassword.setError(null);
                }
            }
        });
        tilConfirmNewPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!tilConfirmNewPassword.getEditText().getText().toString().equals(tilNewPassword.getEditText().getText().toString())) {
                    tilConfirmNewPassword.setError("两次输入不一致");
                } else {
                    tilConfirmNewPassword.setError(null);
                }
            }
        });

    }

    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("修改密码");
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

    public void modifyPassword(View view) {
        String oldPassword = tilOldPassword.getEditText().getText().toString();
        String newPassword = tilNewPassword.getEditText().getText().toString();
        String confirmNewPassword = tilConfirmNewPassword.getEditText().getText().toString();
        String userNo = sp.getString("userNo", "");

        if(!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(this, "两次输入不一致！", Toast.LENGTH_SHORT).show();
        } else {
            RetrofitUtil.getUserAPI()
                    .modifyPassword(userNo, oldPassword, newPassword)
                    .compose(bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(respond -> {
                        if(respond.getCode() == 200) {
                            Log.d("modify", respond.getMsg());
                            Log.d("modify", "00000000000000");
                            if (respond.getMsg().equals("0")) {
                                Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            } else {
                                Toast.makeText(this, "密码错误！", Toast.LENGTH_SHORT).show();
                                tilOldPassword.getEditText().setText("");
                            }
                        }
                    }, throwable -> {
                    });
        }
    }
}