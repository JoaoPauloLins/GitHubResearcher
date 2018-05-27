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

import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.model.repository.entities.UserEntity;
import com.example.android.githubresearcher.model.service.GitHubService;
import com.example.android.githubresearcher.model.service.pojo.UserPojo;
import com.example.android.githubresearcher.viewmodel.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.usernameWrapper)
    TextInputLayout usernameWrapper;

    @BindView(R.id.passwordWrapper)
    TextInputLayout passwordWrapper;

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.description)
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        usernameWrapper.setHint("Username or email address");
        passwordWrapper.setHint("Password");

        description.setText(
                new StringBuilder()
                .append("Find users or organizations from GitHub;\n")
                .append("Explore their public repositories;\n")
                .append("Quickly edit yours repositories markdown and easily commit.")
        );
    }

    public void signIn(View view) {
        // TODO: Ir para a tela de Menu, já com o usuário logado.

        final UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://git-researcher-api.herokuapp.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService gitHubService = retrofit.create(GitHubService.class);
        Observable<UserPojo> userPojoObservable = gitHubService.getUser(
                this.username.getText().toString(),
                this.password.getText().toString());

        userPojoObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userPojo -> {
                    UserEntity userEntity = new UserEntity(userPojo);
                    userViewModel.setUser(userEntity);
                });

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void singUp(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/join?source=header-home"));
        startActivity(intent);
    }
}
