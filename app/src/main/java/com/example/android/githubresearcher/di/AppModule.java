package com.example.android.githubresearcher.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.android.githubresearcher.api.GithubService;
import com.example.android.githubresearcher.db.GithubDb;
import com.example.android.githubresearcher.db.UserDao;
import com.example.android.githubresearcher.util.LiveDataCallAdapterFactory;

import java.io.IOException;

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
    GithubService provideGithubService() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("User","jplo@cin.ufpe.br"+":"+"#Kronos1907")
                    .method(original.method(),original.body())
                    .build();

            return chain.proceed(request);
        });

        OkHttpClient client = httpClient.build();

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
}
