package com.example.android.githubresearcher.di;

import com.example.android.githubresearcher.ui.login.LoginFragment;
import com.example.android.githubresearcher.ui.login.LoginFragment;
import com.example.android.githubresearcher.ui.menu.ListFragment;
import com.example.android.githubresearcher.ui.menu.RepositoriesFragment;
import com.example.android.githubresearcher.ui.menu.SearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract LoginFragment contributeUserFragment();

    @ContributesAndroidInjector
    abstract RepositoriesFragment contributeRepositoriesFragment();

    @ContributesAndroidInjector
    abstract SearchFragment contributeSearchFragment();

    @ContributesAndroidInjector
    abstract ListFragment contributeListFragment();
}
