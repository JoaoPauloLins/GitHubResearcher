package com.example.android.githubresearcher.di;

import com.example.android.githubresearcher.ui.listdetail.ListDetailActivity;
import com.example.android.githubresearcher.ui.repodetail.RepoDetailActivity;
import com.example.android.githubresearcher.ui.login.LoginActivity;
import com.example.android.githubresearcher.ui.menu.MenuActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MenuActivity contributeMenuActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract RepoDetailActivity contributeRepoDetailActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract ListDetailActivity contributeListDetailActivity();
}
