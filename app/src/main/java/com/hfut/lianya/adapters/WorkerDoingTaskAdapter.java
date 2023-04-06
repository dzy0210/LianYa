package com.hfut.lianya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.R;
import com.hfut.lianya.bean.WorkerDoingTask;

import java.util.List;


public class WorkerDoingTaskAdapter extends RecyclerView.Adapter<WorkerDoingTaskAdapter.ViewHolder> {
    private List<WorkerDoingTask> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(WorkerDoingTask workerDoingTask);
    }

    public WorkerDoingTaskAdapter(List<WorkerDoingTask> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_worker_doing_task, parent, false);
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
        TextView tvWorkerNo;
        TextView tvWorkerName;
        TextView tvPackageNum;
        TextView tvPieceNum;
        TextView tvRemainingTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvWorkerNo = itemView.findViewById(R.id.tv_worker_no);
            this.tvPackageNum = itemView.findViewById(R.id.tv_package_num);
            this.tvPieceNum = itemView.findViewById(R.id.tv_piece_num);
            this.tvRemainingTime = itemView.findViewById(R.id.tv_remaining_time);
        }
        public void bind(WorkerDoingTask workerDoingTask, OnItemClickListener listener) {
            tvWorkerNo.setText(workerDoingTask.getWorkerNo()+'-'+workerDoingTask.getWorkerName());
            tvPackageNum.setText(String.valueOf(workerDoingTask.getPackageNum())+"包");
            tvPieceNum.setText(String.valueOf(workerDoingTask.getPieceNum())+"件");
            tvRemainingTime.setText(workerDoingTask.getRemainingTime()+"分钟");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(workerDoingTask);
                }
            });
        }
    }

}
