package com.qjfcc.myratelimit.bucket;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * @author 秦江峰
 * 漏桶的简单代码实现
 * @AddTime 2020/4/29 15:24
 */
public class LeakyBucket implements IBucket {

    private volatile long timeLimit;  //毫秒数

    private volatile long datetime;

    public LeakyBucket(long timeLimit, TimeUnit timeUnit) {
        this.timeLimit = timeUnit.toMillis(timeLimit);
        Calendar calendar = Calendar.getInstance();
        this.datetime = calendar.getTimeInMillis();
    }

    @Override
    public boolean get() {
        return get(1) == 1;
    }

    @Override
    public synchronized int get(int tokenNum) {
        Calendar calendar = Calendar.getInstance();
        if (datetime + timeLimit <= calendar.getTimeInMillis()) {
            datetime = calendar.getTimeInMillis();
            System.out.println("consumer ok[" + Thread.currentThread().getId() + "]");
            return 1;
        } //else System.out.println("consumer fail[" + Thread.currentThread().getId() + "]");
        return 0;
    }
}
