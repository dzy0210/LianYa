package com.hfut.lianya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.loadState.LoadState;
import com.chad.library.adapter.base.loadState.leading.LeadingLoadStateAdapter;
import com.hfut.lianya.databinding.ViewLoadMoreBinding;

public class CustomLoadMoreAdapter extends LeadingLoadStateAdapter<CustomLoadMoreAdapter.CustomVH> {

    @Override
    public CustomVH onCreateViewHolder(ViewGroup parent, LoadState loadState) {
        // 创建你自己的 UI 布局
        ViewLoadMoreBinding viewBinding = ViewLoadMoreBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CustomVH(viewBinding);
    }

    @Override
    public void onBindViewHolder(CustomVH holder, LoadState loadState) {
        // 根据加载状态，来自定义你的 UI 界面
        if (loadState instanceof LoadState.Loading) {
            holder.viewBinding.loadingProgress.setVisibility(View.VISIBLE);
        } else {
            holder.viewBinding.loadingProgress.setVisibility(View.GONE);
        }
    }

    public static class CustomVH extends RecyclerView.ViewHolder {

        private ViewLoadMoreBinding viewBinding;

        public CustomVH(ViewLoadMoreBinding viewBinding) {
            super(viewBinding.getRoot());
            this.viewBinding = viewBinding;
        }

        public ViewLoadMoreBinding getViewBinding() {
            return viewBinding;
        }
    }
}
