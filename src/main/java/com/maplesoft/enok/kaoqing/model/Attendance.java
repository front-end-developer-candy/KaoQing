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
        return timeDifference == null ? "" : timeDifference;
    }

    public void setTimeDifference(String timeDifference) {
        this.timeDifference = timeDifference;
    }

    public float getTimeCount() {
        return timeCount;
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
        //总工时-午餐时间< 5    餐补为0
        //总工时-午餐时间>= 5  and  总工时-午餐时间<10    餐补为 11
        //总工时-午餐时间>= 10  and  总工时-午餐时间<15   餐补为 22
        timeCount = getTimeCount();
        lunchTime = getLunchTime();
        if (timeCount - lunchTime >= 5 && timeCount - lunchTime < 10) return 11;
        if (timeCount - lunchTime >= 10 && timeCount - lunchTime < 15) return 22;
        return 0;
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

    // 计算
    public void calculate() {
        // 计算时间差
        if (workTime != null && offworkTime != null && workTime.indexOf(":") != -1 && offworkTime.indexOf(":") != -1) {
            timeDifference = Clock.diff(offworkTime, workTime).toString();
        }

        // 计算工作总时
        if (timeDifference != null && timeDifference.indexOf(":") > -1) {
            Clock clock = new Clock(timeDifference);
            timeCount = clock.getHour();
            if (clock.getMinute() >= 50) {
                timeCount += 1;
            } else if (clock.getMinute() >= 20) {
                timeCount += 0.5;
            }
        }

        // 计算用餐时间
        if (null != timeDifference) {
            // 在12:20点前打卡离开或在12:30点后打卡上班的，不扣减午餐时间。
            Clock workClock = new Clock(getWorkTime());
            Clock offworkClock = new Clock(getOffworkTime());
            int overtime = 12 * 60 + 50;    // 12:50
            int afterovertime = 20 * 60;    // 20:00

            // 夏季
            // float wcsj = 1.5f; // 午餐时间

            // 冬季
            float wcsj = 1.0f; // 午餐时间

            // 上班时间 < 12:30 and 总工时 > 午餐时间 ，用餐时间加上午餐时间
            if (workClock.getHour() * 60 + workClock.getMinute() < overtime && timeCount > wcsj && offworkClock.getHour() * 60 + offworkClock.getMinute() > overtime) {
                lunchTime += wcsj;
            }

            // 下班时间 >= 20:00 ，用餐时间加上晚餐时间
            if (offworkClock.getHour() * 60 + offworkClock.getMinute() >= afterovertime) {
                lunchTime += 0.5f;
            }

        }

        // 计算餐补
        //总工时-午餐时间< 5    餐补为0
        //总工时-午餐时间>= 5  and  总工时-午餐时间<10    餐补为 11
        //总工时-午餐时间>= 10  and  总工时-午餐时间<15   餐补为 22
        if (timeCount - lunchTime >= 5 && timeCount - lunchTime < 10) mealSupplement = 11;
        if (timeCount - lunchTime >= 10 && timeCount - lunchTime < 15) mealSupplement = 22;

        // 计算实际工时
        // 总工时-午餐时间<=8    为总工时-午餐时间
        // 总工时-午餐时间>8    为8
        if (timeCount - lunchTime <= 8) actualWorkingHours = timeCount - lunchTime;
        else if (timeCount - lunchTime > 8) actualWorkingHours = 8;

        // 计算加班工时
        // 加班工时：如果实际工时>8,那么加班工时=总工时-午餐时间-8
        if (timeCount - lunchTime > 8) overtimeWork = timeCount - lunchTime - 8;

    }
}
