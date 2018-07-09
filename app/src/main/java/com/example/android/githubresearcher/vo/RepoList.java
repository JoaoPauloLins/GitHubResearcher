package com.example.android.githubresearcher.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"listId", "repoId"},
        foreignKeys = {
                @ForeignKey(
                        entity = UserList.class,
                        parentColumns = "id",
                        childColumns = "listId"
                ),
                @ForeignKey(
                        entity = Repo.class,
                        parentColumns = "id",
                        childColumns = "repoId"
                )})
public class RepoList {

    public final int listId;
    public final int repoId;

    public RepoList(int listId, int repoId) {
        this.listId = listId;
        this.repoId = repoId;
    }
}
