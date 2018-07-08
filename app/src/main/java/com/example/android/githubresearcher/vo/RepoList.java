package com.example.android.githubresearcher.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

@Entity(
        primaryKeys = {"listName", "repoName", "userLogin"},
        foreignKeys = {
                @ForeignKey(
                        entity = UserList.class,
                        parentColumns = "name",
                        childColumns = "listName"
                ),
                @ForeignKey(
                        entity = Repo.class,
                        parentColumns = "name",
                        childColumns = "repoName"
                ),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "login",
                        childColumns = "userLogin")})
public class RepoList {

    @NonNull
    public final String listName;
    @NonNull
    public final String repoName;
    @NonNull
    public final String userLogin;

    public RepoList(@NonNull String listName, @NonNull String repoName, @NonNull String userLogin) {
        this.listName = listName;
        this.repoName = repoName;
        this.userLogin = userLogin;
    }
}
