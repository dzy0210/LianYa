package com.hfut.lianya.bean;

public class Fkpb {
    Integer id;
    String fkid;
    String layer;
    String region;
    String line;
    Integer segment;
    Integer diff;
    Double gtma;
    String nextStation;
    String nextLayer;
    String des;
    String saveTime;
    String factory;
    String date;
    Integer start;
    Integer end;
    String l1Day;
    int status;
    String startTime;
    String endTime;
    String currentWorkerNo;
    String currentWorkstation;
    String workerNumber;
    String nextWorkStation;
    String day;
    String packageId;
    Integer priority;
    String url;
    Integer state;

    @Override
    public String toString() {
        return "Fkpb{" +
                "id=" + id +
                ", fkid='" + fkid + '\'' +
                ", layer='" + layer + '\'' +
                ", region='" + region + '\'' +
                ", line='" + line + '\'' +
                ", segment=" + segment +
                ", diff=" + diff +
                ", gtma=" + gtma +
                ", nextStation='" + nextStation + '\'' +
                ", nextLayer='" + nextLayer + '\'' +
                ", des='" + des + '\'' +
                ", saveTime='" + saveTime + '\'' +
                ", factory='" + factory + '\'' +
                ", date='" + date + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", l1Day='" + l1Day + '\'' +
                ", status=" + status +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", currentWorkerNo='" + currentWorkerNo + '\'' +
                ", currentWorkstation='" + currentWorkstation + '\'' +
                ", workerNumber='" + workerNumber + '\'' +
                ", nextWorkStation='" + nextWorkStation + '\'' +
                ", day='" + day + '\'' +
                ", packageId='" + packageId + '\'' +
                ", priority=" + priority +
                ", url='" + url + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFkid() {
        return fkid;
    }

    public void setFkid(String fkid) {
        this.fkid = fkid;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Integer getSegment() {
        return segment;
    }

    public void setSegment(Integer segment) {
        this.segment = segment;
    }

    public Integer getDiff() {
        return diff;
    }

    public void setDiff(Integer diff) {
        this.diff = diff;
    }

    public Double getGtma() {
        return gtma;
    }

    public void setGtma(Double gtma) {
        this.gtma = gtma;
    }

    public String getNextStation() {
        return nextStation;
    }

    public void setNextStation(String nextStation) {
        this.nextStation = nextStation;
    }

    public String getNextLayer() {
        return nextLayer;
    }

    public void setNextLayer(String nextLayer) {
        this.nextLayer = nextLayer;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getL1Day() {
        return l1Day;
    }

    public void setL1Day(String l1Day) {
        this.l1Day = l1Day;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getCurrentWorkerNo() {
        return currentWorkerNo;
    }

    public void setCurrentWorkerNo(String currentWorkerNo) {
        this.currentWorkerNo = currentWorkerNo;
    }

    public String getCurrentWorkstation() {
        return currentWorkstation;
    }

    public void setCurrentWorkstation(String currentWorkstation) {
        this.currentWorkstation = currentWorkstation;
    }

    public String getWorkerNumber() {
        return workerNumber;
    }

    public void setWorkerNumber(String workerNumber) {
        this.workerNumber = workerNumber;
    }

    public String getNextWorkStation() {
        return nextWorkStation;
    }

    public void setNextWorkStation(String nextWorkStation) {
        this.nextWorkStation = nextWorkStation;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
