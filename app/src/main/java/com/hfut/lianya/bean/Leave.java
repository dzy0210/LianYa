package com.hfut.lianya.bean;

public class Leave {
    int id;
    char state;
    char leaveType;
    String sender;
    String senderNo;
    String submitter;
    String submitterNo;
    String dealer;
    String dealerNo;
    String leaveDesc;
    String startTime;
    String endTime;

    public Leave(char state, char leaveType, String sender, String senderNo, String submitter, String submitterNo, String leaveDesc, String startTime, String endTime) {
        this.state = state;
        this.leaveType = leaveType;
        this.sender = sender;
        this.senderNo = senderNo;
        this.submitter = submitter;
        this.submitterNo = submitterNo;
        this.leaveDesc = leaveDesc;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public Leave() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public char getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(char leaveType) {
        this.leaveType = leaveType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderNo() {
        return senderNo;
    }

    public void setSenderNo(String senderNo) {
        this.senderNo = senderNo;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getSubmitterNo() {
        return submitterNo;
    }

    public void setSubmitterNo(String submitterNo) {
        this.submitterNo = submitterNo;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getDealerNo() {
        return dealerNo;
    }

    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
    }

    public String getLeaveDesc() {
        return leaveDesc;
    }

    public void setLeaveDesc(String leaveDesc) {
        this.leaveDesc = leaveDesc;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Leave{" +
                "id=" + id +
                ", state=" + state +
                ", leaveType=" + leaveType +
                ", sender='" + sender + '\'' +
                ", senderNo='" + senderNo + '\'' +
                ", submitter='" + submitter + '\'' +
                ", submitterNo='" + submitterNo + '\'' +
                ", dealer='" + dealer + '\'' +
                ", dealerNo='" + dealerNo + '\'' +
                ", leaveDesc='" + leaveDesc + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
