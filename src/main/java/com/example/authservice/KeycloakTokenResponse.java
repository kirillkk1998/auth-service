package com.example.authservice;

public class KeycloakTokenResponse {
    private String access_token;
    private String refresh_token;
    private int expires_in;
    private int refresh_expires_in;

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public void setRefreshToken(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpiresIn() {
        return expires_in;
    }

    public void setExpiresIn(int expires_in) {
        this.expires_in = expires_in;
    }

    public int getRefreshExpiresIn() {
        return refresh_expires_in;
    }

    public void setRefreshExpiresIn(int refresh_expires_in) {
        this.refresh_expires_in = refresh_expires_in;
    }
}