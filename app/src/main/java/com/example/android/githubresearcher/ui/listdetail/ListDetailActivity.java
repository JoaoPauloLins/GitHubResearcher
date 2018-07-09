package com.example.android.githubresearcher.ui.listdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.vo.UserList;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ListDetailActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);

        UserList userList = (UserList)getIntent().getSerializableExtra("List");
        FragmentManager fragmentManager = getSupportFragmentManager();
        int containerId = R.id.container_list_detail;
        if (savedInstanceState == null) {
            ListDetailFragment listDetailFragment = ListDetailFragment.create(userList);
            fragmentManager.beginTransaction()
                    .replace(containerId, listDetailFragment)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
