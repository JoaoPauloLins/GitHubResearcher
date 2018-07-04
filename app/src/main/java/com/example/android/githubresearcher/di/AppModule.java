package com.example.android.githubresearcher.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.android.githubresearcher.AppExecutors;
import com.example.android.githubresearcher.AppExecutorsImpl;
import com.example.android.githubresearcher.api.AuthenticationHeader;
import com.example.android.githubresearcher.api.AuthenticationHeaderImpl;
import com.example.android.githubresearcher.api.GithubService;
import com.example.android.githubresearcher.db.GithubDb;
import com.example.android.githubresearcher.db.RepoDao;
import com.example.android.githubresearcher.db.UserDao;
import com.example.android.githubresearcher.repository.RepoRepository;
import com.example.android.githubresearcher.repository.RepoRepositoryImpl;
import com.example.android.githubresearcher.repository.UserRepository;
import com.example.android.githubresearcher.repository.UserRepositoryImpl;
import com.example.android.githubresearcher.util.LiveDataCallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.Executors;

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
}
