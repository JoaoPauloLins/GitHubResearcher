package com.example.android.githubresearcher.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.android.githubresearcher.vo.Repo;
import com.example.android.githubresearcher.vo.User;

@Database(entities = {User.class, Repo.class}, version = 1)
public abstract class GithubDb extends RoomDatabase {

    abstract public UserDao userDao();
    abstract public RepoDao repoDao();
}
