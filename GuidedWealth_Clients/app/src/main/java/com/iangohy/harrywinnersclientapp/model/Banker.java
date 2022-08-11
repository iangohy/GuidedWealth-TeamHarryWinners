package com.iangohy.harrywinnersclientapp.model;

public class Banker {
    private String name;
    private String rank;
    private String description;
    private String profilePictureURL;
    private int karma;

    public Banker() {}

    public Banker(String name, String rank, String description, String profilePictureURL, int karma) {
        this.name = name;
        this.rank = rank;
        this.description = description;
        this.profilePictureURL = profilePictureURL;
        this.karma = karma;
    }

    public String getName() {
        return name;
    }

    public String getRank() {
        return rank;
    }

    public String getDescription() {
        return description;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public int getKarma() {
        return karma;
    }

    @Override
    public String toString() {
        return "Banker{" +
                "name='" + name + '\'' +
                ", rank='" + rank + '\'' +
                ", description='" + description + '\'' +
                ", profilePictureURL='" + profilePictureURL + '\'' +
                ", karma=" + karma +
                '}';
    }
}
