package com.example.domain;

import org.apache.shiro.SecurityUtils;

public class Constant {
    //自动去除表前缀
    public static final String AUTO_REOMVE_PRE = "true";
    //终止计划任务
    public static final String STATUS_RUNNING_STOP = "stop";
    //新增计划任务
    public static final String STATUS_RUNNING_START = "start";
    //暂停计划任务
    public static final String STATUS_RUNNING_PAUSED = "paused";
    //恢复计划任务
    public static final String STATUS_RUNNING_EXECUTE= "execute";
    //开启计划任务
    public static final String STATUS_RUNNING_PLAN = "plan";
    //取消计划任务
    public static final String STATUS_RUNNING_UNPLAN= "unPlan";
    //通知公告阅读状态-未读
    public static final String OA_NOTIFY_READ_NO = "0";
    //通知公告阅读状态-已读
    public static final int OA_NOTIFY_READ_YES = 1;
    //部门根节点id
    public static final Long DEPT_ROOT_ID = 0l;
    //缓存方式
    public static final String CACHE_TYPE_REDIS ="redis";

    public static final String LOG_ERROR = "error";
    
    public static final String CURRENT_USER = "user";
    
	public static final Object SESSION_FORCE_LOGOUT_KEY = "session_force_logout";

	//系统用户id
	public static final Long SYS_USER_ID = 0L;

    public static final String DES_SECURITY_KEY = "purchase_3721_!@#_567";
    
    public static final String CACHE_TYPE="redis";

    /**
     * 默认第一页
     */
    public static final Integer PAGE = 1;

    /**
     * 默认每页显示的行数
     */
    public static final Integer ROWS = 10;

    /**
     * 获取当前用户businessIds
     * @return
     */
    public static String getBusinessIds() {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        String businessIds = userInfo.getBusinessIds();
        if (businessIds.equals("ALL")) {
            return "";
        } else if (businessIds == null || businessIds.trim().equals("")) {
            return "-1";
        }
        return businessIds;
    }



}
