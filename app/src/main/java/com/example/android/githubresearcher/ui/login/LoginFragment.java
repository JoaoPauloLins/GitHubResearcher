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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.di.Injectable;
import com.example.android.githubresearcher.ui.common.NavigationController;
import com.example.android.githubresearcher.vo.Status;

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
        loginViewModel.authenticate(username.getText().toString(), password.getText().toString());
        loginViewModel.getUser().observe(this, userResource -> {
            if (userResource != null) {
                switch (userResource.status){
                    case LOADING:
                        Toast.makeText(
                                getActivity(),
                                "Carregando...",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCESS:
                        if (userResource.data != null && userResource.data.id != 0) {
                            Toast.makeText(
                                    getActivity(),
                                    "Logou com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(
                                    getActivity(),
                                    "Usuário/Senha inválidos.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case ERROR:
                        Toast.makeText(
                                getActivity(),
                                "Erro ao logar, tente novamente.",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            else {
                Toast.makeText(
                    getActivity(),
                    "Erro ao logar, tente novamente.",
                    Toast.LENGTH_SHORT).show();
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
