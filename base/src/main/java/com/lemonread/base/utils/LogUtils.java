package com.lemonread.base.utils;

import android.util.Log;

/**
 * Created by xuyao on 3/17/17.
 */

public class LogUtils {


    private static boolean isDebug = true ;//BuildConfig.DEBUG;

    private static boolean SHOW_LINE_NUMBER_IN_LOG = isDebug;// 是否在log中显示行号
    private static final String TAG = "log";

    public static void setDebug(boolean b) {
        isDebug = b;
        SHOW_LINE_NUMBER_IN_LOG = b;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void log(String TAG, String msg) {
        if (isDebug) {

            LOG(TAG, msg, Log.DEBUG, false);

        }


    }

    public static void log(String msg) {
        if (isDebug) {

            LOG(TAG, msg, Log.DEBUG, false);

        }


    }

    /**
     * 打印Log.w信息
     *
     * @param aPrintLogStr 打印信息字符串
     */
    public static void w(String aPrintLogStr) {
        LOG(TAG, aPrintLogStr, Log.WARN, false);
    }

    public static void w(String tag, String aPrintLogStr) {
        LOG(tag, aPrintLogStr, Log.WARN, false);
    }

    public static void i(String tag, String aPrintLogStr) {
        LOG(tag, aPrintLogStr, Log.INFO, false);
    }


    public static void i(String aPrintLogStr) {
        LOG(TAG, aPrintLogStr, Log.INFO, false);
    }

    public static void callerI(String aPrintLogStr) {
        LOG(TAG, aPrintLogStr, Log.INFO, true);
    }

    public static void d(String tag, String aPrintLogStr) {
        LOG(tag, aPrintLogStr, Log.DEBUG, false);
    }

    public static void d(String aPrintLogStr) {
        LOG(TAG, aPrintLogStr, Log.DEBUG, false);
    }

    public static void e(String tag, String aPrintLogStr) {
        LOG(tag, aPrintLogStr, Log.ERROR, false);
    }

    public static void e(String aPrintLogStr) {
        LOG(TAG, aPrintLogStr, Log.ERROR, false);
    }

    /**
     * @param tag        log的tag
     * @param content    log的内容
     * @param logType    log的类型,如Log.INFO,Log.DEBUG等
     * @param showCaller 是否显示调用者的方法名和行号等
     */
    private static void LOG(String tag, String content, int logType, boolean showCaller) {
        if (isDebug) {
            if (SHOW_LINE_NUMBER_IN_LOG) {
                Throwable throwable = new Throwable();

                int methodDepth = 2;//LogUtils.LOGI的depth是0, 该函数的depth是1, 调用者的depth是2,调用者的调用者是3
                StackTraceElement[] stackTraceElements = throwable.getStackTrace();

                if (methodDepth < stackTraceElements.length) {
                    StackTraceElement element = stackTraceElements[methodDepth];

                    if (showCaller) {
                        if ((methodDepth + 1) < stackTraceElements.length) {
                            StackTraceElement elementCaller = stackTraceElements[methodDepth + 1];

                            if (elementCaller != null && elementCaller.getFileName() != null) {
                                int ps1 = elementCaller.getFileName().lastIndexOf(".");
                                int ps2 = element.getFileName().lastIndexOf(".");
                                if (ps1 >= 0 && ps2 >= 0) {
                                    String callerClassName = elementCaller.getFileName().substring(0, ps1);
                                    String className = element.getFileName().substring(0, ps2);
                                    String loginfo = String.format("[%s.%s() (%s:%d)]", callerClassName, elementCaller.getMethodName(), elementCaller.getFileName(), elementCaller.getLineNumber());
                                    loginfo = loginfo + "\n";
                                    loginfo = loginfo + "    " + String.format("[%s.%s() (%s:%d)]%s", className, element.getMethodName(), element.getFileName(), element.getLineNumber(), content);

                                    content = loginfo;
                                } else {
                                    String callerClassName = elementCaller.getFileName();
                                    String className = element.getFileName();
                                    String loginfo = String.format("[%s.%s() (%s:%d)]", callerClassName, elementCaller.getMethodName(), elementCaller.getFileName(), elementCaller.getLineNumber());
                                    loginfo = loginfo + "\n";
                                    loginfo = loginfo + "    " + String.format("[%s.%s() (%s:%d)]%s", className, element.getMethodName(), element.getFileName(), element.getLineNumber(), content);

                                    content = loginfo;
                                }


                            }

                        }

                    } else {
                        if (element != null && element.getFileName() != null) {
                            int ps = element.getFileName().lastIndexOf(".");
                            if (ps >= 0) {
                                String className = element.getFileName().substring(0, ps);
                                String loginfo = String.format("[%s.%s() (%s:%d)]%s", className, element.getMethodName(), element.getFileName(), element.getLineNumber(), content);
                                content = loginfo;
                            } else {
                                String className = element.getFileName();
                                String loginfo = String.format("[%s.%s() (%s:%d)]%s", className, element.getMethodName(), element.getFileName(), element.getLineNumber(), content);
                                content = loginfo;
                            }
                        }
                    }

                }
            }

            switch (logType) {
                case Log.INFO:
                    Log.i(tag, content);
                    break;
                case Log.DEBUG:
                    Log.d(tag, content);
                    break;
                case Log.ERROR:
                    Log.e(tag, content);
                    break;
                case Log.WARN:
                    Log.w(tag, content);
                    break;

                default:
                    break;
            }
        }

    }
}
