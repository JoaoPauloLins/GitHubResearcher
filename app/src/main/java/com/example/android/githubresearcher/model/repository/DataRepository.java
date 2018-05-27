package com.example.android.githubresearcher.model.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.example.android.githubresearcher.model.repository.database.AppDatabase;
import com.example.android.githubresearcher.model.repository.entities.UserEntity;

public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<UserEntity> mObservableUser;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableUser = new MediatorLiveData<>();
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<UserEntity> getProducts() {
        return mObservableUser;
    }

    public LiveData<UserEntity> loadUser(final int userId) {
        return mDatabase.userDao().selectUser(userId);
    }
}
