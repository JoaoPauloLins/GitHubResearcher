package com.example.android.githubresearcher;

import android.app.Application;

import com.example.android.githubresearcher.model.repository.DataRepository;
import com.example.android.githubresearcher.model.repository.database.AppDatabase;

public class GitHubResearcherApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
