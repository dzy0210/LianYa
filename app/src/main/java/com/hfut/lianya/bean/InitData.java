package com.hfut.lianya.bean;

import java.util.List;
public class InitData {
    List<String> abnormalityType;
    List<String> abnormalityResponder;
    List<String> leaveType;
    String workstation;

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

    public String getWorkstation() {
        return workstation;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }
}
