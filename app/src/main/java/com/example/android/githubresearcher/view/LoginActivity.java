package com.example.android.githubresearcher.view;

import android.app.Activity;
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
import com.example.android.githubresearcher.repository.service.GitHubService;
import com.example.android.githubresearcher.repository.service.pojo.UserPojo;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    String mensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    public void signIn(View view) {
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
                    if(userPojo.getName() != null) {
                        mensagem = "Bem vindo "+userPojo.getName().toString();
                    } else {
                        mensagem = "Usuário/Senha inválidos";
                    }
                    Toast.makeText(this.getApplicationContext(), mensagem, Toast.LENGTH_SHORT).show();
                });
    }

    public void signUp(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/join?source=header-home"));
        startActivity(intent);
    }
}
