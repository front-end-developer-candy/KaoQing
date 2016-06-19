package com.maplesoft.enok.kaoqing.model;

/**
 * 出勤记录
 */
public class Attendance {

    // 日期
    private String date;

    // 星期
    private String week;

    // 上班时间
    private String workTime;

    // 下班时间
    private String offworkTime;

    // 时间差
    private String timeDifference;

    // 工作总时
    private float timeCount;

    // 午餐时间
    private float lunchTime;

    // 实际工作时间
    private float actualWorkingHours;

    // 餐补
    private float mealSupplement;

    // 加班工时
    private float overtimeWork;

    // 备注
    private String memo;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getOffworkTime() {
        return offworkTime;
    }

    public void setOffworkTime(String offworkTime) {
        this.offworkTime = offworkTime;
    }

    public String getTimeDifference() {
        if (workTime != null && offworkTime != null && workTime.indexOf(":") != -1 && offworkTime.indexOf(":") != -1) {
            return Clock.diff(offworkTime, workTime).toString();
        }
        return "";
    }

    public void setTimeDifference(String timeDifference) {
        this.timeDifference = timeDifference;
    }

    public float getTimeCount() {
        String timeStr = getTimeDifference();
        if (timeStr == null || timeStr.indexOf(":") == -1) return 0;
        Clock clock = new Clock(timeStr);
        float time = clock.getHour();
        if (clock.getMinute() >= 50) {
            time += 1;
        } else if (clock.getMinute() >= 20) {
            time += 0.5;
        }
        return time;
    }

    public void setTimeCount(float timeCount) {
        this.timeCount = timeCount;
    }

    public float getLunchTime() {
        return lunchTime;
    }

    public void setLunchTime(float lunchTime) {
        this.lunchTime = lunchTime;
    }

    public float getActualWorkingHours() {
        return actualWorkingHours;
    }

    public void setActualWorkingHours(float actualWorkingHours) {
        this.actualWorkingHours = actualWorkingHours;
    }

    public float getMealSupplement() {
        return mealSupplement;
    }

    public void setMealSupplement(float mealSupplement) {
        this.mealSupplement = mealSupplement;
    }

    public float getOvertimeWork() {
        return overtimeWork;
    }

    public void setOvertimeWork(float overtimeWork) {
        this.overtimeWork = overtimeWork;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
