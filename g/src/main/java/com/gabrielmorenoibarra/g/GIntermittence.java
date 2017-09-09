package com.gabrielmorenoibarra.g;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Utility to do an intermittence in a view.
 * Created by Gabriel Moreno on 2017-09-09.
 */
public class GIntermittence {

    private CountDownTimer countDownTimerIntermittence;
    private TextView tv;
    private String on;
    private String off;
    private long millisInFuture;

    public GIntermittence(TextView tv, String on, String off, long millisInFuture) {
        this.tv = tv;
        this.on = on;
        this.off = off;
        this.millisInFuture = millisInFuture;
    }

    public void start() {
        tv.setText(off);
        countDownTimerIntermittence = new CountDownTimer(millisInFuture, 500) {
            public void onTick(long millisUntilFinished) {
                if (tv.getAlpha() == 0.5f) {
                    tv.setAlpha(1f);
                } else {
                    tv.setAlpha(0.5f);
                }
            }
            public void onFinish() {
                tv.setAlpha(1f);
            }
        }.start();
    }

    public void stop() {
        tv.setText(on);
        if (countDownTimerIntermittence != null) countDownTimerIntermittence.cancel();
    }
}