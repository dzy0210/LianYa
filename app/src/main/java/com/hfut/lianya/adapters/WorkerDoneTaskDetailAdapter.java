package com.hfut.lianya.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.QuickViewHolder;
import com.hfut.lianya.R;
import com.hfut.lianya.bean.Finish;
import com.hfut.lianya.bean.Fkpb;

import java.util.List;

public class WorkerDoneTaskDetailAdapter extends BaseQuickAdapter<Finish, QuickViewHolder> {
    private List<Fkpb> list;
    private OnItemClickListener listener;

    @Override
    protected void onBindViewHolder(@NonNull QuickViewHolder quickViewHolder, int i, @Nullable Finish finish) {
        quickViewHolder.setText(R.id.tv_state, "已完成");
        quickViewHolder.setText(R.id.tv_task_no, finish.getFkid());
        quickViewHolder.setText(R.id.tv_task_type, finish.getLine());
        quickViewHolder.setText(R.id.tv_num, String.valueOf(finish.getNum()));
        quickViewHolder.setText(R.id.tv_segment_id, finish.getSegment()+"");
    }

    @NonNull
    @Override
    protected QuickViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return new QuickViewHolder(R.layout.item_worker_done_task_detail, viewGroup);
    }
}
