package com.example.android.githubresearcher.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(primaryKeys = "id",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = CASCADE))
public class Repo implements Serializable {

    @SerializedName("id")
    public final int id;
    @SerializedName("name")
    public final String name;
    @SerializedName("userId")
    public final int userId;
    @SerializedName("description")
    public final String description;
    @SerializedName("language")
    public final String language;
    @SerializedName("createdAt")
    public final String createdAt;

    public Repo(int id,
                String name,
                int userId,
                String description,
                String language,
                String createdAt) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.description = description;
        this.language = language;
        this.createdAt = createdAt;
    }
}
