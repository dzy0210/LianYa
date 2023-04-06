package com.hfut.lianya.bean;

import java.util.List;
import java.util.Map;
public class InitData {
    Map<String,String> abnormalityMap;
    List<String> leaveType;

    public Map<String, String> getAbnormalityMap() {
        return abnormalityMap;
    }

    public void setAbnormalityMap(Map<String, String> abnormalityMap) {
        this.abnormalityMap = abnormalityMap;
    }

    public List<String> getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(List<String> leaveType) {
        this.leaveType = leaveType;
    }

    @Override
    public String toString() {
        return "InitData{" +
                "abnormalityMap=" + abnormalityMap +
                ", leaveType=" + leaveType +
                '}';
    }
}
