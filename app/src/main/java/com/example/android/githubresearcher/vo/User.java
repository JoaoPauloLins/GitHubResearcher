package com.example.android.githubresearcher.vo;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(primaryKeys = "login")
public class User {
    @SerializedName("login")
    @NonNull
    public final String login;
    @SerializedName("name")
    public final String name;
    @SerializedName("bio")
    public final String bio;
    @SerializedName("avatar_url")
    public final String avatarUrl;

    public User(@NonNull String login, String name, String bio, String avatarUrl) {
        this.login = login;
        this.name = name;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
    }
}
