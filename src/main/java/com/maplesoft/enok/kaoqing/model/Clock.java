package com.maplesoft.enok.kaoqing.model;

public class Clock {

    // 小时
    private int hour;

    // 分钟
    private int minute;

    public Clock(String time) {
        String[] s = time.split(":");
        this.hour = Integer.parseInt(s[0]);
        this.minute = Integer.parseInt(s[1]);
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public Clock() {
    }

    @Override
    public String toString() {
        String str = "";
        if (hour < 10) str += "0";
        str += hour + ":";
        if (minute < 10) str += "0";
        str += minute;
        return str;
    }

    public static Clock diff(Clock bigTime, Clock smallTime) {
        if (bigTime.minute < smallTime.minute) {
            bigTime.minute += 60;
            bigTime.hour -= 1;
        }
        Clock clock = new Clock();
        clock.hour = bigTime.hour - smallTime.hour;
        clock.minute = bigTime.minute - smallTime.minute;
        return clock;
    }

    public static Clock diff(String bigTimeStr, String smallTimeStr) {
        Clock bigTime = new Clock(bigTimeStr), smallTime = new Clock(smallTimeStr);
        if (bigTime.minute < smallTime.minute) {
            bigTime.minute += 60;
            bigTime.hour -= 1;
        }
        Clock clock = new Clock();
        clock.hour = bigTime.hour - smallTime.hour;
        clock.minute = bigTime.minute - smallTime.minute;
        return clock;
    }

}
