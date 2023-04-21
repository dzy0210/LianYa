package com.hfut.lianya.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.QuickViewHolder;
import com.hfut.lianya.GlobalApplication;
import com.hfut.lianya.R;
import com.hfut.lianya.bean.Abnormality;

public class AbnormalityAdapter extends BaseQuickAdapter<Abnormality, QuickViewHolder> {

    GlobalApplication globalApplication = GlobalApplication.getInstance();
    @Override
    protected void onBindViewHolder(@NonNull QuickViewHolder quickViewHolder, int i, @Nullable Abnormality abnormality) {
//        quickViewHolder.setText(R.id.tv_abnormality_sender, abnormality.getSender());
//        quickViewHolder.setText(R.id.tv_abnormality_receiver, abnormality.getReceiver());
//        quickViewHolder.setText(R.id.tv_abnormality_desc, abnormality.getAbnormalityDesc());
        quickViewHolder.setText(R.id.tv_abnormality_send_time, abnormality.getSendTime());
        if(abnormality.getState() == '0' || abnormality.getState() == '1') {
            quickViewHolder.setGone(R.id.tv_abnormality_solve_time, true);
        } else {
            quickViewHolder.setText(R.id.tv_abnormality_solve_time, abnormality.getSolveTime());
        }
        String state = "待接收";
        int color = Color.BLACK;
        switch (abnormality.getState()) {
            case 0:{
                state = "待接收";
                color = Color.GRAY;
                break;
            }
            case 1:{
                state = "已接收";
                color = Color.BLUE;
                break;
            }
            case 2:{
                state = "已解决";
                color = Color.GREEN;
                break;
            }
            case 3:{
                state = "已退回";
                color = Color.RED;
                break;
            }
        }
        quickViewHolder.setText(R.id.tv_abnormality_state, state);
        quickViewHolder.setTextColor(R.id.tv_abnormality_state, color);
        quickViewHolder.setText(R.id.tv_abnormality_type, globalApplication.abnormalityType.get(abnormality.getAbnormalityType()));
}

    @NonNull
    @Override
    protected QuickViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return new QuickViewHolder(R.layout.item_abnormality, viewGroup);
    }

}
