package com.hfut.lianya.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.QuickViewHolder;
import com.hfut.lianya.R;
import com.hfut.lianya.bean.Deliveries;
import com.hfut.lianya.bean.Fkpb;

public class ReadyAdapter extends BaseQuickAdapter<Deliveries, QuickViewHolder> {

    @Override
    protected void onBindViewHolder(@NonNull QuickViewHolder quickViewHolder, int i, @Nullable Deliveries deliveries) {
        quickViewHolder.setText(R.id.tv_serial_no, deliveries.getSerialNo());
        quickViewHolder.setText(R.id.tv_current_workstation, deliveries.getCurrentPosition());
        quickViewHolder.setText(R.id.tv_next_workstation, deliveries.getNextPosition());
        quickViewHolder.setText(R.id.tv_line, deliveries.getLine());
    }

    @NonNull
    @Override
    protected QuickViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return new QuickViewHolder(R.layout.item_ready, viewGroup);
    }
}
