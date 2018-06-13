package com.example.android.githubresearcher.model;

import android.arch.lifecycle.LiveData;

public interface UserRepository {

    LiveData<User> getUser(String login, String password);
}
