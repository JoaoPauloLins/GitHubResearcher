package com.example.android.githubresearcher.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserRepositoryImpl implements UserRepository {

    private GitHubService gitHubService;
    private UserDao userDao;

    @Inject
    public UserRepositoryImpl(GitHubService gitHubService, UserDao userDao) {
        this.gitHubService = gitHubService;
        this.userDao = userDao;
    }

    @Override
    public LiveData<UserEntity> getUser(String login, String password) {

        final MutableLiveData<UserEntity> data = new MutableLiveData<>();

        Observable<UserEntity> userObservable = gitHubService.getUser(login, password);

        userObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    if(user != null && userDao.selectUser(user.getId()) == null) {
                        userDao.insertUser(user);
                    }
                });

        return data;
    }
}
