package com.example.android.githubresearcher.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface UserDao {

    @Query("select * from User where id = :userId")
    LiveData<User> selectUser(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);
}
