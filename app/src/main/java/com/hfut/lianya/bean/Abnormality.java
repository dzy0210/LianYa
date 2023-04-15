package com.hfut.lianya.bean;

public class Abnormality {
    private Integer id;
    private Integer state;
    private String senderNo;
    private String sender;
    private String receiverNo;
    private String receiver;
    private String workstation;
    private String packageId;
    private Integer abnormalityType;
    private String abnormalityDesc;
    private String sendTime;
    private String solveTime;

    public Abnormality(Integer id, Integer state, String senderNo, String sender, String receiverNo, String receiver, String workstation, String packageId, Integer abnormalityType, String abnormalityDesc, String sendTime, String solveTime) {
        this.id = id;
        this.state = state;
        this.senderNo = senderNo;
        this.sender = sender;
        this.receiverNo = receiverNo;
        this.receiver = receiver;
        this.workstation = workstation;
        this.packageId = packageId;
        this.abnormalityType = abnormalityType;
        this.abnormalityDesc = abnormalityDesc;
        this.sendTime = sendTime;
        this.solveTime = solveTime;
    }

    public Abnormality() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSenderNo() {
        return senderNo;
    }

    public void setSenderNo(String senderNo) {
        this.senderNo = senderNo;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiverNo() {
        return receiverNo;
    }

    public void setReceiverNo(String receiverNo) {
        this.receiverNo = receiverNo;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getWorkstation() {
        return workstation;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getAbnormalityType() {
        return abnormalityType;
    }

    public void setAbnormalityType(Integer abnormalityType) {
        this.abnormalityType = abnormalityType;
    }

    public String getAbnormalityDesc() {
        return abnormalityDesc;
    }

    public void setAbnormalityDesc(String abnormalityDesc) {
        this.abnormalityDesc = abnormalityDesc;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSolveTime() {
        return solveTime;
    }

    public void setSolveTime(String solveTime) {
        this.solveTime = solveTime;
    }

    @Override
    public String toString() {
        return "Abnormality{" +
                "id=" + id +
                ", state=" + state +
                ", senderNo='" + senderNo + '\'' +
                ", sender='" + sender + '\'' +
                ", receiverNo='" + receiverNo + '\'' +
                ", receiver='" + receiver + '\'' +
                ", workstation='" + workstation + '\'' +
                ", packageId='" + packageId + '\'' +
                ", abnormalityType=" + abnormalityType +
                ", abnormalityDesc='" + abnormalityDesc + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", solveTime='" + solveTime + '\'' +
                '}';
    }
}
