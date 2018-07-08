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

    @Query("SELECT * FROM userList WHERE userLogin = :userLogin")
    LiveData<List<UserList>> findUserListByLogin(String userLogin);
}
