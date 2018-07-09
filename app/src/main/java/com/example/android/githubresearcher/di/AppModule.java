package com.example.android.githubresearcher.di;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;

import com.example.android.githubresearcher.AppExecutors;
import com.example.android.githubresearcher.AppExecutorsImpl;
import com.example.android.githubresearcher.api.AuthenticationHeader;
import com.example.android.githubresearcher.api.AuthenticationHeaderImpl;
import com.example.android.githubresearcher.api.GithubService;
import com.example.android.githubresearcher.db.GithubDb;
import com.example.android.githubresearcher.db.RepoDao;
import com.example.android.githubresearcher.db.RepoListDao;
import com.example.android.githubresearcher.db.UserDao;
import com.example.android.githubresearcher.db.UserListDao;
import com.example.android.githubresearcher.repository.RepoListRepository;
import com.example.android.githubresearcher.repository.RepoListRepositoryImpl;
import com.example.android.githubresearcher.repository.RepoRepository;
import com.example.android.githubresearcher.repository.RepoRepositoryImpl;
import com.example.android.githubresearcher.repository.SearchRepository;
import com.example.android.githubresearcher.repository.SearchRepositoryImpl;
import com.example.android.githubresearcher.repository.UserListRepository;
import com.example.android.githubresearcher.repository.UserListRepositoryImpl;
import com.example.android.githubresearcher.repository.UserRepository;
import com.example.android.githubresearcher.repository.UserRepositoryImpl;
import com.example.android.githubresearcher.util.LiveDataCallAdapterFactory;
import com.example.android.githubresearcher.viewmodel.GithubViewModelFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelProviderFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        return new GithubViewModelFactory(creators);
    }

    @Singleton
    @Provides
    AppExecutors provideAppExecutors() {
        return new AppExecutorsImpl(Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(3),
                new AppExecutorsImpl.MainThreadExecutor());
    }

    @Singleton
    @Provides
    AuthenticationHeader provideAuthenticationHeader() {
        return new AuthenticationHeaderImpl();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(AuthenticationHeader authenticationHeader) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("User", authenticationHeader.getAuthentication())
                    .method(original.method(),original.body())
                    .build();

            return chain.proceed(request);
        });

        return httpClient.build();
    }

    @Singleton
    @Provides
    GithubService provideGithubService(OkHttpClient client) {

        return new Retrofit.Builder()
                .baseUrl("http://git-researcher-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(client)
                .build()
                .create(GithubService.class);
    }

    @Singleton
    @Provides
    GithubDb provideDb(Application app) {
        return Room.databaseBuilder(app, GithubDb.class,"github.db").build();
    }

    @Singleton
    @Provides
    UserDao provideUserDao(GithubDb db) {
        return db.userDao();
    }

    @Singleton
    @Provides
    RepoDao provideRepoDao(GithubDb db) {
        return db.repoDao();
    }

    @Singleton
    @Provides
    UserListDao provideUserListDao(GithubDb db) {
        return db.userListDao();
    }

    @Singleton
    @Provides
    RepoListDao provideRepoListDao(GithubDb db) {
        return db.repoListDao();
    }

    @Singleton
    @Provides
    UserRepository provideUserRepository(AppExecutors appExecutors,
                                         UserDao userDao,
                                         GithubService githubService,
                                         AuthenticationHeader authenticationHeader) {
        return new UserRepositoryImpl(appExecutors, userDao, githubService, authenticationHeader);
    }

    @Singleton
    @Provides
    RepoRepository provideRepoRepository(AppExecutors appExecutors,
                                         RepoDao repoDao,
                                         GithubService githubService) {
        return new RepoRepositoryImpl(appExecutors, repoDao, githubService);
    }

    @Singleton
    @Provides
    SearchRepository provideSearchRepository(GithubService githubService) {
        return new SearchRepositoryImpl(githubService);
    }

    @Singleton
    @Provides
    UserListRepository provideUserListRepository(AppExecutors appExecutors,
                                                 UserListDao userListDao) {
        return new UserListRepositoryImpl(appExecutors, userListDao);
    }

    @Singleton
    @Provides
    RepoListRepository provideRepoListRepository(AppExecutors appExecutors,
                                                 RepoListDao repoListDao) {
        return new RepoListRepositoryImpl(appExecutors, repoListDao);
    }
}
