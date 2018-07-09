package com.example.android.githubresearcher.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;

import com.example.android.githubresearcher.AppExecutors;
import com.example.android.githubresearcher.api.ApiResponse;
import com.example.android.githubresearcher.api.AuthenticationHeader;
import com.example.android.githubresearcher.api.GithubService;
import com.example.android.githubresearcher.db.UserDao;
import com.example.android.githubresearcher.util.Objects;
import com.example.android.githubresearcher.vo.Resource;
import com.example.android.githubresearcher.vo.User;

public class UserRepositoryImpl implements UserRepository{

    private final AppExecutors appExecutors;
    private final UserDao userDao;
    private final GithubService githubService;
    private final AuthenticationHeader authenticationHeader;

    private final MediatorLiveData<Resource<User>> result = new MediatorLiveData<>();

    public UserRepositoryImpl(AppExecutors appExecutors,
                              UserDao userDao,
                              GithubService githubService,
                              AuthenticationHeader authenticationHeader) {
        this.appExecutors = appExecutors;
        this.userDao = userDao;
        this.githubService = githubService;
        this.authenticationHeader = authenticationHeader;
    }

    @MainThread
    private void setValue(Resource<User> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    @Override
    public LiveData<Resource<User>> loadUser(String authentication) {

        authenticationHeader.setAuthentication(authentication);

        String username = authentication.split(":")[0];

        LiveData<User> dbSource = userDao.findByUsername(username);
        result.addSource(dbSource, data -> {
            if (data == null) {
                result.removeSource(dbSource);
                LiveData<ApiResponse<User>> apiResponse = githubService.getUser();
                result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
                result.addSource(apiResponse, response -> {
                    result.removeSource(apiResponse);
                    result.removeSource(dbSource);
                    //noinspection ConstantConditions
                    if (response.isSuccessful()) {
                        appExecutors.diskIO().execute(() -> {
                            User user = response.body;
                            userDao.insertUser(user);
                            result.addSource(userDao.findByUsername(username),
                                    newData -> setValue(Resource.success(user)));
                        });
                    } else {
                        result.addSource(dbSource,
                                newData -> setValue(Resource.error(response.errorMessage, newData)));
                    }
                });
            } else {
                result.removeSource(dbSource);
                LiveData<ApiResponse<User>> apiResponse = githubService.getUser();
                result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
                result.addSource(apiResponse, response -> {
                    result.removeSource(apiResponse);
                    result.removeSource(dbSource);
                    //noinspection ConstantConditions
                    if (response.isSuccessful()) {
                        assert response.body != null;
                        if (response.body.login != null) {
                            setValue(Resource.success(data));
                        } else {
                            setValue(Resource.success(response.body));
                        }
                    } else {
                        result.addSource(dbSource,
                                newData -> setValue(Resource.error(response.errorMessage, newData)));
                    }
                });
            }
        });

        return result;
    }

    @Override
    public LiveData<User> getUser(String login) {
        return userDao.findByUsername(login);
    }
}
