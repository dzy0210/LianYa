package com.hfut.lianya.bean;

public class User {
    private long id;
    private String userNo;
    private String password;
    private String phone;

    @Override
    public String toString() {
        return "User{" +
                "userNo='" + userNo + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
