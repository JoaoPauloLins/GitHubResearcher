package com.example.android.githubresearcher.model.repository.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.android.githubresearcher.model.User;

@Entity(tableName = "user")
public class UserEntity implements User{

    @PrimaryKey
    final private int id;
    final private String login;
    final private String name;
    final private String avatarUrl;
    final private String reposUrl;
    final private String location;

    public UserEntity(int id,
                      String login,
                      String name,
                      String avatarUrl,
                      String reposUrl,
                      String location){
        this.id = id;
        this.login = login;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.reposUrl = reposUrl;
        this.location = location;
    }

    public UserEntity(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.name = (String) user.getName();
        this.avatarUrl = user.getAvatarUrl();
        this.reposUrl = user.getReposUrl();
        this.location = (String) user.getLocation();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Override
    public String getReposUrl() {
        return reposUrl;
    }

    @Override
    public String getLocation() {
        return location;
    }
}
