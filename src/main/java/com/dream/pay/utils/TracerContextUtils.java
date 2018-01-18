package com.dream.pay.utils;

import java.util.UUID;

/**
 * 全局跟踪上下文工具,目的是便于日志中定位问题
 * <p>
 * Created by mengzhenbin on 16/9/2.
 */
public class TracerContextUtils {

    /**
     * 日志跟踪id,全局唯一
     **/
    static ThreadLocal<String> tracerId = new ThreadLocal<String>();

    /**
     * 生成tracerId
     */
    public static void genTracerId() {

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        tracerId.set(uuid);
    }

    /**
     * 取当前tracerId
     */
    public static String getTracerId() {

        return tracerId.get();
    }

    /**
     * 设置traceId
     */
    public static void setTracerId(String id) {
        tracerId.set(id);
    }

    /**
     * 清除
     */
    public static void clearTracerId() {
        tracerId.set("");
    }
}
