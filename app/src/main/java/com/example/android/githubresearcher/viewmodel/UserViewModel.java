package com.example.android.githubresearcher.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.githubresearcher.model.UserEntity;
import com.example.android.githubresearcher.model.UserRepository;

import javax.inject.Inject;

public class UserViewModel extends ViewModel {

    private UserRepository userRepository;

    private LiveData<UserEntity> user;

    @Inject
    public UserViewModel (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean init(String login, String password) {
        user = userRepository.getUser(login, password);
        return user != null;
    }
    public LiveData<UserEntity> getUser() {
        return user;
    }
}
