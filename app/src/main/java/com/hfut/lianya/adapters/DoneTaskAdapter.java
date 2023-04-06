//package com.hfut.lianya.adapters;
//
//import android.graphics.Color;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.hfut.lianya.R;
//import com.hfut.lianya.bean.PackageCirculation;
//
//import java.util.List;
//
//public class DoneTaskAdapter extends RecyclerView.Adapter<DoneTaskAdapter.ViewHolder> {
//    private List<PackageCirculation> taskList;
//    private OnItemClickListener listener;
//    public interface OnItemClickListener {
//        void onItemClick(PackageCirculation task);
//    }
//
//    public DoneTaskAdapter(List<PackageCirculation> taskList, OnItemClickListener listener) {
//        this.taskList = taskList;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doing_task, parent, false);
//        ViewHolder viewHolder = new ViewHolder(itemView);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.bind(taskList.get(position), listener);
//    }
//
//    @Override
//    public int getItemCount() {
//        return taskList.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        TextView tvState;
//        TextView tvTaskNo;
//        TextView tvTaskType;
//        TextView tvSegmentId;
//        Button btnInstruction;
////        Button btnConfirmComplete;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            this.tvState = itemView.findViewById(R.id.tv_state);
//            this.tvTaskNo = itemView.findViewById(R.id.tv_task_no);
//            this.tvTaskType = itemView.findViewById(R.id.tv_task_type);
//            this.btnInstruction = itemView.findViewById(R.id.btn_instruction);
////            this.btnConfirmComplete = itemView.findViewById(R.id.btn_confirm_complete);
//        }
//        public void bind(PackageCirculation packageCirculation, OnItemClickListener listener) {
//            String state = "";
//            int color = Color.BLACK;
//            switch (packageCirculation.getState()) {
//                case '1': {
//                    state = "配送中";
//                    btnInstruction.setVisibility(View.GONE);
//                    break;
//                }
//                case '2':{
//                    state = "待加工";
//                    color = Color.RED;
//                    btnInstruction.setVisibility(View.GONE);
//                    break;
//                }
//                case '3': {
//                    state = "加工中";
//                    color = Color.GREEN;
//                    break;
//                }
//                case '4': {
//                    state = "已完成";
//                    color = Color.GRAY;
//                    btnInstruction.setVisibility(View.GONE);
////                    btnConfirmComplete.setVisibility(View.GONE);
//                    break;
//                }
//            }
//
//            tvState.setText(state);
//
//            tvSegmentId.setText(packageCirculation.getSegmentId());
//            tvState.setTextColor(color);
//            tvTaskNo.setText(packageCirculation.getPackageId());
//            tvTaskType.setText(packageCirculation.getLine());
//            btnInstruction.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onItemClick(packageCirculation);
//                }
//            });
//        }
//
//    }
//}
