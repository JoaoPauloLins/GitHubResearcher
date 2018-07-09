package com.example.android.githubresearcher.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.MainThread;

import com.example.android.githubresearcher.AppExecutors;
import com.example.android.githubresearcher.db.UserListDao;
import com.example.android.githubresearcher.util.Objects;
import com.example.android.githubresearcher.vo.Resource;
import com.example.android.githubresearcher.vo.UserList;

import java.util.ArrayList;
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
    public LiveData<List<UserList>> loadUserList(int userId) {

        return userListDao.findUserListByUserId(userId);
    }

    @Override
    public LiveData<List<UserList>> loadUserListByRepo(int repoId) {

        MutableLiveData<List<UserList>> userListsLiveData = new MutableLiveData<>();
        List<UserList> userLists = new ArrayList<>();
        appExecutors.diskIO().execute(() -> {
            List<Integer> userListIds = userListDao.findUserListIdByRepoId(repoId);
            for (int userListId: userListIds) {
                userLists.add(userListDao.findUserListById(userListId));
            }
            userListsLiveData.postValue(userLists);
        });
        return userListsLiveData;
    }

    @Override
    public void saveUserList(UserList userList) {

        LiveData<List<UserList>> userLists = userListDao.findUserListByName(userList.userId, userList.name);
        if (userLists.getValue() != null && userLists.getValue().size() > 0) {
            return;
        }
        appExecutors.diskIO().execute(() -> userListDao.insertUserList(userList));
    }
}
