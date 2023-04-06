package com.hfut.lianya.bean;

public class ModelQrcode {
    Integer state;
    String barCodeN;
    String procTix2;
    String sizee;
    Integer procSeq;
    Integer qty;
    String currentPosition;
    String nextPosition;
    String courier;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getBarCodeN() {
        return barCodeN;
    }

    public void setBarCodeN(String barCodeN) {
        this.barCodeN = barCodeN;
    }

    public String getProcTix2() {
        return procTix2;
    }

    public void setProcTix2(String procTix2) {
        this.procTix2 = procTix2;
    }

    public String getSizee() {
        return sizee;
    }

    public void setSizee(String sizee) {
        this.sizee = sizee;
    }

    public Integer getProcSeq() {
        return procSeq;
    }

    public void setProcSeq(Integer procSeq) {
        this.procSeq = procSeq;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
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

    @Override
    public String toString() {
        return "ModelQrcode{" +
                "state=" + state +
                ", barCodeN='" + barCodeN + '\'' +
                ", procTix2='" + procTix2 + '\'' +
                ", sizee='" + sizee + '\'' +
                ", procSeq=" + procSeq +
                ", qty=" + qty +
                ", currentPosition='" + currentPosition + '\'' +
                ", nextPosition='" + nextPosition + '\'' +
                ", courier='" + courier + '\'' +
                '}';
    }
}
