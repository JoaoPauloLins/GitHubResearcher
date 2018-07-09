package com.example.android.githubresearcher.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
        primaryKeys = {"name", "userLogin"},
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "login",
                childColumns = "userLogin",
                onDelete = CASCADE))
public class Repo implements Serializable {

    @SerializedName("name")
    @NonNull
    public final String name;
    @SerializedName("userLogin")
    @NonNull
    public final String userLogin;
    @SerializedName("description")
    public final String description;
    @SerializedName("language")
    public final String language;
    @SerializedName("createdAt")
    public final String createdAt;

    public Repo(@NonNull String name,
                @NonNull String userLogin,
                String description,
                String language,
                String createdAt) {
        this.name = name;
        this.userLogin = userLogin;
        this.description = description;
        this.language = language;
        this.createdAt = createdAt;
    }
}
