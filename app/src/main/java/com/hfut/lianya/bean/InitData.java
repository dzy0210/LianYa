package com.hfut.lianya.bean;

import java.util.List;
import java.util.Map;
public class InitData {
    List<String> abnormalityType;
    List<String> abnormalityResponder;
    List<String> leaveType;

    public List<String> getAbnormalityType() {
        return abnormalityType;
    }

    public void setAbnormalityType(List<String> abnormalityType) {
        this.abnormalityType = abnormalityType;
    }

    public List<String> getAbnormalityResponder() {
        return abnormalityResponder;
    }

    public void setAbnormalityResponder(List<String> abnormalityResponder) {
        this.abnormalityResponder = abnormalityResponder;
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
                "abnormalityType=" + abnormalityType +
                ", abnormalityResponder=" + abnormalityResponder +
                ", leaveType=" + leaveType +
                '}';
    }
}
