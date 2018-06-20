package com.example.android.githubresearcher.view;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.common.GitHubResearcherViewModelFactory;
import com.example.android.githubresearcher.viewmodel.UserViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username_wrapper)
    TextInputLayout usernameWrapper;

    @BindView(R.id.password_wrapper)
    TextInputLayout passwordWrapper;

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.description)
    TextView description;

    private UserViewModel userViewModel;

    @Inject
    GitHubResearcherViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        userViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);

        usernameWrapper.setHint("Username or email address");
        passwordWrapper.setHint("Password");

        description.setText(
                new StringBuilder()
                .append("Find users or organizations from GitHub;\n")
                .append("Explore their public repositories;\n")
                .append("Quickly edit yours repositories markdown and easily commit.")
        );
    }

    @OnClick(R.id.sign_in)
    public void signIn(View view) {

        boolean login = userViewModel.init(
                username.getText().toString(),
                password.getText().toString());

        if (login) {
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("Username", username.getText().toString());
            intent.putExtra("Password", password.getText().toString());
            startActivity(intent);
        }
        else {
            Toast.makeText(
                    this.getApplicationContext(),
                    "Usuário/Senha inválidos",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.sign_up)
    public void signUp(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/join?source=header-home"));
        startActivity(intent);
    }
}
