package com.example.android.githubresearcher.common;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.example.android.githubresearcher.model.AppDatabase;
import com.example.android.githubresearcher.model.GitHubService;
import com.example.android.githubresearcher.model.UserDao;
import com.example.android.githubresearcher.model.UserRepository;
import com.example.android.githubresearcher.model.UserRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(subcomponents = ViewModelSubComponent.class)
public class GitHubResearcherAppModule {

    @Provides
    @Singleton
    Context provideContext(GitHubResearcherApp gitHubResearcherApp) {
        return gitHubResearcherApp.getApplicationContext();
    }

    @Provides
    @Singleton
    UserDao provideUserDao(Context context) {
        return AppDatabase.getAppDatabase(context).userDao();
    }

    @Provides
    @Singleton
    public GitHubService provideGitHubService(RxJava2CallAdapterFactory rxJava2CallAdapterFactory,
                                         GsonConverterFactory gsonConverterFactory) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl("http://git-researcher-api.herokuapp.com/")
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .client(client)
                .build();

        return retrofit.create(GitHubService.class);
    }

    @Provides
    public RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory(){
        return RxJava2CallAdapterFactory.create();
    }

    @Provides GsonConverterFactory provideGsonConverterFactory(){
        return GsonConverterFactory.create();
    }

    @Provides
        public UserRepository provideGitHubResearcherRepository(GitHubService gitHubService, UserDao userDao){
        return new UserRepositoryImpl(gitHubService, userDao);
    }

    @Singleton
    @Provides
    GitHubResearcherViewModelFactory provideViewModelFactory(
            ViewModelSubComponent.Builder viewModelSubComponent) {

        return new GitHubResearcherViewModelFactory(viewModelSubComponent.build());
    }
}
