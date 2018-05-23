package com.example.android.githubresearcher.view;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

    @BindView(R.id.usernameWrapper)
    TextInputLayout usernameWrapper;

    @BindView(R.id.passwordWrapper)
    TextInputLayout passwordWrapper;

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.user)
    TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        usernameWrapper.setHint("Username or email address");
        passwordWrapper.setHint("Password");
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
                    user.setText("Login: "+userPojo.getLogin()+"\n\n\n"
                            +"Name: "+userPojo.getName()+"\n\n\n"
                            +"Location: "+userPojo.getLocation());
                });
    }

    public void singUp(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/join?source=header-home"));
        startActivity(intent);
    }
}
