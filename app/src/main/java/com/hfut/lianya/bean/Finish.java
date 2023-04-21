package com.hfut.lianya.bean;

import java.util.Date;

public class Finish {
    private Integer id;
    private String fkid;
    private String kshno;
    private String layer;
    private String region;
    private String line;
    private String segment;
    private double gtma;
    private String factory;
    private int start;
    private int end;
    private Date startTime;
    private Date endTime;
    private String name;
    private String workernumber;
    private String workbenchid;
    private Integer duration;

    private Integer num;

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

    public String getKshno() {
        return kshno;
    }

    public void setKshno(String kshno) {
        this.kshno = kshno;
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

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public double getGtma() {
        return gtma;
    }

    public void setGtma(double gtma) {
        this.gtma = gtma;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkernumber() {
        return workernumber;
    }

    public void setWorkernumber(String workernumber) {
        this.workernumber = workernumber;
    }

    public String getWorkbenchid() {
        return workbenchid;
    }

    public void setWorkbenchid(String workbenchid) {
        this.workbenchid = workbenchid;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
