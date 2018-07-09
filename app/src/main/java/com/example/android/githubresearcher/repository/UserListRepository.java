package com.example.android.githubresearcher.repository;

import android.arch.lifecycle.LiveData;

import com.example.android.githubresearcher.vo.Resource;
import com.example.android.githubresearcher.vo.UserList;

import java.util.List;

public interface UserListRepository {

    LiveData<List<UserList>> loadUserList(int userId);
    LiveData<List<UserList>> loadUserListByRepo(int repoId);
    void saveUserList(UserList userList);
}
