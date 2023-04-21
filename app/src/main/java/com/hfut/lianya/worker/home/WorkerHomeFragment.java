package com.hfut.lianya.worker.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.hfut.lianya.R;
import com.hfut.lianya.base.RxLazyFragment;
import com.hfut.lianya.databinding.FragmentWorkerHomeBinding;


public class WorkerHomeFragment extends RxLazyFragment implements View.OnClickListener {

    private FragmentWorkerHomeBinding binding;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_worker_home;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWorkerHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button btnHistoryTask = binding.btnHistoryTask;
        Button btnTest = binding.btnTest;
        btnTest.setOnClickListener(this);
//        btnHistoryTask.setOnClickListener(this);

        return root;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
//        Intent intent = new Intent(this.getActivity(), CheckDemoActivity.class);
//        startActivity(intent);
    }
}