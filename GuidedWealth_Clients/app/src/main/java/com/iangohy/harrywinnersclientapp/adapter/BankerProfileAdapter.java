package com.iangohy.harrywinnersclientapp.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.Query;
import com.iangohy.harrywinnersclientapp.ConfirmActivity;
import com.iangohy.harrywinnersclientapp.R;
import com.iangohy.harrywinnersclientapp.model.Banker;

import java.io.InputStream;

public class BankerProfileAdapter extends FirestoreAdapter<BankerProfileAdapter.ViewHolder> {
    
    private final String TAG = "BankerProfileAdapter";

    public BankerProfileAdapter(Query query) {
        super(query);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new BankerProfileAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.banker_profile_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position).toObject(Banker.class));
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView profilePhoto;
        private final TextView nameAndRank;
        private final TextView bankerDesc;
        private final TextView bankerKarma;

        public ViewHolder(View view) {
            super(view);
            profilePhoto = (ImageView) view.findViewById(R.id.bankerPhoto);
            nameAndRank = (TextView) view.findViewById(R.id.bankerNameRank);
            bankerDesc = (TextView) view.findViewById(R.id.bankerDesc);
            bankerKarma = (TextView) view.findViewById(R.id.bankerKarma);
        }

        public void bind(Banker banker) {
            bankerDesc.setText(banker.getDescription());
            bankerKarma.setText(banker.getKarma() + " Karma points");
            nameAndRank.setText(banker.getName() + "\n" + banker.getRank());
            new DownloadImageTask(profilePhoto).execute(banker.getProfilePictureURL());

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ConfirmActivity.class);
                intent.putExtra("banker", banker.getName());
                v.getContext().startActivity(intent);
            });
        }
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
