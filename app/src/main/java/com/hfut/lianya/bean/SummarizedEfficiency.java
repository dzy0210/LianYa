package com.hfut.lianya.bean;

public class SummarizedEfficiency {
    private Integer id;
    String userNo;
    String userName;
    Double threeDayEfficiency;
    Double todayEfficiency;

    public SummarizedEfficiency(Integer id, String userNo, String userName, double threeDayEfficiency, double todayEfficiency) {
        this.id = id;
        this.userNo = userNo;
        this.userName = userName;
        this.threeDayEfficiency = threeDayEfficiency;
        this.todayEfficiency = todayEfficiency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getThreeDayEfficiency() {
        return threeDayEfficiency;
    }

    public void setThreeDayEfficiency(double threeDayEfficiency) {
        this.threeDayEfficiency = threeDayEfficiency;
    }

    public Double getTodayEfficiency() {
        return todayEfficiency;
    }

    public void setTodayEfficiency(double todayEfficiency) {
        this.todayEfficiency = todayEfficiency;
    }

    @Override
    public String toString() {
        return "SummarizedEfficiency{" +
                "id=" + id +
                ", userNo='" + userNo + '\'' +
                ", userName='" + userName + '\'' +
                ", threeDayEfficiency=" + threeDayEfficiency +
                ", todayEfficiency=" + todayEfficiency +
                '}';
    }
}
