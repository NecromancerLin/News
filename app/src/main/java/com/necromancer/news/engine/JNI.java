package com.necromancer.news.engine;

/**
 * Created by HP-PC on 2018/4/13.
 */

public class JNI {
    static {
        System.loadLibrary("native-lib");
    }
    /**获取AppKey*/
    public static native String getAppKey();
}
