package com.iangohy.harrywinnersclientapp.ui.recommend;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.iangohy.harrywinnersclientapp.FirebaseUtil;
import com.iangohy.harrywinnersclientapp.model.Recommendation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecommendViewModel extends ViewModel {
    private final String TAG ="RecommendViewModel";
    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<Recommendation>> mRecommendations;
    private final MutableLiveData<String> mComments;
    private final MutableLiveData<Recommendation.RecommendationBankerContact> mBanker;

    public RecommendViewModel() {
        mText = new MutableLiveData<>();
        mRecommendations = new MutableLiveData<>();
        mComments = new MutableLiveData<>();
        mBanker = new MutableLiveData<>();
        mText.setValue("Your Recommended Changes");

        this.loadOverallAdviceAndBanker();
        this.loadHoldings();
        Log.d(TAG, String.valueOf(mRecommendations.getValue()));
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getComments() {
        return mComments;
    }
    public LiveData<List<Recommendation>> getRecommendations() {
        return mRecommendations;
    }
    public LiveData<Recommendation.RecommendationBankerContact> getBanker() {
        return mBanker;
    }

    private void loadHoldings() {
        CollectionReference holdingsRef = FirebaseUtil.getUserDocument().collection("holdings");
        EventListener<QuerySnapshot> handler = (value, error) -> {
            if (error != null) {
                Log.w(TAG, "holdings listen failed", error);
                return;
            }
            List<Recommendation> newRecommendations = new ArrayList<>();
            for (QueryDocumentSnapshot doc: value) {
                Map<String, Object> documentObj = doc.getData();
                String input = (String) doc.get("input");
                if (input == null || input.length() == 0) continue;
                Recommendation recommendation = Recommendation.convertDocumentToRecommendation(documentObj);
                newRecommendations.add(recommendation);
            }
            mRecommendations.setValue(newRecommendations);
        };
        holdingsRef.addSnapshotListener(handler);
    }

    private void loadOverallAdviceAndBanker() {
        DocumentReference userRef = FirebaseUtil.getUserDocument();
        EventListener<DocumentSnapshot> handler = (value, error) -> {
            if (error != null) {
                Log.w(TAG, "user/client listen failed", error);
                return;
            }
            if (value != null && value.exists()) {
                String overallAdvice = (String) value.get("summary");
                Map<String, Object> bankerObj = (Map<String, Object>) value.get("lastBankerContact");
                if (overallAdvice != null) {
                    mComments.setValue(overallAdvice);
                }
                if (bankerObj != null) {
                    Recommendation.RecommendationBankerContact banker = new Recommendation.RecommendationBankerContact(bankerObj);
                    mBanker.setValue(banker);
                }
            }
        };
        userRef.addSnapshotListener(handler);
    }
}