package com.hfut.lianya.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.QuickViewHolder;
import com.hfut.lianya.R;
import com.hfut.lianya.bean.SingleEfficiency;

public class SingleEfficiencyAdapter extends BaseQuickAdapter<SingleEfficiency, QuickViewHolder> {
    @Override
    protected void onBindViewHolder(@NonNull QuickViewHolder quickViewHolder, int i, @Nullable SingleEfficiency singleEfficiency) {;
        quickViewHolder.setText(R.id.tv_task_area, singleEfficiency.getPackageid());
        quickViewHolder.setText(R.id.tv_worker_no, singleEfficiency.getNameid());
        quickViewHolder.setText(R.id.tv_task_pipeline, singleEfficiency.getName());
        quickViewHolder.setText(R.id.tv_task_efficiency, singleEfficiency.getEfficieny().toString());
    }

    @NonNull
    @Override
    protected QuickViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return new QuickViewHolder(R.layout.item_single_efficiency, viewGroup);
    }
}
