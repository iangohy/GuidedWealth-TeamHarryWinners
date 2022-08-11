package com.iangohy.harrywinnersclientapp.ui.recommend;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FieldValue;
import com.iangohy.harrywinnersclientapp.FirebaseUtil;
import com.iangohy.harrywinnersclientapp.R;
import com.iangohy.harrywinnersclientapp.adapter.RecommendationAdapter;
import com.iangohy.harrywinnersclientapp.databinding.FragmentRecommendBinding;
import com.iangohy.harrywinnersclientapp.model.Recommendation;

import java.io.InputStream;
import java.util.ArrayList;

public class RecommendFragment extends Fragment {
    private static final String TAG ="RecommendFragment";
    private RecommendViewModel recommendViewModel;
    private RecommendationAdapter recommendationAdapter;
    private RecyclerView recommendationRecyclerView;
    private FragmentRecommendBinding binding;
    private Button actionButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.recommendViewModel = new ViewModelProvider(this).get(RecommendViewModel.class);

        binding = FragmentRecommendBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        this.recommendViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        final TextView commentsTextView = binding.recommendComments;
        this.recommendViewModel.getComments().observe(getViewLifecycleOwner(), commentsTextView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.recommendationAdapter = new RecommendationAdapter(getContext(), new ArrayList<>());
        this.recommendViewModel.getRecommendations().observe(getViewLifecycleOwner(), this.recommendationAdapter::updateDataset);
        this.recommendationRecyclerView = view.findViewById(R.id.recommendations_recycler);
        this.recommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recommendationRecyclerView.setAdapter(this.recommendationAdapter);

        this.recommendViewModel.getBanker().observe(
                getViewLifecycleOwner(),
                recommendationBankerContact -> {
                    updateBankerContact(view, recommendationBankerContact);
                });



        this.actionButton = view.findViewById(R.id.acceptSuggestion);
        this.actionButton.setOnClickListener(v -> {
            // increase banker karma by 1
            FirebaseUtil.getBankerCollection().document("Jane Flyer").update("karma", FieldValue.increment(1));
        });
    }

    private void updateBankerContact(
            @NonNull View view,
            Recommendation.RecommendationBankerContact recommendationBankerContact) {
        LinearLayout bankerContactRoot = view.findViewById(R.id.recommend_banker_contact);
        View profileCard = getLayoutInflater().inflate(R.layout.banker_contact_card, null);
        bankerContactRoot.addView(profileCard);
        TextView bankerName = view.findViewById(R.id.banker_name);
        TextView bankerEmail = view.findViewById(R.id.banker_email);
        TextView bankerOffice = view.findViewById(R.id.banker_office);
        TextView bankerPhone = view.findViewById(R.id.banker_phone);
        TextView bankerTelegram = view.findViewById(R.id.banker_telegram);
        Resources res = getResources();

        String name = recommendationBankerContact.getName();
        String email = recommendationBankerContact.getEmail();
        String officeNo = recommendationBankerContact.getOfficeNumber();
        String phoneNo = recommendationBankerContact.getPhoneNumber();
        String tele = recommendationBankerContact.getTelegramHandle();
        bankerName.setText(String.format(res.getString(R.string.bankerName), name != "" ? name : "Hidden"));
        bankerEmail.setText(String.format(res.getString(R.string.bankerEmail), email != "" ? email : "Hidden"));
        bankerOffice.setText(String.format(res.getString(R.string.bankerOffice), officeNo != "" ? officeNo : "Hidden"));
        bankerPhone.setText(String.format(res.getString(R.string.bankerPhone), phoneNo != "" ? phoneNo : "Hidden"));
        bankerTelegram.setText(String.format(res.getString(R.string.bankerTelegram), tele != "" ? tele : "Hidden"));

        ImageView bankerPhoto = view.findViewById(R.id.banker_photo);
        new DownloadImageTask(bankerPhoto).execute("https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8cHJvZmlsZXxlbnwwfHwwfHw%3D&w=1000&q=80");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}