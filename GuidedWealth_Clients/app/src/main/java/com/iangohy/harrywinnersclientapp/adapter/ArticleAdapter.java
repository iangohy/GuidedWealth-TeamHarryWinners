package com.iangohy.harrywinnersclientapp.adapter;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.Query;
import com.iangohy.harrywinnersclientapp.ConfirmActivity;
import com.iangohy.harrywinnersclientapp.FullArticleActivity;
import com.iangohy.harrywinnersclientapp.R;
import com.iangohy.harrywinnersclientapp.model.Article;
import com.iangohy.harrywinnersclientapp.model.Banker;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ArticleAdapter extends FirestoreAdapter<ArticleAdapter.ViewHolder> {

    private final String TAG = "ArticleAdapter";
    private static Resources res;

    // Pass data to constructor
    public ArticleAdapter(Query query) {
        super(query);
    }

    // Inflate row layout from xml
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ArticleAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.article_row, viewGroup, false));
        // Create a new view, which defines the UI of the list item
//        View view = LayoutInflater.from(viewGroup.getContext())
//                .inflate(R.layout.article_row, viewGroup, false);
//        ViewHolder holder =  new ViewHolder(view);
//        view.setOnClickListener(v -> {
//            int position = holder.getAdapterPosition();
//            Intent intent = new Intent(v.getContext(), FullArticleActivity.class);
//            intent.putExtra("articleId", mData.get(position).getId());
//            v.getContext().startActivity(intent);
//        });
//
//        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.i(TAG, "bindviewholder : " + position);
        viewHolder.bind(getSnapshot(position).toObject(Article.class));

//        Article article = mData.get(position);
//        Banker banker = article.getAuthor();
//
//        new DownloadImageTask((ImageView) viewHolder.getProfilePhoto()).execute(banker.getProfilePictureURL());
//
//        viewHolder.getArticleTitle().setText(article.getTitle());
//        viewHolder.getArticleViews().setText(article.getViews() + " views");
//
//        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
//        viewHolder.getArticleDate().setText(dateFormat.format(article.getDate()));
//
//        viewHolder.getBankerKarma().setText(banker.getKarma() + " Karma points");
//        viewHolder.getNameAndRank().setText(banker.getName() + "\n" + banker.getRank());

    }


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView articleTitle;
        private final TextView articleViews;
        private final TextView articleDate;
        private final ImageView profilePhoto;
        private final TextView nameAndRank;
        private final TextView bankerKarma;
        private final TextView articleUpvotes;

        public ViewHolder(View view) {
            super(view);

            articleTitle = (TextView) view.findViewById(R.id.articleTitle);
            articleViews = (TextView) view.findViewById(R.id.articleViews);
            articleDate = (TextView) view.findViewById(R.id.articleDate);
            articleUpvotes = (TextView) view.findViewById(R.id.articleUpvotes);
            profilePhoto = (ImageView) view.findViewById(R.id.articlePhoto);
            nameAndRank = (TextView) view.findViewById(R.id.articleNameRank);
            bankerKarma = (TextView) view.findViewById(R.id.articleKarma);
        }

        public void bind(Article article) {
            articleTitle.setText(article.getTitle());
            articleViews.setText(String.valueOf(article.getViews()) + " views");
            articleUpvotes.setText(String.valueOf(article.getUpvotes()) + " upvotes");
            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
            articleDate.setText(dateFormat.format(article.getDate()));

            Banker banker = article.getAuthor();
            new DownloadImageTask(profilePhoto).execute(banker.getProfilePictureURL());
            nameAndRank.setText(banker.getName() + "\n" + banker.getRank());
            bankerKarma.setText(banker.getKarma() + " Karma points");

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), FullArticleActivity.class);
                intent.putExtra("articleTitle", article.getTitle());
                intent.putExtra("articleUpvotes", article.getUpvotes());
                intent.putExtra("articleViews", article.getViews());
                intent.putExtra("articleDate", dateFormat.format(article.getDate()));
                intent.putExtra("articleContent", article.getContent());
                intent.putExtra("authorName", article.getAuthor().getName());
                intent.putExtra("articleShares", article.getShares());
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
