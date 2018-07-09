package com.example.android.githubresearcher.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = CASCADE))
public class UserList {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public final int userId;
    public final String name;

    public UserList(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
