package com.hfut.lianya.bean;

public class WorkerDoingTask {
    String workerNo;
    String workerName;
    Integer packageNum;
    Integer pieceNum;
    String remainingTime;

    public WorkerDoingTask(String workerNo, String workerName, Integer packageNum, Integer pieceNum, String remainingTime) {
        this.workerNo = workerNo;
        this.workerName = workerName;
        this.packageNum = packageNum;
        this.pieceNum = pieceNum;
        this.remainingTime = remainingTime;
    }

    public String getWorkerNo() {
        return workerNo;
    }

    public void setWorkerNo(String workerNo) {
        this.workerNo = workerNo;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public Integer getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(Integer packageNum) {
        this.packageNum = packageNum;
    }

    public Integer getPieceNum() {
        return pieceNum;
    }

    public void setPieceNum(Integer pieceNum) {
        this.pieceNum = pieceNum;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    @Override
    public String toString() {
        return "DoingTask{" +
                "workerNo='" + workerNo + '\'' +
                ", workerName='" + workerName + '\'' +
                ", packageNum=" + packageNum +
                ", pieceNum=" + pieceNum +
                ", remainingTime='" + remainingTime + '\'' +
                '}';
    }
}
