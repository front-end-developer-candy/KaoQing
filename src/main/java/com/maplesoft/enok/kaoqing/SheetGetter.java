package com.maplesoft.enok.kaoqing;

import jxl.Sheet;
import jxl.Workbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SheetGetter {

    private Sheet sheet;

    public SheetGetter(Sheet sheet) {
        this.sheet = sheet;
    }

    public int getInt(String cell) {
        String text = getText(cell);
        if (text.trim().length() == 0) return 0;
        return Integer.parseInt(text);
    }

    public String getText(String cell) {
        return sheet.getCell(cell).getContents();
    }

    public DateWeek getDateWeek(String cell) {
        String val = getText(cell);
        if (val.trim().length() == 0) {
            return null;
        }
        String[] strs = val.split(" ");
        DateWeek dateWeek = new DateWeek();
        dateWeek.setDate(strs[0].trim());
        dateWeek.setWeek(strs[1].trim());
        return dateWeek;
    }

    public WorkTime getWorkTime(String... cells) {
        List<String> times = new ArrayList<String>();
        for (String cell : cells) {
            String time = getText(cell).trim();
            if (time.length() > 0 && !time.equals("旷工")) {
                times.add(getText(cell));
            }
        }
        if (times.size() == 0) {
            return null;
        }
        WorkTime workTime = new WorkTime();
        workTime.setInTime(times.get(0));
        if (times.size() > 1) {
            workTime.setOutTime(times.get(times.size() - 1));
        } else {
            workTime.setOutTime("");
        }
        return workTime;
    }

    public float getFloat(String cell) {
        return Float.parseFloat(getText(cell));
    }

}

class DateWeek {
    private String week;
    private String date;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

class WorkTime {
    private String inTime;
    private String outTime;

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
        if (this.outTime != null && this.outTime.length() > 0) {
            String[] s = this.outTime.split(":");
            if (Integer.parseInt(s[0]) < 6) {
                System.out.println("下班时间小于6点钟" + this.outTime);
                this.outTime = (Integer.parseInt(s[0]) + 24) + ":" + s[1];
            }
        }
    }
}