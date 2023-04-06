package com.hfut.lianya.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.QuickViewHolder;
import com.hfut.lianya.R;
import com.hfut.lianya.bean.Fkpb;

public class DoingTaskAdapter extends BaseQuickAdapter<Fkpb, QuickViewHolder> {
    @Override
    protected void onBindViewHolder(@NonNull QuickViewHolder quickViewHolder, int i, @Nullable Fkpb fkpb) {
        quickViewHolder.setText(R.id.tv_task_type, fkpb.getLine());
        quickViewHolder.setText(R.id.tv_task_no, fkpb.getPackageId());
        String state = "";
        int color = Color.BLACK;
        switch (fkpb.getStatus()) {
            case 1: {
                state = "配送中";
                quickViewHolder.setGone(R.id.tv_complete, true);
                break;
            }
            case 2:{
                state = "待加工";
                color = Color.RED;
                quickViewHolder.setGone(R.id.tv_complete, true);
                break;
            }
            case 3: {
                state = "加工中";
                color = Color.GREEN;
                break;
            }
            case 4: {
                state = "已完成";
                color = Color.GRAY;
                quickViewHolder.setGone(R.id.tv_complete, true);
                break;
            }
        }
        quickViewHolder.setText(R.id.tv_state, state);
        quickViewHolder.setTextColor(R.id.tv_state, color);
    }

    @NonNull
    @Override
    protected QuickViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return new QuickViewHolder(R.layout.item_doing_task, viewGroup);
    }
}
