package com.example.android.githubresearcher.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.android.githubresearcher.repository.UserRepository;
import com.example.android.githubresearcher.util.AbsentLiveData;
import com.example.android.githubresearcher.util.Objects;
import com.example.android.githubresearcher.vo.Resource;
import com.example.android.githubresearcher.vo.User;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    final MutableLiveData<String> login = new MutableLiveData<>();

    private final LiveData<Resource<User>> user;
    @SuppressWarnings("unchecked")
    @Inject
    public LoginViewModel(UserRepository userRepository) {
        user = Transformations.switchMap(login, login -> {
            if (login == null) {
                return AbsentLiveData.create();
            } else {
                return userRepository.loadUser(login);
            }
        });
    }

    public void setLogin(String login) {
        if (Objects.equals(this.login.getValue(), login)) {
            return;
        }
        this.login.setValue(login);
    }

    public LiveData<Resource<User>> getUser() {
        return user;
    }

    public void retry() {
        if (this.login.getValue() != null) {
            this.login.setValue(this.login.getValue());
        }
    }
}
