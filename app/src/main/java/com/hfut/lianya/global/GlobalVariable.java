package com.hfut.lianya.global;

import android.app.Application;

public class GlobalVariable extends Application {
    public static final String USERNAME = "龚德风";
    public static final String USERNO = "1398";
    public static final String PACKAGEID = "0000000001";
    public static final String[] LEAVE_TYPE = {"正常请假", "立即临时请假", "非立即临时请假"};
    public static final String[] ABNORMALITY_TYPE = {"裁片瑕疵", "辅料瑕疵", "上段品质", "做不到品质", "本层返工", "机器故障", "花样程序故障", "模板", "辅助工具", "扫描故障", "看不懂说明书", "瓶颈", "无裁片/半成品"};
    public static final String[] ABNORMALITY_RESPONDER = {"裁床", "配料员", "主管/领班", "TPE", "特定维修员", "TPE", "花样程序员", "模板员","机修/领班", "系统维护员", "TPE", "裁床", "系统自动计算"};
    public static final String[] IS_COMPLETE = {"完成当前包", "完成剩余包", "不完成剩余包"};
    public static final String[] WORKER_NAME = {"龚德凤", "邓海菊", "黎燕雄"};
    public static final String[] AREA = {"车缝05组", "车缝07组"};
}
