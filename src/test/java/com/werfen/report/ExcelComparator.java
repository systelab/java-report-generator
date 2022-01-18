package com.werfen.report;


import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ExcelComparator {

    public void assertWorkbookEquals(Workbook expected, Workbook actual) {
        this.assertIfExcelFilesHaveSameNumberAndNameOfSheets(expected, actual);
        this.assertSheetsInExcelFilesHaveSameRowsAndColumns(expected, actual);
        this.assertDataInExcelBookAllSheets(expected, actual);
    }

    public void assertIfExcelFilesHaveSameNumberAndNameOfSheets(Workbook expected, Workbook actual) {

        int expectedSheetCount = expected.getNumberOfSheets();
        int actualSheetCount = actual.getNumberOfSheets();
        assertEquals(expectedSheetCount, actualSheetCount,
                "Excel work books have different number of sheets. \n "
                        + "Sheets in expected work book: " + expectedSheetCount + "\n "
                        + "Sheets in actual work book: " + actualSheetCount);
        List<String> expectedSheetNames = new ArrayList<>();
        List<String> actualSheetNames = new ArrayList<>();

        for (int i = 0; i < expectedSheetCount; i++) {
            expectedSheetNames.add(expected.getSheetName(i));
            actualSheetNames.add(actual.getSheetName(i));
        }

        assertEquals(expectedSheetNames, actualSheetNames, "Provided excel work books have different name of sheets.");
    }

    public void assertSheetsInExcelFilesHaveSameRowsAndColumns(Workbook expected, Workbook actual) {
        int expectedSheetCount = expected.getNumberOfSheets();

        for (int i = 0; i < expectedSheetCount; i++) {
            Sheet expectedSheet = expected.getSheetAt(i);
            Sheet actualSheet = actual.getSheetAt(i);
            int expectedRowCount = expectedSheet.getPhysicalNumberOfRows();
            int actualRowCount = actualSheet.getPhysicalNumberOfRows();
            assertEquals(expectedRowCount, actualRowCount, "Sheet " + actualSheet.getSheetName() + " have different count of rows..");

            Iterator<Row> expectedSheetRowIterator = expectedSheet.rowIterator();
            Iterator<Row> actualSheetRowIterator = actualSheet.rowIterator();
            while (expectedSheetRowIterator.hasNext()) {
                int expectedCellCount = expectedSheetRowIterator.next().getPhysicalNumberOfCells();
                int actualCellCount = actualSheetRowIterator.next().getPhysicalNumberOfCells();
                assertEquals(expectedCellCount, actualCellCount, "Sheet " + actualSheet.getSheetName() + " have different count of columns..");
            }
        }
    }

    public void assertDataInExcelBookAllSheets(Workbook expected, Workbook actual) {
        int expectedSheetCount = expected.getNumberOfSheets();

        for (int i = 0; i < expectedSheetCount; i++) {
            Sheet expectedSheet = expected.getSheetAt(i);
            Sheet actualSheet = actual.getSheetAt(i);
            int expectedRowCount = expectedSheet.getPhysicalNumberOfRows();

            for (int row = 0; row < expectedRowCount; row++) {
                if (nonNull(expectedSheet.getRow(row)) && nonNull(actualSheet.getRow(row))) {
                    int expectedCellCount = expectedSheet.getRow(row).getPhysicalNumberOfCells();
                    for (int cell = 0; cell < expectedCellCount; cell++) {
                        Cell expectedCell = expectedSheet.getRow(row).getCell(cell);
                        Cell actualCell = actualSheet.getRow(row).getCell(cell);
                        if (nonNull(expectedCell) && nonNull(actualCell)) {
                            if (expectedCell.getCellType().equals(actualCell.getCellType())) {
                                if (expectedCell.getCellType() == CellType.STRING) {
                                    String expectedCellStringValue = expectedCell.getStringCellValue();
                                    String actualCellStringValue = actualCell.getStringCellValue();
                                    assertEquals(expectedCellStringValue, actualCellStringValue, "Sheet " + actualSheet.getSheetName() + " has different cell " + cell + " values in row " + row);
                                }
                                if (expectedCell.getCellType() == CellType.NUMERIC) {
                                    if (DateUtil.isCellDateFormatted(expectedCell) | DateUtil.isCellDateFormatted(actualCell)) {
                                        DataFormatter df = new DataFormatter();
                                        String expectedCellDateValue = df.formatCellValue(expectedCell);
                                        String actualCellDateValue = df.formatCellValue(actualCell);
                                        assertEquals(expectedCellDateValue, actualCellDateValue, "Sheet " + actualSheet.getSheetName() + " has different cell " + cell + " values in row " + row);
                                    } else {
                                        double expectedCellNumericValue = expectedCell.getNumericCellValue();
                                        double actualCellNumericValue = actualCell.getNumericCellValue();
                                        assertEquals(expectedCellNumericValue, actualCellNumericValue, "Sheet " + actualSheet.getSheetName() + " has different cell " + cell + " values in row " + row);
                                    }
                                }
                                if (expectedCell.getCellType() == CellType.BOOLEAN) {
                                    boolean expectedCellBooleanValue = expectedCell.getBooleanCellValue();
                                    boolean actualCellBooleanValue = actualCell.getBooleanCellValue();
                                    assertEquals(expectedCellBooleanValue, actualCellBooleanValue, "Sheet " + actualSheet.getSheetName() + " has different cell " + cell + " values in row " + row);
                                }
                            } else {
                                fail("Non matching cell " + cell + " type in sheet " + actualSheet.getSheetName());
                            }
                        }
                    }
                }
            }
        }
    }
}
