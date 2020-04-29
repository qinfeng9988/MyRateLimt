package com.qjfcc.myratelimit.bucket;

/**
 * @author 秦江峰
 * @AddTime 2020/4/29 15:14
 */
public interface IBucket {

    boolean get();

    int get(int tokenNum);

}
