package com.hfut.lianya.courier.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.R;
import com.hfut.lianya.adapters.ReadyAdapter;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.bean.Deliveries;
import com.hfut.lianya.databinding.FragmentCourierReadyBinding;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CourierReadyFragment extends RxLazyFragment {
    List<Deliveries> list = new ArrayList<>();
    FragmentCourierReadyBinding binding;
    private RecyclerView rvReady;
    ReadyAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_courier_ready;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCourierReadyBinding.inflate(inflater, container, false);
        loadData();
        return binding.getRoot();
    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        loadData();
//    }

    @Override
    public void finishCreateView(Bundle state) {

    }

    public static CourierReadyFragment newInstance() {
        return new CourierReadyFragment();
    }

    @Override
    protected void loadData() {
    super.loadData();
        RetrofitUtil.getDashboardAPI()
            .getForDeliver()
            .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
            .map(HttpRespondBody::getData)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(delivering -> {
                list.addAll(delivering);
                finishTask();
            }, throwable -> {});
    }

    @Override
    protected void finishTask() {
        super.finishTask();
        initRecyclerView();
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        rvReady = binding.rvReady;
        rvReady.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new ReadyAdapter();
        adapter.submitList(list);
        rvReady.setAdapter(adapter);
    }
}

