package com.example.android.githubresearcher.ui.login;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.di.Injectable;
import com.example.android.githubresearcher.ui.common.NavigationController;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment implements Injectable {

    @BindView(R.id.description)
    TextView description;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    private LoginViewModel loginViewModel;

    public static LoginFragment create() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);

        description.setText(
                new StringBuilder()
                        .append("Find users or organizations from GitHub;\n")
                        .append("Explore their public repositories;\n")
                        .append("Quickly edit yours repositories markdown and easily commit.")
        );
    }

    @OnClick(R.id.sign_in)
    public void signIn(View view) {
        // Implementar login
    }

    @OnClick(R.id.sign_up)
    public void signUp(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/join?source=header-home"));
        startActivity(intent);
    }

}
