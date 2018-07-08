package com.example.android.githubresearcher.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.android.githubresearcher.vo.Repo;
import com.example.android.githubresearcher.vo.User;
import com.example.android.githubresearcher.vo.UserList;

@Database(entities = {User.class, Repo.class, UserList.class}, version = 1)
public abstract class GithubDb extends RoomDatabase {

    abstract public UserDao userDao();
    abstract public RepoDao repoDao();
    abstract public UserListDao userListDao();
}
