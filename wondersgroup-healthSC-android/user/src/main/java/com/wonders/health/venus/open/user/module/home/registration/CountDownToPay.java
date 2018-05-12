package com.wonders.health.venus.open.user.module.home.registration;
/*
 * Created by sunning on 2016/11/15.
 */

import android.os.CountDownTimer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class CountDownToPay {

    private static final int COUNT_DOWN_INTERVAL = 1000;
    private static final int MINUTE = 60;
    private static final int MILLIS_IN_FUTURE = 15 * MINUTE * COUNT_DOWN_INTERVAL;

    private static CountDownToPay instance;

    private CountDownTimer timer;

    private CountDownToPay() {
    }

    public static CountDownToPay getInstance() {
        if (instance == null) {
            synchronized (CountDownToPay.class) {
                if (instance == null) {
                    instance = new CountDownToPay();
                }
            }
        }
        return instance;
    }

    public void start(int timeRemaining, final CountdownCallBack callBack) {
        if (timeRemaining == 0) {
            timeRemaining = MILLIS_IN_FUTURE;
        } else {
            if (timeRemaining <= 0) {
                callBack.onFinish();
                return;
            }
            timeRemaining *= COUNT_DOWN_INTERVAL;
        }
        timer = new CountDownTimer(timeRemaining, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                int UnFinishCountTime = (int) (millisUntilFinished / COUNT_DOWN_INTERVAL);
                int countDownMinute = UnFinishCountTime / MINUTE;
                int countDownMillis = UnFinishCountTime % MINUTE;
                callBack.getTime(getTwoDigit(countDownMinute), getTwoDigit(countDownMillis));
            }

            @Override
            public void onFinish() {
                callBack.onFinish();
            }
        };
        timer.start();
    }

    private long parseFormatTime(String time) throws ParseException {
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date parse = date.parse(time);
        return System.currentTimeMillis() - parse.getTime();
    }

    CountDownTimer getTimer() {
        return timer;
    }

    private String getTwoDigit(int time) {
        if (time < 10) {
            return "0" + time;
        } else {
            return String.valueOf(time);
        }
    }

    interface CountdownCallBack {

        void getTime(String min, String sec);

        void onFinish();
    }
}
