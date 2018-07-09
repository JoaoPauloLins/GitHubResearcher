package com.example.android.githubresearcher.vo;

import com.google.gson.annotations.SerializedName;

public class Readme {

    @SerializedName("name")
    public final String name;
    @SerializedName("content")
    public final String content;

    public Readme(String name, String content) {
        this.name = name;
        this.content = content;
    }
}
