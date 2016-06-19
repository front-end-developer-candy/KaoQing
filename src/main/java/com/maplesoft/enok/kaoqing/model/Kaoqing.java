package com.maplesoft.enok.kaoqing.model;

import java.util.List;

public class Kaoqing {

    // 姓名
    private String name;

    // 工号
    private String jobNumber;

    // 统计日期
    private String date;

    // 出勤天数
    private int attendanceDays;

    // 工作天数
    private int workDays;

    // 迟到天数
    private int lateDays;

    // 早退次数
    private int earlyTimes;

    // 缺勤天数
    private int absenceDays;

    // 加班时数
    private int overtimeHours;

    // 病假时数
    private int sickLeave;

    // 事假时数
    private int leaveNumber;

    // 考勤记录
    private List<Attendance> Attendances;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAttendanceDays() {
        return attendanceDays;
    }

    public void setAttendanceDays(int attendanceDays) {
        this.attendanceDays = attendanceDays;
    }

    public int getWorkDays() {
        return workDays;
    }

    public void setWorkDays(int workDays) {
        this.workDays = workDays;
    }

    public int getLateDays() {
        return lateDays;
    }

    public void setLateDays(int lateDays) {
        this.lateDays = lateDays;
    }

    public int getEarlyTimes() {
        return earlyTimes;
    }

    public void setEarlyTimes(int earlyTimes) {
        this.earlyTimes = earlyTimes;
    }

    public int getAbsenceDays() {
        return absenceDays;
    }

    public void setAbsenceDays(int absenceDays) {
        this.absenceDays = absenceDays;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(int overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public int getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(int sickLeave) {
        this.sickLeave = sickLeave;
    }

    public int getLeaveNumber() {
        return leaveNumber;
    }

    public void setLeaveNumber(int leaveNumber) {
        this.leaveNumber = leaveNumber;
    }

    public List<Attendance> getAttendances() {
        return Attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        Attendances = attendances;
    }
}
