package com.example.android.githubresearcher.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
        primaryKeys = {"name","userLogin"},
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "login",
                childColumns = "userLogin",
                onDelete = CASCADE))
public class UserList {

    @NonNull
    public final String name;
    @NonNull
    public final String userLogin;

    public UserList(@NonNull String name, @NonNull String userLogin) {
        this.name = name;
        this.userLogin = userLogin;
    }
}
