package com.hfut.lianya.bean;

public class Deliveries {
    String Line;
    String currentPosition;
    String nextPosition;
    String courier;
    Integer num;
    String serialNo;

    public Deliveries(String line, String currentPosition, String nextPosition, String courier, Integer num, String serialNo) {
        Line = line;
        this.currentPosition = currentPosition;
        this.nextPosition = nextPosition;
        this.courier = courier;
        this.num = num;
        this.serialNo = serialNo;
    }

    public String getLine() {
        return Line;
    }

    public void setLine(String line) {
        Line = line;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getNextPosition() {
        return nextPosition;
    }

    public void setNextPosition(String nextPosition) {
        this.nextPosition = nextPosition;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @Override
    public String toString() {
        return "Deliveries{" +
                "Line='" + Line + '\'' +
                ", currentPosition='" + currentPosition + '\'' +
                ", nextPosition='" + nextPosition + '\'' +
                ", courier='" + courier + '\'' +
                ", num=" + num +
                ", serialNo='" + serialNo + '\'' +
                '}';
    }
}
