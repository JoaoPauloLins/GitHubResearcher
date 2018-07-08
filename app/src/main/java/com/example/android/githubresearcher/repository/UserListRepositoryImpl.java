package com.example.android.githubresearcher.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;

import com.example.android.githubresearcher.AppExecutors;
import com.example.android.githubresearcher.db.UserListDao;
import com.example.android.githubresearcher.util.Objects;
import com.example.android.githubresearcher.vo.Resource;
import com.example.android.githubresearcher.vo.UserList;

import java.util.List;

public class UserListRepositoryImpl implements UserListRepository{

    private final AppExecutors appExecutors;
    private final UserListDao userListDao;

    public UserListRepositoryImpl(AppExecutors appExecutors,
                                  UserListDao userListDao) {
        this.appExecutors = appExecutors;
        this.userListDao = userListDao;
    }

    @Override
    public LiveData<List<UserList>> loadUserList(String userLogin) {

        return userListDao.findUserListByLogin(userLogin);
    }

    @Override
    public void saveUserList(UserList userList) {
        appExecutors.diskIO().execute(() -> userListDao.insertUserList(userList));
    }
}
