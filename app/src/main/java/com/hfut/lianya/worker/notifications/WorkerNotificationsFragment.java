package com.hfut.lianya.worker.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.R;
import com.hfut.lianya.adapters.NotificationAdapter;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.bean.Notification;
import com.hfut.lianya.databinding.FragmentWorkerNotificationsBinding;
import com.hfut.lianya.global.GlobalVariable;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WorkerNotificationsFragment extends RxLazyFragment {

    private FragmentWorkerNotificationsBinding binding;
    private List<Notification> list ;
    private RecyclerView rvNotification;
    private NotificationAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_worker_notifications;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWorkerNotificationsBinding.inflate(inflater, container, false);
        list = new ArrayList<>();
        loadData();
        return binding.getRoot();
    }

    @Override
    public void finishCreateView(Bundle state) {

    }


    @Override
    protected void loadData() {
        super.loadData();
        RetrofitUtil.getNotificationAPI()
                .getNotification(GlobalVariable.USERNO, '1', '0')
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(notificationList -> {
                    list.addAll(notificationList);
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
        Log.d("Notification", list.toString());
        rvNotification = binding.rvNotification;
        rvNotification.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new NotificationAdapter(list, new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Notification notification) {

            }

            @Override
            public void onConfirmClick(View v, int position, Notification notification) {
                confirmNotification(v, position, notification.getId());
            }
        });
        rvNotification.setAdapter(adapter);
    }

    void confirmNotification(View v, int position, int id) {
        RetrofitUtil.getNotificationAPI().readNotification(id)
                .compose(bindToLifecycle()).subscribeOn(Schedulers.io())
                .map(HttpRespondBody::getCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                    if(code == 200) {
                        Button button = v.findViewById(R.id.btn_read);
                        button.setClickable(false);
                        button.setText("已确定");
                    }
                }, throwable -> {});
    }
}