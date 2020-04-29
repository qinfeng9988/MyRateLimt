package com.qjfcc.myratelimit.bucket;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 秦江峰
 * 令牌桶的简单代码实现
 * @AddTime 2020/4/29 15:14
 */
public class TokenBucket implements IBucket {


    private Object object = new Object();

    private int rate;
    private volatile int token;
    private int max;

    private volatile Date lastUpdate;
    private long secondsWindow;

    private TimeUnit timeUnit;

    public TokenBucket(int rate, int max, int secondsWindow, TimeUnit timeUnit) {
        this.rate = this.token = rate;
        this.max = max;
        this.lastUpdate = Calendar.getInstance().getTime();

        this.secondsWindow = timeUnit.toSeconds(secondsWindow);
    }

    private void add() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastUpdate);
        calendar.add(Calendar.SECOND, Integer.valueOf((int) secondsWindow));
        Date now = Calendar.getInstance().getTime();
        if (calendar.getTime().before(now)) {
            this.lastUpdate = now;
            this.token = Math.min(max, this.token + this.rate);
//            System.out.println("producer");
        } //else System.out.println("buket is full");
    }

    @Override
    public boolean get() {
        return get(1) == 1;
    }

    public synchronized int get(int num) {
        this.add();
        if (token - num >= 0) {
            System.out.println("consumer ok[" + num + "]");
            token = token - num;
            return 1;
        }
//        System.out.println("consumer fail");
        return 0;
    }
}
