package com.example.android.githubresearcher.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.android.githubresearcher.repository.UserRepository;
import com.example.android.githubresearcher.util.Objects;
import com.example.android.githubresearcher.vo.Resource;
import com.example.android.githubresearcher.vo.User;

import java.util.List;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private UserRepository userRepository;

    private MutableLiveData<String> authenticationLiveData = new MutableLiveData<>();

    private LiveData<Resource<User>> user = Transformations.switchMap(
            authenticationLiveData, authentication -> userRepository.loadUser(authentication)
    );

    @SuppressWarnings("unchecked")
    @Inject
    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void authenticate(String username, String password) {
        authenticationLiveData.setValue(username+":"+password);
    }

    public LiveData<Resource<User>> getUser() {
        return user;
    }
}
