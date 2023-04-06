package com.hfut.lianya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.R;
import com.hfut.lianya.bean.Leave;

import java.util.List;

public class HistoryLeaveAdapter extends RecyclerView.Adapter<HistoryLeaveAdapter.ViewHolder> {
    private List<Leave> leaveList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Leave leave);
    }

    public HistoryLeaveAdapter(List<Leave> leaveList, OnItemClickListener listener) {
        this.leaveList = leaveList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_abnormality, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(leaveList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return leaveList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public void bind(Leave leave, OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(leave);
                }
            });
        }
    }

}
