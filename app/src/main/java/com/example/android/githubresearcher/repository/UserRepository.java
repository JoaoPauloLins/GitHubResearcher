package com.example.android.githubresearcher.repository;

import android.arch.lifecycle.LiveData;

import com.example.android.githubresearcher.vo.Resource;
import com.example.android.githubresearcher.vo.User;

public interface UserRepository {

    LiveData<Resource<User>> loadUser(String authentication);
    LiveData<User> getUser(int id);
}
