package com.hfut.lianya.courier.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.R;
import com.hfut.lianya.adapters.DeliveringAdapter;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.bean.Deliveries;
import com.hfut.lianya.databinding.FragmentCourierDeliveringBinding;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;
import com.hfut.lianya.bean.Fkpb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CourierDeliveringFragment extends RxLazyFragment {
    List<Deliveries> list = new ArrayList<>();
    List<Map<String, Object>> listItems=new ArrayList<Map<String,Object>>();
    RecyclerView rvDelivering;
    FragmentCourierDeliveringBinding binding;
    DeliveringAdapter adapter;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_courier_delivering;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCourierDeliveringBinding.inflate(inflater, container, false);
        loadData();
        return binding.getRoot();
    }

    @Override
    public void finishCreateView(Bundle state) {

    }

    public static CourierDeliveringFragment newInstance() {
        return new CourierDeliveringFragment();
    }

    @Override
    protected void loadData() {
        super.loadData();
        RetrofitUtil.getDashboardAPI()
                .getDelivering(GlobalApplication.getInstance().getSharedPreferences("user", Context.MODE_PRIVATE).getString("userNo", ""))
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(delivering -> {
                    list.addAll(delivering);
                    Log.d("list", list.toString());
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
        rvDelivering = binding.rvDelivering;
        rvDelivering.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new DeliveringAdapter();
        adapter.submitList(list);
        rvDelivering.setAdapter(adapter);
    }
}


