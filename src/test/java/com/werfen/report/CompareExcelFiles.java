package com.werfen.report;


import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CompareExcelFiles {

    public void verifyIfExcelFilesHaveSameNumberAndNameOfSheets(Workbook workbook1, Workbook workbook2) {

        int sheetsInWorkbook1 = workbook1.getNumberOfSheets();
        int sheetsInWorkbook2 = workbook2.getNumberOfSheets();
        assertEquals(sheetsInWorkbook1, sheetsInWorkbook2,
                "Excel work books have different number of sheets. \n "
                        + "Sheets in work book 1 : " + sheetsInWorkbook1 + "\n "
                        + "Number of sheets in work book 2 : " + sheetsInWorkbook2);
        List<String> sheetsNameOfWb1 = new ArrayList<>();
        List<String> sheetsNameOfWb2 = new ArrayList<>();

        for (int i = 0; i < sheetsInWorkbook1; i++) {
            sheetsNameOfWb1.add(workbook1.getSheetName(i));
            sheetsNameOfWb2.add(workbook2.getSheetName(i));
        }

        Collections.sort(sheetsNameOfWb1);
        Collections.sort(sheetsNameOfWb2);
        assertEquals(sheetsNameOfWb1, sheetsNameOfWb2, "Provided excel work books have different name of sheets.");
    }

    public void verifySheetsInExcelFilesHaveSameRowsAndColumns(Workbook workbook1, Workbook workbook2) {
        int sheetCounts = workbook1.getNumberOfSheets();

        for (int i = 0; i < sheetCounts; i++) {
            Sheet s1 = workbook1.getSheetAt(i);
            Sheet s2 = workbook2.getSheetAt(i);
            int rowsInSheet1 = s1.getPhysicalNumberOfRows();
            int rowsInSheet2 = s2.getPhysicalNumberOfRows();
            assertEquals(rowsInSheet1, rowsInSheet2, "Sheets have different count of rows..");

            Iterator<Row> rowInSheet1 = s1.rowIterator();
            Iterator<Row> rowInSheet2 = s2.rowIterator();
            while (rowInSheet1.hasNext()) {
                int cellCounts1 = rowInSheet1.next().getPhysicalNumberOfCells();
                int cellCounts2 = rowInSheet2.next().getPhysicalNumberOfCells();
                assertEquals(cellCounts1, cellCounts2, "Sheets have different count of columns..");
            }
        }
    }

    public void verifyDataInExcelBookAllSheets(Workbook workbook1, Workbook workbook2) {
        int sheetCounts = workbook1.getNumberOfSheets();

        for (int i = 0; i < sheetCounts; i++) {
            Sheet s1 = workbook1.getSheetAt(i);
            Sheet s2 = workbook2.getSheetAt(i);
            int rowCounts = s1.getPhysicalNumberOfRows();

            for (int j = 0; j < rowCounts; j++) {
                if (nonNull(s1.getRow(j)) && nonNull(s2.getRow(j))) {
                    int cellCounts = s1.getRow(j).getPhysicalNumberOfCells();
                    for (int k = 0; k < cellCounts; k++) {
                        Cell c1 = s1.getRow(j).getCell(k);
                        Cell c2 = s2.getRow(j).getCell(k);
                        if (nonNull(c1) && nonNull(c2)) {
                            if (c1.getCellType().equals(c2.getCellType())) {
                                if (c1.getCellType() == CellType.STRING) {
                                    String v1 = c1.getStringCellValue();
                                    String v2 = c2.getStringCellValue();
                                    assertEquals(v1, v2, "Cell values are different.....");
                                }
                                if (c1.getCellType() == CellType.NUMERIC) {
                                    if (DateUtil.isCellDateFormatted(c1) | DateUtil.isCellDateFormatted(c2)) {
                                        DataFormatter df = new DataFormatter();
                                        String v1 = df.formatCellValue(c1);
                                        String v2 = df.formatCellValue(c2);
                                        assertEquals(v1, v2, "Cell values are different.....");
                                    } else {
                                        double v1 = c1.getNumericCellValue();
                                        double v2 = c2.getNumericCellValue();
                                        assertEquals(v1, v2, "Cell values are different.....");
                                    }
                                }
                                if (c1.getCellType() == CellType.BOOLEAN) {
                                    boolean v1 = c1.getBooleanCellValue();
                                    boolean v2 = c2.getBooleanCellValue();
                                    assertEquals(v1, v2, "Cell values are different.....");
                                }
                            } else {
                                fail("Non matching cell type.");
                            }
                        }
                    }
                }
            }
        }
    }
}
