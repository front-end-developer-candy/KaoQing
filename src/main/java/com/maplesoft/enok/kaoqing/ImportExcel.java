package com.maplesoft.enok.kaoqing;


import com.maplesoft.enok.kaoqing.model.Attendance;
import com.maplesoft.enok.kaoqing.model.Clock;
import com.maplesoft.enok.kaoqing.model.Kaoqing;
import jxl.CellReferenceHelper;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.*;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.read.biff.BiffException;
import jxl.write.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ImportExcel {

    private Workbook workbook;
    private ArrayList<Sheet> sheets;
    private List<Kaoqing> kaoqings = new ArrayList<Kaoqing>();

    private WritableWorkbook outbook;

    public boolean load(File file) throws IOException, BiffException {
        if (file == null) return false;
        workbook = Workbook.getWorkbook(file);
        sheets = new ArrayList<Sheet>(Arrays.asList(workbook.getSheets()));
        // 移除前两个Sheet
        sheets.remove(0);
        sheets.remove(0);
        for (Sheet sheet : sheets) {
            dealSheet(sheet);
        }
        return true;
    }

    public String getText(Sheet sheet, String cell) {
        return sheet.getCell(cell).getContents();
    }

    public int getInt(Sheet sheet, String cell) {
        String val = getText(sheet, cell);
        return Integer.parseInt(val);
    }

    public float getFloat(Sheet sheet, String cell) {
        String val = getText(sheet, cell);
        return Float.parseFloat(val);
    }

    public void dealSheet(Sheet sheet) {
        SheetGetter getter = new SheetGetter(sheet);
        int begin = 12, end = sheet.getRows();
        DateWeek dateWeek;
        WorkTime workTime;
        // 第一个人
        Kaoqing kaoqing = new Kaoqing();
        kaoqings.add(kaoqing);
        List<Attendance> attendances = new ArrayList<Attendance>();
        kaoqing.setAttendances(attendances);
        kaoqing.setDate(getter.getText("B4"));
        kaoqing.setJobNumber(getter.getText("J4"));
        kaoqing.setName(getter.getText("J3"));
        kaoqing.setAbsenceDays(getter.getInt("A7"));
        kaoqing.setAttendanceDays(getter.getInt("E7"));
        for (int i = begin; i < end; i++) {
            dateWeek = getter.getDateWeek("A" + i);
            if (dateWeek == null) break;
            Attendance attendance = new Attendance();
            attendance.setWeek(dateWeek.getWeek());
            attendance.setDate(dateWeek.getDate());
            workTime = getter.getWorkTime("B" + i, "D" + i, "G" + i, "I" + i, "K" + i, "M" + i);
            if (workTime != null) {
                attendance.setWorkTime(workTime.getInTime());
                attendance.setOffworkTime(workTime.getOutTime());
            }
            attendances.add(attendance);
        }

        // 第二个人
        kaoqing = new Kaoqing();
        kaoqings.add(kaoqing);
        attendances = new ArrayList<Attendance>();
        kaoqing.setAttendances(attendances);
        kaoqing.setDate(getter.getText("Q4"));
        kaoqing.setJobNumber(getter.getText("Y4"));
        kaoqing.setName(getter.getText("Y3"));
        kaoqing.setAbsenceDays(getter.getInt("P7"));
        kaoqing.setAttendanceDays(getter.getInt("T7"));
        for (int i = begin; i < end; i++) {
            dateWeek = getter.getDateWeek("P" + i);
            if (dateWeek == null) break;
            Attendance attendance = new Attendance();
            attendance.setWeek(dateWeek.getWeek());
            attendance.setDate(dateWeek.getDate());
            workTime = getter.getWorkTime("Q" + i, "S" + i, "V" + i, "X" + i, "Z" + i, "AB" + i);
            if (workTime != null) {
                attendance.setWorkTime(workTime.getInTime());
                attendance.setOffworkTime(workTime.getOutTime());
            }
            attendances.add(attendance);
        }

        // 第三个人
        kaoqing = new Kaoqing();
        kaoqings.add(kaoqing);
        attendances = new ArrayList<Attendance>();
        kaoqing.setAttendances(attendances);
        kaoqing.setDate(getter.getText("AF4"));
        kaoqing.setJobNumber(getter.getText("AN4"));
        kaoqing.setName(getter.getText("AN3"));
        kaoqing.setAbsenceDays(getter.getInt("AE7"));
        kaoqing.setAttendanceDays(getter.getInt("AI7"));
        for (int i = begin; i < end; i++) {
            dateWeek = getter.getDateWeek("AE" + i);
            if (dateWeek == null) break;
            Attendance attendance = new Attendance();
            attendance.setWeek(dateWeek.getWeek());
            attendance.setDate(dateWeek.getDate());
            workTime = getter.getWorkTime("AF" + i, "AH" + i, "AK" + i, "AM" + i, "AO" + i, "AQ" + i);
            if (workTime != null) {
                attendance.setWorkTime(workTime.getInTime());
                attendance.setOffworkTime(workTime.getOutTime());
            }
            attendances.add(attendance);
        }
    }

    public void output(File file) throws IOException, BiffException, WriteException {
        if (file == null) return;
        int row = 2;
        outbook = Workbook.createWorkbook(file);
        WritableSheet sheet = outbook.createSheet("考勤统计", 0);

        // 普通带边框格式
        WritableCellFormat format = new WritableCellFormat();
        format.setBorder(Border.ALL, BorderLineStyle.THIN);

        // 带边框居中文本
        WritableCellFormat centerFormat = new WritableCellFormat();
        centerFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        centerFormat.setAlignment(Alignment.CENTRE);

        for (Kaoqing kq : kaoqings) {
            sheet.addCell(new Label(CellReferenceHelper.getColumn("A"), row, "单位：重庆优启科技有限公司", format));
            sheet.mergeCells(CellReferenceHelper.getColumn("A"), row, CellReferenceHelper.getColumn("D"), row);
            sheet.addCell(new Label(CellReferenceHelper.getColumn("E"), row, "工号：" + kq.getJobNumber(), format));
            sheet.mergeCells(CellReferenceHelper.getColumn("E"), row, CellReferenceHelper.getColumn("F"), row);
            sheet.addCell(new Label(CellReferenceHelper.getColumn("G"), row, "姓名：" + kq.getName(), format));
            sheet.mergeCells(CellReferenceHelper.getColumn("G"), row, CellReferenceHelper.getColumn("H"), row);
            sheet.addCell(new Label(CellReferenceHelper.getColumn("I"), row, "日期：" + kq.getDate(), format));
            sheet.mergeCells(CellReferenceHelper.getColumn("I"), row, CellReferenceHelper.getColumn("K"), row);

            row++;
            sheet.addCell(new Label(CellReferenceHelper.getColumn("A"), row, "工作日数：" + kq.getWorkDays()
                    + "  出勤天数：" + kq.getAttendanceDays() + "  迟到次数：0 早退次数：0  缺勤天数："
                    + kq.getAbsenceDays() + " 加班时数：  病假时数：    事假时数：", format));
            sheet.mergeCells(CellReferenceHelper.getColumn("A"), row, CellReferenceHelper.getColumn("K"), row);
            row++;
            sheet.addCell(new Label(CellReferenceHelper.getColumn("A"), row, "日薪工资：   加班工资：   其它津贴：  其他扣款：   实得工资： ", format));
            sheet.mergeCells(CellReferenceHelper.getColumn("A"), row, CellReferenceHelper.getColumn("K"), row);
            row++;
            sheet.addCell(new Label(CellReferenceHelper.getColumn("A"), row, "上下班时间", centerFormat));
            sheet.mergeCells(CellReferenceHelper.getColumn("A"), row, CellReferenceHelper.getColumn("E"), row);
            sheet.addCell(new Label(CellReferenceHelper.getColumn("F"), row, "考勤统计", centerFormat));
            sheet.mergeCells(CellReferenceHelper.getColumn("F"), row, CellReferenceHelper.getColumn("K"), row);
            row++;
            sheet.addCell(new Label(CellReferenceHelper.getColumn("A"), row, "日期", centerFormat));
            sheet.addCell(new Label(CellReferenceHelper.getColumn("B"), row, "星期", centerFormat));
            sheet.addCell(new Label(CellReferenceHelper.getColumn("C"), row, "上班", centerFormat));
            sheet.addCell(new Label(CellReferenceHelper.getColumn("D"), row, "下班", centerFormat));
            sheet.addCell(new Label(CellReferenceHelper.getColumn("E"), row, "时间差", centerFormat));
            sheet.addCell(new Label(CellReferenceHelper.getColumn("F"), row, "工作总时", centerFormat));
            sheet.addCell(new Label(CellReferenceHelper.getColumn("G"), row, "午餐时间", centerFormat));
            sheet.addCell(new Label(CellReferenceHelper.getColumn("H"), row, "实际工时", centerFormat));
            sheet.addCell(new Label(CellReferenceHelper.getColumn("I"), row, "餐补", centerFormat));
            sheet.addCell(new Label(CellReferenceHelper.getColumn("J"), row, "加班工时", centerFormat));
            sheet.addCell(new Label(CellReferenceHelper.getColumn("K"), row, "备注", centerFormat));
            for (Attendance at : kq.getAttendances()) {
                row++;
                at.calculate();
                sheet.addCell(new Label(CellReferenceHelper.getColumn("A"), row, at.getDate(), centerFormat));
                sheet.addCell(new Label(CellReferenceHelper.getColumn("B"), row, at.getWeek(), centerFormat));
                sheet.addCell(new Label(CellReferenceHelper.getColumn("C"), row, at.getWorkTime(), centerFormat));
                sheet.addCell(new Label(CellReferenceHelper.getColumn("D"), row, at.getOffworkTime(), centerFormat));
                sheet.addCell(new Label(CellReferenceHelper.getColumn("E"), row, at.getTimeDifference() + "", centerFormat));
                sheet.addCell(new Label(CellReferenceHelper.getColumn("F"), row, at.getTimeCount() + "", centerFormat));
                sheet.addCell(new Label(CellReferenceHelper.getColumn("G"), row, at.getLunchTime() + "", centerFormat));
                sheet.addCell(new Label(CellReferenceHelper.getColumn("H"), row, at.getActualWorkingHours() + "", centerFormat));
                sheet.addCell(new Label(CellReferenceHelper.getColumn("I"), row, at.getMealSupplement() + "", centerFormat));
                sheet.addCell(new Label(CellReferenceHelper.getColumn("J"), row, at.getOvertimeWork() + "", centerFormat));
                sheet.addCell(new Label(CellReferenceHelper.getColumn("K"), row, at.getMemo(), centerFormat));
            }
            row += 4;
        }
        outbook.write();
        outbook.close();
    }

    public static File chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
                if (f.getName().toLowerCase().endsWith(".xls")) {
                    return true;
                }
                return false;
            }

            @Override
            public String getDescription() {
                return ".xls";
            }
        });
        fileChooser.showOpenDialog(null);
        return fileChooser.getSelectedFile();
    }

    public static void main(String[] args) throws IOException, BiffException, WriteException {
//        File in = new File("C:\\in.xls");
//        File out = new File("C:\\out.xls");
//        ImportExcel excel = new ImportExcel();
//        excel.load(in);
//        excel.output(out);

        ImportExcel importExcel = new ImportExcel();
        if (importExcel.load(chooseFile())) {
            importExcel.output(chooseFile());
        }
        System.exit(0);
    }

}
