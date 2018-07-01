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
import com.example.android.githubresearcher.viewmodel.UserViewModel;

import javax.inject.Inject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private UserViewModel userViewModel;

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

        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
    }

    @OnClick(R.id.sign_in)
    public void signIn(View view) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("User",username.getText().toString()+":"+password.getText().toString())
                        .method(original.method(),original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://git-researcher-api.herokuapp.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        GitHubService gitHubService = retrofit.create(GitHubService.class);
        Observable<UserPojo> userPojoObservable = gitHubService.getUser();

        userPojoObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userPojo -> {
                    if (userPojo.getName() != null) {
                        Intent intent = new Intent(this, MenuActivity.class);
                        intent.putExtra("UserPojo", userPojo);
                        startActivity(intent);
                    } else {
                        mensagem = "Usuário/Senha inválidos";
                    }
                    Toast.makeText(this.getApplicationContext(), mensagem, Toast.LENGTH_SHORT).show();
                });
    
    }

    @OnClick(R.id.sign_up)
    public void signUp(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/join?source=header-home"));
        startActivity(intent);
    }
}
