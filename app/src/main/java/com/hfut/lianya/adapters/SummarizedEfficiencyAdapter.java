package com.hfut.lianya.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.QuickViewHolder;
import com.hfut.lianya.R;
import com.hfut.lianya.bean.SummarizedEfficiency;

public class SummarizedEfficiencyAdapter extends BaseQuickAdapter<SummarizedEfficiency, QuickViewHolder> {
    @Override
    protected void onBindViewHolder(@NonNull QuickViewHolder quickViewHolder, int i, @Nullable SummarizedEfficiency efficiency) {;
        quickViewHolder.setText(R.id.tv_user_no, efficiency.getUserNo());
        quickViewHolder.setText(R.id.tv_user_name, efficiency.getUserName());
        quickViewHolder.setText(R.id.tv_today_efficiency, efficiency.getTodayEfficiency() == null ? "":efficiency.getTodayEfficiency().toString());
        quickViewHolder.setText(R.id.tv_three_day_efficiency, efficiency.getThreeDayEfficiency() == null ? "":efficiency.getThreeDayEfficiency().toString());
    }

    @NonNull
    @Override
    protected QuickViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return new QuickViewHolder(R.layout.item_summarized_efficiency, viewGroup);
    }
}
