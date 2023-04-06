package com.hfut.lianya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.R;
import com.hfut.lianya.bean.WorkerDoneTask;

import java.util.List;

public class WorkerDoneTaskAdapter extends RecyclerView.Adapter<WorkerDoneTaskAdapter.ViewHolder> {
    private List<WorkerDoneTask> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(WorkerDoneTask workerDoneTask);
    }

    public WorkerDoneTaskAdapter(List<WorkerDoneTask> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_worker_done_task, parent, false);
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvWorkerNo = itemView.findViewById(R.id.tv_worker_no);
            this.tvPackageNum = itemView.findViewById(R.id.tv_package_num);
            this.tvPieceNum = itemView.findViewById(R.id.tv_piece_num);
        }
        public void bind(WorkerDoneTask workerDoneTask, OnItemClickListener listener) {
            tvWorkerNo.setText(workerDoneTask.getWorkerNo()+'-'+workerDoneTask.getWorkerName());
            tvPackageNum.setText(String.valueOf(workerDoneTask.getPackageNum())+"包");
            tvPieceNum.setText(String.valueOf(workerDoneTask.getPieceNum())+"件");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(workerDoneTask);
                }
            });
        }
    }

}
