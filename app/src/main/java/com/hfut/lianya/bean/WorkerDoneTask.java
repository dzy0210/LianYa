package com.hfut.lianya.bean;

public class WorkerDoneTask {
    String workerNo;
    String workerName;
    Integer packageNum;
    Integer pieceNum;

    public WorkerDoneTask(String workerNo, String workerName, Integer packageNum, Integer pieceNum) {
        this.workerNo = workerNo;
        this.workerName = workerName;
        this.packageNum = packageNum;
        this.pieceNum = pieceNum;
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

    @Override
    public String toString() {
        return "WorkerDoneTask{" +
                "workerNo='" + workerNo + '\'' +
                ", workerName='" + workerName + '\'' +
                ", packageNum=" + packageNum +
                ", pieceNum=" + pieceNum +
                '}';
    }
}
