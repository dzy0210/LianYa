package com.hfut.lianya.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.QuickViewHolder;
import com.hfut.lianya.R;
import com.hfut.lianya.global.GlobalVariable;
import com.hfut.lianya.bean.Leave;

public class LeaveRequestAdapter extends BaseQuickAdapter<Leave, QuickViewHolder> {
    @Override
    protected void onBindViewHolder(@NonNull QuickViewHolder quickViewHolder, int i, @Nullable Leave leave) {
        String state = "";
        int color = Color.BLACK;
        switch (leave.getState()) {
            case 0 : {
                state = "待处理";
                color = Color.GRAY;
                break;
            }
            case 1 : {
                state = "已通过";
                color = Color.GREEN;
                break;
            }
            case 2 : {
                state = "已拒绝";
                color = Color.RED;
                break;
            }
        }
        quickViewHolder.setText(R.id.tv_leave_state, state);
        quickViewHolder.setTextColor(R.id.tv_leave_state, color);
        quickViewHolder.setText(R.id.tv_leave_type, GlobalVariable.LEAVE_TYPE[leave.getLeaveType()]);
        quickViewHolder.setText(R.id.tv_leave_start_time, leave.getStartTime());
        quickViewHolder.setText(R.id.tv_leave_end_time, leave.getEndTime());
    }

    @NonNull
    @Override
    protected QuickViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return new QuickViewHolder(R.layout.item_leave_request, viewGroup);
    }
}
