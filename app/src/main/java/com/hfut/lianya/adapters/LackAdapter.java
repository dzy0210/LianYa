package com.hfut.lianya.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.QuickViewHolder;
import com.hfut.lianya.R;
import com.hfut.lianya.bean.Lack;

public class LackAdapter extends BaseQuickAdapter<Lack, QuickViewHolder> {
    @Override
    protected void onBindViewHolder(@NonNull QuickViewHolder quickViewHolder, int i, @Nullable Lack lack) {
        quickViewHolder.setText(R.id.tv_line, lack.getLine());
        quickViewHolder.setText(R.id.tv_size, lack.getSize());
        quickViewHolder.setText(R.id.tv_serial_no, lack.getSerialNo());
        quickViewHolder.setText(R.id.tv_workstation, lack.getWorkbench());
    }

    @NonNull
    @Override
    protected QuickViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return new QuickViewHolder(R.layout.item_lack, viewGroup);
    }
}
