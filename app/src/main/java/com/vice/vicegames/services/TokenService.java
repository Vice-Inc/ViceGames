package com.vice.vicegames.services;

public class TokenService {
    private static TokenService tokenService = null;

    public static TokenService getTokenService(){
        if(tokenService == null){
            tokenService = new TokenService();
        }
        return tokenService;
    }

    private String token = "";
    private String email = "";

    private TokenService(){}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
