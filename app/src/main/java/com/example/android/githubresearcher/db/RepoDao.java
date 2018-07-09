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

    @Query("SELECT * FROM repo WHERE userId = :userId")
    LiveData<List<Repo>> findReposByUserId(int userId);

    @Query("SELECT * FROM repo WHERE id = :id")
    Repo findRepoById(int id);
}
