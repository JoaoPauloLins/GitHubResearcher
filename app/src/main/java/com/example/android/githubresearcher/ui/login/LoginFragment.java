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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.githubresearcher.MenuActivity;
import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.di.Injectable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment implements Injectable {

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.progress_bar_login)
    ProgressBar progressBar;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

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

        progressBar.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.sign_in)
    public void signIn(View view) {
        loginViewModel.authenticate(username.getText().toString(), password.getText().toString());
        loginViewModel.getUser().observe(this, userResource -> {
            if (userResource != null) {
                switch (userResource.status){
                    case LOADING:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        progressBar.setVisibility(View.INVISIBLE);
                        if (userResource.data != null && userResource.data.login != null) {
                            Intent intent = new Intent(getActivity(), MenuActivity.class);
                            intent.putExtra("User", userResource.data);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(
                                    getActivity(),
                                    "Usuário/Senha inválidos.",
                                    Toast.LENGTH_LONG).show();
                        }
                        break;
                    case ERROR:
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(
                                getActivity(),
                                "Erro ao logar, tente novamente mais tarde.",
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }
            else {
                Toast.makeText(
                    getActivity(),
                    "Erro ao logar, tente novamente.",
                    Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.sign_up)
    public void signUp(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/join?source=header-home"));
        startActivity(intent);
    }

}
