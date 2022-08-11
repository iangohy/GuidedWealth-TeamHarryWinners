package com.iangohy.harrywinnersclientapp.model;

import java.util.Date;

public class Article {
    private Banker author;
    private String content;
    private Date date;
    private int shares;
    private String title;
    private int upvotes;
    private int views;

    public Article() {}

    public Article(Banker author, String content, Date date, int shares, String title, int upvotes, int views) {
        this.author = author;
        this.content = content;
        this.date = date;
        this.shares = shares;
        this.title = title;
        this.upvotes = upvotes;
        this.views = views;
    }

    public Banker getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public int getShares() {
        return shares;
    }

    public String getTitle() {
        return title;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getViews() {
        return views;
    }
}
