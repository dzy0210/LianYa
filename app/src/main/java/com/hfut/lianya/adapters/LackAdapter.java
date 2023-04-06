package com.hfut.lianya.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.QuickViewHolder;
import com.hfut.lianya.R;
import com.hfut.lianya.bean.Lack;
import com.hfut.lianya.bean.Leave;
import com.hfut.lianya.global.GlobalVariable;

public class LackAdapter extends BaseQuickAdapter<Lack, QuickViewHolder> {
    @Override
    protected void onBindViewHolder(@NonNull QuickViewHolder quickViewHolder, int i, @Nullable Lack lack) {

    }

    @NonNull
    @Override
    protected QuickViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return new QuickViewHolder(R.layout.item_leave_request, viewGroup);
    }
}
