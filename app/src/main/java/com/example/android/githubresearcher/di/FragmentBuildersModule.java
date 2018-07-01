package com.example.android.githubresearcher.di;

import com.example.android.githubresearcher.ui.login.LoginFragment;
import com.example.android.githubresearcher.ui.login.LoginFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract LoginFragment contributeUserFragment();
}
