package com.example.android.githubresearcher.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.githubresearcher.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.imageUser)
    ImageView userAvatar;

    @BindView(R.id.nomeUser)
    TextView nomeUser;

    @BindView(R.id.loginUser)
    TextView loginUser;

    @BindView(R.id.bioUser)
    TextView bioUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        String nome = extras.getString("NOME");
        String login = extras.getString("LOGIN");
        String url = extras.getString("AVATAR");
        nomeUser.setText(extras.getString("NOME"));
        loginUser.setText(extras.getString("LOGIN"));
        bioUser.setText(extras.getString("BIO"));
        Picasso.with(this).load(extras.getString("AVATAR")).into(userAvatar);
    }
}
