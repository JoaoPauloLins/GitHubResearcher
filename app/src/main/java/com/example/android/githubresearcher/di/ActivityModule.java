package com.example.android.githubresearcher.di;

import com.example.android.githubresearcher.LoginActivity;
import com.example.android.githubresearcher.MenuActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MenuActivity contributeMenuActivity();
}
