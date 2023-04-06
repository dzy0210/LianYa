//package com.hfut.lianya.adapters;
//
//import android.util.SparseArray;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.hfut.lianya.R;
//import com.hfut.lianya.bean.PackageCirculation;
//import com.hfut.lianya.R;
//import com.hfut.lianya.bean.PackageCirculation;
//
//import java.util.List;
//
//public class CheckBoxAdapter extends RecyclerView.Adapter<CheckBoxAdapter.ViewHolder> {
//    private List<PackageCirculation> packageList;
//    private CheckItemListener listener;
//    SparseArray<Boolean> checkStates;
//
//    public interface CheckItemListener {
//
//        void itemChecked(PackageCirculation packageCirculation, boolean isChecked);
//    }
//
//    public CheckBoxAdapter(List<PackageCirculation> packageList, CheckItemListener listener, SparseArray<Boolean> checkStates) {
//        this.packageList = packageList;
//        this.listener = listener;
//        this.checkStates = checkStates;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_worker_task, parent, false);
//        ViewHolder viewHolder = new ViewHolder(itemView);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.bind(packageList.get(position), listener, position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return packageList.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        CheckBox cbSelect;
//        TextView tvPackageId;
//        TextView tvState;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            this.cbSelect = itemView.findViewById(R.id.cb_select);
//            this.tvPackageId = itemView.findViewById(R.id.tv_package_id);
//            this.tvState = itemView.findViewById(R.id.tv_state);
//        }
//        public void bind(PackageCirculation packageCirculation, CheckItemListener listener, int position) {
//            tvPackageId.setText(packageCirculation.getPackageId());
//            tvState.setText(packageCirculation.getState() == '0' ? "待加工" : "加工中");
////            cbSelect.setChecked(checkStates.get(position));
//            if (checkStates.get(position) != null) {
//                cbSelect.setChecked(checkStates.get(position));
//            }else {
//                cbSelect.setChecked(false);
//            }
//            cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    checkStates.put(position, isChecked);
//                }
//            });
//        }
//    }
//
//}
