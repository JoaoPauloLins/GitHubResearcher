package com.example.android.githubresearcher.vo;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(primaryKeys = "id")
public class User implements Serializable {

    @SerializedName("id")
    public final int id;
    @SerializedName("login")
    public final String login;
    @SerializedName("name")
    public final String name;
    @SerializedName("avatar")
    public final String avatar;
    @SerializedName("bio")
    public final String bio;

    public User(int id, String login, String name, String avatar, String bio) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.avatar = avatar;
        this.bio = bio;
    }
}
