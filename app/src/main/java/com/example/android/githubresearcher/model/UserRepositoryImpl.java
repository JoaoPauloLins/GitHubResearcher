package com.example.android.githubresearcher.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class UserRepositoryImpl implements UserRepository {

    private GitHubService gitHubService;

    private final AppDatabase appDatabase;

    @Inject
    public UserRepositoryImpl(GitHubService gitHubService, @NonNull final AppDatabase appDatabase) {
        this.gitHubService = gitHubService;
        this.appDatabase = appDatabase;
    }

    @Override
    public LiveData<User> getUser(String login, String password) {

        final MutableLiveData<User> data = new MutableLiveData<>();

        Observable<User> userObservable = gitHubService.getUser(login, password);

        userObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    if(user != null && appDatabase.userDao().selectUser(user.getId()) == null) {
                        appDatabase.userDao().insertUser(user);
                    }
                    data.setValue(user);
                });

        return data;
    }
}
