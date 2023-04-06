package com.hfut.lianya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.hfut.lianya.administrator.AdminMainActivity;
import com.hfut.lianya.courier.CourierMainActivity;
import com.hfut.lianya.worker.WorkerMainActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
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
        SharedPreferences sharedPreferences = GlobalApplication.getInstance().getSharedPreferences("user", MODE_PRIVATE);
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

    private void startLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}