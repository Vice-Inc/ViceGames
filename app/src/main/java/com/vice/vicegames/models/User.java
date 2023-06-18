package com.vice.vicegames.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String name = "", email = "";
    private String vkURL = "", telegramURL = "", instagramURL = "";

    public User(){}

    public User(JSONObject userDataJson){
        try {
            name = userDataJson.getString("userName");
        } catch (JSONException ignored) {}
        try {
            email = userDataJson.getString("email");
        } catch (JSONException ignored) {}
        try {
            vkURL = userDataJson.getString("vkURL");
        } catch (JSONException ignored) {}
        try {
            telegramURL = userDataJson.getString("telegramURL");
        } catch (JSONException ignored) {}
        try {
            instagramURL = userDataJson.getString("instagramURL");
        } catch (JSONException ignored) {}
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVkURL() {
        return vkURL;
    }

    public void setVkURL(String vkURL) {
        this.vkURL = vkURL;
    }

    public String getTelegramURL() {
        return telegramURL;
    }

    public void setTelegramURL(String telegramUrl) {
        this.telegramURL = telegramUrl;
    }

    public String getInstagramURL() {
        return instagramURL;
    }

    public void setInstagramURL(String instagramURL) {
        this.instagramURL = instagramURL;
    }
}
