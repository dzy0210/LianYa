package com.hfut.lianya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.R;
import com.hfut.lianya.bean.Abnormality;

import java.util.List;

public class HistoryAbnormalityAdapter extends RecyclerView.Adapter<HistoryAbnormalityAdapter.ViewHolder> {
    List<Abnormality> abnormalityList;

    public HistoryAbnormalityAdapter(List<Abnormality> abnormalityList) {
        this.abnormalityList = abnormalityList;
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
    }

    @Override
    public int getItemCount() {
        return abnormalityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
