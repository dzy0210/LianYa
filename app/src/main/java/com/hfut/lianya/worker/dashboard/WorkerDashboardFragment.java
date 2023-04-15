package com.hfut.lianya.worker.dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.LoginActivity;
import com.hfut.lianya.R;
import com.hfut.lianya.adapters.DoingTaskAdapter;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.bean.Fkpb;
import com.hfut.lianya.databinding.FragmentWorkerDashboardBinding;
import com.hfut.lianya.global.GlobalVariable;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;
import com.hfut.lianya.utils.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WorkerDashboardFragment extends RxLazyFragment implements View.OnClickListener {


    private FragmentWorkerDashboardBinding binding;
    private TextView tvHourlyWage;
    private TextView tvPieceRateWage;
    private TextView tvPause;
    private ImageView ivPause;
    LinearLayout llStartScan;
    LinearLayout llLeave;
    LinearLayout llPause;
    private List<Fkpb>list = new ArrayList<>();
    private GlobalApplication application = GlobalApplication.getInstance();
    SharedPreferences sp = application.getSharedPreferences("user", Context.MODE_PRIVATE);
    double hourlyWage;
    double pieceRateWage;
    RecyclerView rvDoingTask;
    SwipeRefreshLayout swipeRefreshLayout;
    DoingTaskAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_worker_dashboard;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWorkerDashboardBinding.inflate(inflater, container, false);
        initView();
        getHourlyWage();
        getPieceRateWage();
        loadData();
        return binding.getRoot();
    }

    @Override
    public void finishCreateView(Bundle state) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void loadData() {
        super.loadData();
        list.clear();
        RetrofitUtil.getDashboardAPI()
                .getWorkerDoingTask(application.getSharedPreferences("user", Context.MODE_PRIVATE).getString("userNo", ""))
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskList -> {
                    list.addAll(taskList);
                    finishTask();
                }, throwable -> {});

    }

    @Override
    protected void finishTask() {
        super.finishTask();
        initRecyclerView();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void initRecyclerView() {
        super.initRecyclerView();
        rvDoingTask = binding.rvDoingTask;
        rvDoingTask.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new DoingTaskAdapter();
        adapter.submitList(list);
        adapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            Intent intent = new Intent(getApplicationContext(), InstructionsActivity.class);
            intent.putExtra("url", list.get(i).getUrl());
            startActivity(intent);
        });
        adapter.addOnItemChildClickListener(R.id.tv_complete, (baseQuickAdapter, view, i) -> {
            baseQuickAdapter.getItem(i);
            Toast.makeText(getApplicationContext(), "说明书", Toast.LENGTH_LONG).show();
        });
        rvDoingTask.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rvDoingTask.setAdapter(adapter);
    }

    public void getPieceRateWage() {
        RetrofitUtil.getDashboardAPI()
                .getPieceRateWage(GlobalVariable.USERNO)
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(wage -> wage.getData())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wage -> {
                    this.pieceRateWage = wage;
                    loadPieceRateWage();
                }, throwable -> {});
    }
    public void getHourlyWage() {
        RetrofitUtil.getDashboardAPI()
                .getHourlyWage(GlobalVariable.USERNO)
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(wage -> wage.getData())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wage -> {
                    this.hourlyWage = wage;
                    loadHourlyWage();
                }, throwable -> {});
    }

    public void initView() {
        llLeave = binding.llLeave;
        llStartScan = binding.llStartScan;
        tvHourlyWage = binding.tvHourlyWage;
        tvPieceRateWage = binding.tvPieceRateWage;
        llPause = binding.llPause;
        tvPause = binding.tvPause;
        ivPause = binding.ivPause;
        llPause.setOnClickListener(this);
        llStartScan.setOnClickListener(this);
        llLeave.setOnClickListener(this);
        swipeRefreshLayout = binding.srl;
        swipeRefreshLayout.setOnRefreshListener(() -> swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        }, 3000));
        if (GlobalApplication.getInstance().isPause) {
            tvPause.setText("开始");
            ivPause.setImageResource(R.drawable.start);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.ll_start_scan:
                scanContinuous();//连续扫码功能
                break;
            case R.id.ll_leave:
                leave();
                break;
            case R.id.ll_pause: {
                if (!GlobalApplication.getInstance().isPause) {
                    pauseWork();
                } else {
                    startWork();
                }
                break;
            }
        }
    }

    private void startWork() {
        tvPause.setText("暂停");
        ivPause.setImageResource(R.drawable.pause);
        GlobalApplication.getInstance().isPause = false;
        Calendar now= Calendar.getInstance();
        GlobalApplication.getInstance().pauseEndTime = now.getTime();
        GlobalApplication.getInstance().pauseTime = DateUtil.calLastedTime(GlobalApplication.getInstance().pauseStartTime, GlobalApplication.getInstance().pauseEndTime);
        GlobalApplication.getInstance().totalPauseTime += GlobalApplication.getInstance().pauseTime;
        new MaterialAlertDialogBuilder(getContext()).setMessage(String.valueOf(GlobalApplication.getInstance().totalPauseTime)+" " + GlobalApplication.getInstance().pauseStartTime + " " +GlobalApplication.getInstance().pauseEndTime).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    private void pauseWork() {
        tvPause.setText("开始");
        ivPause.setImageResource(R.drawable.start);
        GlobalApplication.getInstance().isPause = true;
        Calendar now= Calendar.getInstance();
        GlobalApplication.getInstance().pauseStartTime = now.getTime();
    }

    private void knockOff() {
        String time = DateUtil.getCurrentTime(DateUtil.FORMAT_TIME);
        RetrofitUtil.getUserAPI()
                .knockOff(sp.getString("userNo", ""), time)
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respond -> {
                    if(respond.getCode() == 200){
                        Toast.makeText(getApplicationContext(), respond.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    getActivity().finish();
                }, throwable -> {});
    }
    private void leave() {
        knockOff();
        SharedPreferences sharedPreferences = GlobalApplication.getInstance().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    private void scanContinuous() {
        Intent intent = new Intent(getContext(), WorkerContinuousCaptureActivity.class);
        startActivity(intent);
    }

    private void loadPieceRateWage() {
        tvPieceRateWage.setText(String.valueOf(pieceRateWage));
    }
    private void loadHourlyWage() {
        tvHourlyWage.setText(String.valueOf(hourlyWage));
    }
}