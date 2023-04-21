package com.hfut.lianya.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.QuickViewHolder;
import com.hfut.lianya.R;
import com.hfut.lianya.bean.Fkpb;

public class ToBeDoneTaskAdapter extends BaseQuickAdapter<Fkpb, QuickViewHolder> {
    @Override
    protected void onBindViewHolder(@NonNull QuickViewHolder quickViewHolder, int i, @Nullable Fkpb fkpb) {
        quickViewHolder.setText(R.id.tv_segment_id, fkpb.getSegment()+"");
        quickViewHolder.setText(R.id.tv_line, fkpb.getLine());
        quickViewHolder.setText(R.id.tv_package_id, fkpb.getPackageId());
        quickViewHolder.setText(R.id.tv_area, fkpb.getRegion());
        quickViewHolder.setText(R.id.tv_num, String.valueOf(fkpb.getEnd() - fkpb.getStart() + 1)+"件");
    }

    @NonNull
    @Override
    protected QuickViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return new QuickViewHolder(R.layout.item_worker_to_be_done_task, viewGroup);
    }

}
