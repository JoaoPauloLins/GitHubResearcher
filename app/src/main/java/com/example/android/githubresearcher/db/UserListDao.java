package com.example.android.githubresearcher.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.githubresearcher.vo.UserList;

import java.util.List;

@Dao
public interface UserListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserList(UserList userList);

    @Query("SELECT * FROM userList WHERE userId = :userId")
    LiveData<List<UserList>> findUserListByUserId(int userId);

    @Query("SELECT * FROM userList WHERE userId = :userId AND name = :name")
    LiveData<List<UserList>> findUserListByName(int userId, String name);

    @Query("SELECT * FROM userList WHERE id = :id")
    UserList findUserListById(int id);

    @Query("SELECT u.id FROM userList u WHERE NOT EXISTS (SELECT * FROM repoList WHERE listId = u.id AND repoId = :repoId)")
    List<Integer> findUserListIdByRepoId(int repoId);
}
