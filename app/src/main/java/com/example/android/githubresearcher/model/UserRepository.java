package com.example.android.githubresearcher.model;

import android.arch.lifecycle.LiveData;
import android.content.Context;

public interface UserRepository {

    LiveData<UserEntity> getUser(String login, String password);
}
