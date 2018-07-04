package com.example.android.githubresearcher.vo;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(primaryKeys = "login")
public class User implements Serializable {

    @SerializedName("login")
    @NonNull
    public final String login;
    @SerializedName("name")
    public final String name;
    @SerializedName("avatar")
    public final String avatar;
    @SerializedName("bio")
    public final String bio;

    public User(@NonNull String login, String name, String avatar, String bio) {
        this.login = login;
        this.name = name;
        this.avatar = avatar;
        this.bio = bio;
    }
}
