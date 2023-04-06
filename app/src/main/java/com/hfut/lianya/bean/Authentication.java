package com.hfut.lianya.bean;

public class Authentication {
    String token;
    int userType;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "token='" + token + '\'' +
                ", userType=" + userType +
                '}';
    }
}
