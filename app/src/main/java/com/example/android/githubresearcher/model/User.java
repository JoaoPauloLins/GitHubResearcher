package com.example.android.githubresearcher.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user")
public class User {

    @NonNull
    @PrimaryKey
    final private String id;
    final private String name;
    final private String login;
    final private String description;
    final private String imageUrl;

    public User(@NonNull String id,
                String name,
                String login,
                String description,
                String imageUrl){
        this.id = id;
        this.name = name;
        this.login = login;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
