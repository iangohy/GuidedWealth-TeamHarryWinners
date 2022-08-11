package com.iangohy.harrywinnersclientapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iangohy.harrywinnersclientapp.FirebaseUtil;

import com.iangohy.harrywinnersclientapp.ReviewActivity;
import com.iangohy.harrywinnersclientapp.adapter.GoalAdapter;
import com.iangohy.harrywinnersclientapp.adapter.HoldingAdapter;
import com.iangohy.harrywinnersclientapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private static String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    private GoalAdapter mGoalAdapter;
    private HoldingAdapter mHoldingAdapter;

    private final View.OnClickListener requestReviewListener = view -> {
        Intent intent = new Intent(getActivity(), ReviewActivity.class);
        startActivity(intent);
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Goals Button
        binding.addGoal.setOnClickListener(new AddGoalListener(getLayoutInflater(), getContext()));

        // Set username
        binding.welcomeName.setText(FirebaseUtil.getUname());

        // Request review button
        Intent intent = getActivity().getIntent();
        Boolean sent = intent.getBooleanExtra("sent", false);
        if (!sent) {
            binding.requestReview.setOnClickListener(requestReviewListener);
        } else {
            binding.requestReview.setText("Review pending");
        }

        // Portfolio Button
        binding.addPortfolio.setOnClickListener(new AddHoldingListener(getLayoutInflater(), getContext()));

        // Goals adapter
        RecyclerView recyclerView = binding.goalsRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mGoalAdapter = new GoalAdapter(FirebaseUtil.getGoalCollection().orderBy("deadline"));
        recyclerView.setAdapter(mGoalAdapter);

        // Portfolio adapter
        RecyclerView portfolioRecycler = binding.portfolioRecycler;
        portfolioRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHoldingAdapter = new HoldingAdapter(FirebaseUtil.getHoldingsCollection());
        portfolioRecycler.setAdapter(mHoldingAdapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoalAdapter != null) {
            mGoalAdapter.startListening();
        }
        if (mHoldingAdapter != null) {
            mHoldingAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoalAdapter != null) {
            mGoalAdapter.stopListening();
        }
        if (mHoldingAdapter != null) {
            mHoldingAdapter.stopListening();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}