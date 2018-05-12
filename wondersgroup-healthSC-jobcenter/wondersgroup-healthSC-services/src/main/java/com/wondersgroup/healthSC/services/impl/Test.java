package com.wondersgroup.healthSC.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhuchunliu on 2016/11/4.
 */
public class Test {

    public static void  main(String[] args){

        System.err.println((Runtime.getRuntime().availableProcessors()));
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for(int i=0;i<100;i++){
            executorService.submit(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.err.println(Thread.currentThread().getName() + "  ");

                }
            });
        }
    }
}
