package com.example.android.githubresearcher.model.repository.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.githubresearcher.model.repository.entities.UserEntity;

@Dao
public interface UserDao {
    @Query("select * from user where id = :userId")
    LiveData<UserEntity> selectUser(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserEntity userEntity);
}
