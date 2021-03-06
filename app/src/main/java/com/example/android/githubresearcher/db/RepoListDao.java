package com.example.android.githubresearcher.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.githubresearcher.vo.Repo;
import com.example.android.githubresearcher.vo.RepoList;

import java.util.List;

@Dao
public interface RepoListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepoList(RepoList... repoLists);

    @Query("SELECT r.repoId FROM repoList r WHERE listId = :userListId")
    List<Integer> findRepoIdsByUserListId(int userListId);

    @Query("SELECT * FROM repo WHERE id = :id")
    Repo findRepoById(int id);
}
