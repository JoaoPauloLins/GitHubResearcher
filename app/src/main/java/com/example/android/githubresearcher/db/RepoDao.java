package com.example.android.githubresearcher.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.githubresearcher.vo.Repo;

import java.util.List;

@Dao
public interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepos(Repo... repos);

    @Query("SELECT * FROM repo WHERE name = :name")
    LiveData<Repo> findRepoById(String name);

    @Query("SELECT * FROM repo WHERE userLogin = :userLogin")
    LiveData<List<Repo>> findReposByUserLogin(String userLogin);
}
