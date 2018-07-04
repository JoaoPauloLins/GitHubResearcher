package com.example.android.githubresearcher.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
        primaryKeys = {"name", "userLogin"},
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "login",
                childColumns = "userLogin",
                onDelete = CASCADE))
public class Repo {

    @SerializedName("name")
    @NonNull
    public final String name;
    @SerializedName("userLogin")
    @NonNull
    public final String userLogin;

    public Repo(@NonNull String name, @NonNull String userLogin) {
        this.name = name;
        this.userLogin = userLogin;
    }
}
