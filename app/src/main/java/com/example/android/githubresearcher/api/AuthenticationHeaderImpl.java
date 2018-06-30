package com.example.android.githubresearcher.api;

public class AuthenticationHeaderImpl implements AuthenticationHeader {

    private String authentication;

    @Override
    public String getAuthentication() {
        return authentication;
    }

    @Override
    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }
}
