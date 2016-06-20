import com.maplesoft.enok.kaoqing.model.Clock;
import junit.framework.Assert;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Test {

    @org.junit.Test
    public void testClockDiff() {
        String time1 = "09:52";
        String time2 = "19:24";
        Assert.assertEquals("09:32", Clock.diff(time2, time1).toString());
    }

    @org.junit.Test
    public void testCopy() throws IOException, BiffException, WriteException {
        Workbook workbook = Workbook.getWorkbook(new File("C:\\in.xls"));
        ArrayList<Sheet> sheets = new ArrayList<Sheet>(Arrays.asList(workbook.getSheets()));
        WritableWorkbook outbook = Workbook.createWorkbook(new File("C:\\canread.xls"));
        // 移除前两个Sheet
        int index = 0;
        for (Sheet sheet : sheets) {
            WritableSheet rs = outbook.createSheet(sheet.getName(), index++);
            for (int i = 0; i < sheet.getColumns(); i++) {
                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(i, j);
                    CellFormat format = cell.getCellFormat();
                    if (format != null)
                        rs.addCell(new Label(i, j, cell.getContents(), cell.getCellFormat()));
                    else
                        rs.addCell(new Label(i, j, cell.getContents()));
                }
            }
        }
        outbook.write();
        outbook.close();
    }

}
