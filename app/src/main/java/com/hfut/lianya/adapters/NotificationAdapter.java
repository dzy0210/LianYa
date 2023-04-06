package com.hfut.lianya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfut.lianya.R;
import com.hfut.lianya.bean.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<Notification> notificationList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Notification notification);
        void onConfirmClick(View v, int position, Notification notification);
    }

    public NotificationAdapter(List<Notification> notificationList, OnItemClickListener listener) {
        this.notificationList = notificationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(notificationList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNotificationType;
        TextView tvSender;
        TextView tvSendTime;
        Button btnRead;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvNotificationType = itemView.findViewById(R.id.tv_notification_type);
            this.tvSender = itemView.findViewById(R.id.tv_sender);
            this.tvSendTime = itemView.findViewById(R.id.tv_send_time);
            this.btnRead = itemView.findViewById(R.id.btn_read);
        }
        public void bind(Notification notification, OnItemClickListener listener) {
            tvNotificationType.setText(notification.getNotificationType()=='0' ? "请假" : "异常");
            tvSender.setText(notification.getSender());
            tvSendTime.setText(notification.getSendTime());
            btnRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onConfirmClick(v, getBindingAdapterPosition(), notification);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(notification);
                }
            });
        }
    }
}
