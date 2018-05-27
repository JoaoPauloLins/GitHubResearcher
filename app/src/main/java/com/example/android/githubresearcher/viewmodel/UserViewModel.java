package com.example.android.githubresearcher.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.android.githubresearcher.model.repository.DataRepository;
import com.example.android.githubresearcher.model.repository.entities.UserEntity;

public class UserViewModel extends AndroidViewModel {

    private final LiveData<UserEntity> mObservableUser;

    public ObservableField<UserEntity> user = new ObservableField<>();

    private final int mUserId;

    public UserViewModel(@NonNull Application application, DataRepository repository, final int userId) {
        super(application);
        mUserId = userId;

        mObservableUser = repository.loadUser(mUserId);
    }

    public LiveData<UserEntity> getmObservableUser() {
        return mObservableUser;
    }

    public void setUser(UserEntity user) {
        this.user.set(user);
    }
}
