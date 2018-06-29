package com.example.android.githubresearcher.di;

import com.example.android.githubresearcher.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class LoginActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract LoginActivity contributeMainActivity();
}
