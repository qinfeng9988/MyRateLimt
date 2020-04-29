package com.qjfcc.myratelimit;

import com.qjfcc.myratelimit.bucket.IBucket;
import com.qjfcc.myratelimit.bucket.LeakyBucket;
import com.qjfcc.myratelimit.bucket.TokenBucket;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class RateLimitApplication {

    public static void main(String[] args) {
        testTokenBucket();
//        SpringApplication.run(RateLimitApplication.class, args);
    }


    public static void testTokenBucket(){
        TokenBucket bucket1 = new TokenBucket(10, 20, 5, TimeUnit.SECONDS);
        executeThread(bucket1);
    }


    public static void testleakyBucket(){
        IBucket bucket = new LeakyBucket(1,TimeUnit.SECONDS);
        executeThread(bucket);
    }
    private  static void executeThread(IBucket bucket){
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Random random = new Random();
                    bucket.get(random.nextInt(4) + 1);
                }
            }).start();
        }
    }

}
