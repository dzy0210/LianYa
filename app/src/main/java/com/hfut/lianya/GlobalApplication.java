package com.hfut.lianya;

import android.app.Application;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalApplication extends Application {
    private static GlobalApplication globalApplication;
    public Map<String, String> map = new HashMap<>();
    public List<String> leaveType = new ArrayList<>();
    public Map<String, String> abnormalityMap = new HashMap<>();
    public List<String> abnormalityType = new ArrayList<>();
    public List<String> abnormalityResponder = new ArrayList<>();
    public Date pauseStartTime;
    public Date pauseEndTime;
    public static GlobalApplication getInstance() {
        return globalApplication;
    }
    public boolean isPause = false;
    public long pauseTime = 0;
    public long totalPauseTime = 0;
    public String packageId;
    public String[] IS_COMPLETE = {"完成剩余包", "不完成剩余包"};
    public String workstation;

    @Override
    public void onCreate() {
        super.onCreate();
        globalApplication = this;
    }
}
