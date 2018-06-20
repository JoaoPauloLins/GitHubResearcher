package com.example.android.githubresearcher.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.viewmodel.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    private UserViewModel userViewModel;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        username = intent.getStringExtra("Username");
        password = intent.getStringExtra("Password");

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        setDataRepositoriesFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private void setDataRepositoriesFragment() {

        boolean login = userViewModel.init(username, password);

        if (login) {
            Bundle bundle = new Bundle();
            bundle.putString("AVATAR", userViewModel.getUser().getValue().getAvatar());
            bundle.putString("NOME", userViewModel.getUser().getValue().getName());
            bundle.putString("LOGIN", userViewModel.getUser().getValue().getLogin());
            bundle.putString("BIO", userViewModel.getUser().getValue().getBio());
            mSectionsPagerAdapter.getItem(0).setArguments(bundle);
//                    .observe(this, user -> {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("AVATAR", user.getAvatar());
//                        bundle.putString("NOME", user.getName());
//                        bundle.putString("LOGIN", user.getLogin());
//                        bundle.putString("BIO", user.getBio());
//                        mSectionsPagerAdapter.getItem(0).setArguments(bundle);
//                    });
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private RepositoriesFragment repositoriesFragment;
        private SearchFragment searchFragment;
        private ListFragment listFragment;

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            repositoriesFragment = new RepositoriesFragment();
            searchFragment = new SearchFragment();
            listFragment = new ListFragment();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return this.repositoriesFragment;
                case 1:
                    return this.searchFragment;
                case 2:
                    return this.listFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
