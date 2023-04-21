package com.hfut.lianya.abnormalitymanager.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.R;
import com.hfut.lianya.adapters.AbnormalityAdapter;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.bean.Abnormality;
import com.hfut.lianya.databinding.FragmentAbnormalityManagerDashboardBinding;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AbnormalityManagerDashboardFragment extends RxLazyFragment implements View.OnClickListener {


    private FragmentAbnormalityManagerDashboardBinding binding;
    private List<Abnormality>list = new ArrayList<>();
    private GlobalApplication application = GlobalApplication.getInstance();
    SharedPreferences sp = application.getSharedPreferences("user", Context.MODE_PRIVATE);
    RecyclerView rvDoingTask;
    SwipeRefreshLayout swipeRefreshLayout;
    AbnormalityAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_abnormality_manager_dashboard;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAbnormalityManagerDashboardBinding.inflate(inflater, container, false);
        initView();
//        loadData();
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
        RetrofitUtil.getAbnormalityAPI()
                .getDealingAbnormalityByDealer(sp.getString("userNo", ""))
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
        adapter = new AbnormalityAdapter();
        adapter.submitList(list);
        adapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            Intent intent = new Intent(getApplicationContext(), DealAbnormalityActivity.class);
            intent.putExtra("sender", list.get(i).getSender());
            intent.putExtra("senderNo", list.get(i).getSenderNo());
            intent.putExtra("workstation", list.get(i).getWorkstation());
            intent.putExtra("packageId", list.get(i).getPackageId());
            intent.putExtra("desc", list.get(i).getAbnormalityDesc());
            intent.putExtra("time", list.get(i).getSendTime());
            intent.putExtra("type", list.get(i).getAbnormalityType());
            intent.putExtra("id", list.get(i).getId());
            intent.putExtra("state", list.get(i).getState());
            startActivity(intent);
        });
        rvDoingTask.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rvDoingTask.setAdapter(adapter);
    }


    public void initView() {
        swipeRefreshLayout = binding.srl;
        swipeRefreshLayout.setOnRefreshListener(() -> swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        }, 3000));

    }

    @Override
    public void onClick(View view) {
    }

}