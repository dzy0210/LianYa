package com.hfut.lianya.net.base;

public class NetConstant {
    // base地址
//    public static final String BASE_URL = "http://192.168.1.110:9090";
//    public static final String BASE_URL = "http://114.213.213.67:9090";
    public static final String BASE_URL = "http://101.34.129.170:9090";
    /**
     * 用户接口
     */
    public static final String USER = "/user";
    public static final String TASK = "/task";
    // 注册
    public static final String REGISTER = USER + "/register";
    // 登录
    public static final String LOGIN = USER + "/login";
    public static final String MODIFY_PASSWORD = USER + "/modify_password";
    public static final String CLOCK_IN = USER + "/clock_in";
    public static final String KNOCK_OFF = USER + "/knock_off";
    //版本号
    public static final String VERSION = "/app/version";
    public static final String DOING = TASK + "/doing";

    public static final String ABNORMALITY = "/abnormality";
    public static final String SUBMIT_ABNORMALITY = BASE_URL + ABNORMALITY + "/submit";
    public static final String EDIT_ABNORMALITY = BASE_URL + ABNORMALITY + "/edit";
    public static final String CANCEL_ABNORMALITY = BASE_URL + ABNORMALITY + "/cancel";
    public static final String DEAL_ABNORMALITY = BASE_URL + ABNORMALITY + "/deal";
    public static final String ACCEPT_ABNORMALITY = BASE_URL + ABNORMALITY + "/accept";
    public static final String REJECT_ABNORMALITY = BASE_URL + ABNORMALITY + "/reject";
    public static final String COMPLETE_ABNORMALITY = BASE_URL + ABNORMALITY + "/complete";
    public static final String GET_DEALING_ABNORMALITY = BASE_URL + ABNORMALITY + "/dealing";
    public static final String GET_HISTORY_ABNORMALITY = BASE_URL+ ABNORMALITY + "/history";
    public static final String GET_DEALING_ABNORMALITY_BY_DEALER = BASE_URL + ABNORMALITY + "/dealing_dealer";
    public static final String GET_HISTORY_ABNORMALITY_BY_DEALER = BASE_URL+ ABNORMALITY + "/history_dealer";

    public static final String DASHBOARD = "/dashboard";
    public static final String GET_HOURLY_WAGE = BASE_URL + DASHBOARD + "/hourly";;
    public static final String GET_PIECE_RATE_WAGE= BASE_URL + DASHBOARD + "/piece";
    public static final String GET_TASK = BASE_URL + DASHBOARD + "/task";
    public static final String SCAN = BASE_URL + DASHBOARD + "/scan";
    public static final String START = BASE_URL + DASHBOARD + "/start";
    public static final String GET_WORKER_DOING_TASK = BASE_URL + DASHBOARD + "/doing";
    public static final String GET_PACKAGE_INFO = BASE_URL + DASHBOARD + "/get_package_info";
    public static final String GET_MODEL_INFO = BASE_URL + DASHBOARD + "/get_model_info";
    public static final String GET_DELIVERING = BASE_URL + DASHBOARD + "/delivering";
    public static final String GET_FOR_DELIVER = BASE_URL + DASHBOARD + "/for_deliver";
    public static final String TAKE_TASK = BASE_URL +DASHBOARD + "/take_task";
    public static final String TAKE_MODEL = BASE_URL +DASHBOARD + "/take_model";
    public static final String DELIVERED = BASE_URL + DASHBOARD + "/delivered";
    public static final String DELIVERED_MODEL = BASE_URL + DASHBOARD + "/delivered_model";
    public static final String BED_DELIVERED = BASE_URL  + "/bed/bed_delivered";
    public static final String DONE_TASK = BASE_URL + DASHBOARD + "/done_task";
    public static final String WORKER_DONE_TASK = BASE_URL + DASHBOARD + "/worker_done_task";
    public static final String DOING_TASK = BASE_URL + DASHBOARD + "/doing_task";
    public static final String TO_BE_DONE_TASK = BASE_URL + DASHBOARD + "/to_be_done_task";
    public static final String HISTORY_TASK = BASE_URL + DASHBOARD + "/history_task";
    public static final String WORKER_HISTORY_TASK = BASE_URL + DASHBOARD + "/worker_history_task";

    public static final String NOTIFICATION = "/notification";
    public static final String NOTIFICATION_NUM = BASE_URL + NOTIFICATION + "/num";
    public static final String GET_NOTIFICATION = BASE_URL + NOTIFICATION + "/get";
    public static final String READ_NOTIFICATION = BASE_URL + NOTIFICATION + "/read";

    public static final String LEAVE = "/leave";
    public static final String NEW_LEAVE = BASE_URL + LEAVE + "/new";
    public static final String EDIT_LEAVE = BASE_URL +LEAVE + "/edit";
    public static final String CANCEL_LEAVE = BASE_URL + LEAVE + "/cancel";
    public static final String DEAL_LEAVE = BASE_URL + LEAVE + "/deal";
    public static final String GET_HISTORY_LEAVES = BASE_URL + LEAVE + "/history";
    public static final String GET_DEALING_LEAVES = BASE_URL + LEAVE + "/dealing";
    public static final String GET_DEALING_LEAVES_BY_DEALER = BASE_URL + LEAVE + "/dealer";
    public static final String GET_HISTORY_LEAVES_BY_DEALER = BASE_URL + LEAVE + "/history_dealer";

    public static final String COMMON = "/common";
    public static final String GET_VERSION = BASE_URL + COMMON + "/version";
    public static final String GET_INIT_DATA = BASE_URL + COMMON + "/init";
    public static final String GET_LACK = BASE_URL + COMMON + "/lack";

    public static final String EFFICIENCY = "/efficiency";
    public static final String SINGLE = BASE_URL + "/singnaleff/all";
    public static final String SUMMARIZED = BASE_URL + EFFICIENCY + "/all";

    public static final String POST_TEST = BASE_URL + DASHBOARD + "/post_test";
}
