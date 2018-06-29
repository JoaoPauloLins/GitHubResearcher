package com.example.android.githubresearcher.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.githubresearcher.AppExecutors;
import com.example.android.githubresearcher.api.ApiResponse;
import com.example.android.githubresearcher.api.GithubService;
import com.example.android.githubresearcher.db.UserDao;
import com.example.android.githubresearcher.vo.Resource;
import com.example.android.githubresearcher.vo.User;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepository {
    private final UserDao userDao;
    private final GithubService githubService;
    private final AppExecutors appExecutors;

    @Inject
    UserRepository(AppExecutors appExecutors, UserDao userDao, GithubService githubService) {
        this.userDao = userDao;
        this.githubService = githubService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<User>> loadUser(String login) {
        return new NetworkBoundResource<User,User>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull User item) {
                userDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.findById(login);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return githubService.getUser();
            }
        }.asLiveData();
    }
}
