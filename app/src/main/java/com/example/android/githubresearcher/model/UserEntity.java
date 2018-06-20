package com.example.android.githubresearcher.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "user")
public class UserEntity implements Serializable {

    @SerializedName("id")
    @Expose
    @NonNull
    @PrimaryKey
    final private String id;

    @SerializedName("name")
    @Expose
    final private String name;

    @SerializedName("login")
    @Expose
    final private String login;

    @SerializedName("bio")
    @Expose
    final private String bio;

    @SerializedName("avatar")
    @Expose
    final private String avatar;

    public UserEntity(@NonNull String id,
                      String name,
                      String login,
                      String bio,
                      String avatar){
        this.id = id;
        this.name = name;
        this.login = login;
        this.bio = bio;
        this.avatar = avatar;
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

    public String getBio() {
        return bio;
    }

    public String getAvatar() {
        return avatar;
    }
}
