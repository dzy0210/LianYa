//package com.hfut.lianya.adapters;
//
//import android.content.Context;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.viewholder.QuickViewHolder;
//import com.hfut.lianya.R;
//import com.hfut.lianya.bean.Finish;
//import com.hfut.lianya.bean.WorkerDoneTask;
//
//public class WorkerHistoryTaskAdapter extends BaseQuickAdapter<Finish, QuickViewHolder> {
//
//
//    @Override
//    protected void onBindViewHolder(@NonNull QuickViewHolder quickViewHolder, int i, @Nullable Finish finish) {
//        quickViewHolder.setText(R.id.tv_worker_no, finish.getWorkernumber());
//        quickViewHolder.setText(R.id.tv_worker_name, finish.getName());
//        quickViewHolder.setText(R.id.tv_package_num, finish.getNum());
//        quickViewHolder.setText(R.id.tv_piece_num, finish.getPieceNum());
//    }
//
//    @NonNull
//    @Override
//    protected QuickViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
//        return new QuickViewHolder(R.layout.item_worker_done_task_detail, viewGroup);
//    }
//
//}
