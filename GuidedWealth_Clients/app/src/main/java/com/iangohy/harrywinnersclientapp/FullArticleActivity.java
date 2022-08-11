package com.iangohy.harrywinnersclientapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FullArticleActivity extends AppCompatActivity {
    private String TAG = "FullArticleActivity";
    private ImageButton backBtn;
    private Button confirmBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullarticle);

        backBtn = findViewById(R.id.articleFullBack);

        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(FullArticleActivity.this, MainActivity.class);
            intent.putExtra("targetPage", "learn");
            startActivity(intent);
        });


        Intent intent = getIntent();
        String articleTitle = intent.getStringExtra("articleTitle");
        int articleUpvotes = intent.getIntExtra("articleUpvotes", 0);
        int articleViews = intent.getIntExtra("articleViews", 0);
        String articleDate = intent.getStringExtra("articleDate");
        String articleContent = intent.getStringExtra("articleContent");
        String authorName = intent.getStringExtra("authorName");
        int articleShares = intent.getIntExtra("articleShares", 0);

        ((TextView) findViewById(R.id.fullArticleTitle)).setText(articleTitle);
        ((TextView) findViewById(R.id.fullArticleUpvotes)).setText(String.valueOf(articleUpvotes) + " upvotes");
        ((TextView) findViewById(R.id.fullArticleViews)).setText(String.valueOf(articleViews) + " views");
        ((TextView) findViewById(R.id.fullArticleShares)).setText(String.valueOf(articleShares) + " shares");
        ((TextView) findViewById(R.id.fullArticleDate)).setText(articleDate);
        ((TextView) findViewById(R.id.fullArticleContent)).setText(articleContent);
        ((TextView) findViewById(R.id.fullArticleName)).setText(authorName);

    }
}
