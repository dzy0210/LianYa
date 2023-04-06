package com.hfut.lianya.bean;

public class Notification {
    private Integer id;
    private char state;
    private String senderNo;
    private String sender;
    private String receiverNo;
    private String receiver;
    private char notificationType;
    private String sendTime;
    private String readTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
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

    public char getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(char notificationType) {
        this.notificationType = notificationType;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", state=" + state +
                ", senderNo='" + senderNo + '\'' +
                ", sender='" + sender + '\'' +
                ", receiverNo='" + receiverNo + '\'' +
                ", receiver='" + receiver + '\'' +
                ", notificationType=" + notificationType +
                ", sendTime='" + sendTime + '\'' +
                ", readTime='" + readTime + '\'' +
                '}';
    }
}
