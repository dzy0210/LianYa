package com.hfut.lianya.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.R;
import com.hfut.lianya.bean.Fkpb;

import java.util.List;

public class WorkerDoneTaskDetailAdapter extends RecyclerView.Adapter<WorkerDoneTaskDetailAdapter.ViewHolder> {
    private List<Fkpb> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Fkpb fkpb);
    }

    public WorkerDoneTaskDetailAdapter(List<Fkpb> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_worker_done_task_detail, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvState;
        TextView tvTaskNo;
        TextView tvTaskType;
        TextView tvNum;
        TextView tvSegmentId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvState = itemView.findViewById(R.id.tv_state);
            this.tvTaskNo = itemView.findViewById(R.id.tv_task_no);
            this.tvTaskType = itemView.findViewById(R.id.tv_task_type);
            this.tvNum = itemView.findViewById(R.id.tv_num);
            this.tvSegmentId = itemView.findViewById(R.id.tv_segment_id);
        }
        public void bind(Fkpb fkpb, OnItemClickListener listener) {
            String state = "";
            int color = Color.BLACK;
            switch (fkpb.getStatus()) {
                case 1: {
                    state = "配送中";
                    break;
                }
                case 2:{
                    state = "待加工";
                    color = Color.RED;
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
                    break;
                }
            }

            tvState.setText(state);
            tvState.setTextColor(color);
            tvTaskNo.setText(fkpb.getPackageId());
            tvTaskType.setText(fkpb.getLine());
            tvNum.setText(String.valueOf(fkpb.getEnd()-fkpb.getStart() + 1));
            tvSegmentId.setText(fkpb.getSegment());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(fkpb);
                }
            });
        }
    }

}
