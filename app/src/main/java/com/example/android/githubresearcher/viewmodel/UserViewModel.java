package com.example.android.githubresearcher.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.githubresearcher.model.User;
import com.example.android.githubresearcher.model.UserRepository;

import javax.inject.Inject;

public class UserViewModel extends ViewModel {

    private UserRepository userRepository;

    private LiveData<User> user;

    @Inject
    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void init(String login, String password) {
        if (this.user == null) {
            user = userRepository.getUser(login, password);
        }
    }
    public LiveData<User> getUser() {
        return user;
    }
}
