package com.hfut.lianya.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.QuickAdapterHelper;
import com.chad.library.adapter.base.viewholder.QuickViewHolder;
import com.hfut.lianya.R;
import com.hfut.lianya.bean.WorkerDoneTask;

import java.util.List;

public class WorkerDoneTaskAdapter extends BaseQuickAdapter<WorkerDoneTask, QuickViewHolder> {


    @Override
    protected void onBindViewHolder(@NonNull QuickViewHolder quickViewHolder, int i, @Nullable WorkerDoneTask workerDoneTask) {
        quickViewHolder.setText(R.id.tv_worker_no, workerDoneTask.getWorkerNo());
        quickViewHolder.setText(R.id.tv_worker_name, workerDoneTask.getWorkerName());
        quickViewHolder.setText(R.id.tv_package_num, String.valueOf(workerDoneTask.getPackageNum()));
        quickViewHolder.setText(R.id.tv_piece_num, String.valueOf(workerDoneTask.getPieceNum()));
    }

    @NonNull
    @Override
    protected QuickViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return new QuickViewHolder(R.layout.item_worker_done_task, viewGroup);
    }

}
