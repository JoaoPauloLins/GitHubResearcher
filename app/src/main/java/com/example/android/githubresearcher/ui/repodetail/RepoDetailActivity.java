package com.example.android.githubresearcher.ui.repodetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.vo.Repo;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RepoDetailActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);

        Repo repo = (Repo)getIntent().getSerializableExtra("Repo");

        FragmentManager fragmentManager = getSupportFragmentManager();
        int containerId = R.id.container_detail;
        if (savedInstanceState == null) {
            RepoDetailFragment repoDetailFragment = RepoDetailFragment.create(repo);
            fragmentManager.beginTransaction()
                    .replace(containerId, repoDetailFragment)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
