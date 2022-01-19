package com.werfen.report;


import org.apache.poi.ss.usermodel.*;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ExcelComparator {

    public void assertWorkbookEquals(Workbook expected, Workbook actual) {
        int expectedSheetCount = expected.getNumberOfSheets();
        int actualSheetCount = actual.getNumberOfSheets();
        assertEquals(expectedSheetCount, actualSheetCount, "Excel work books have different number of sheets");

        for (int sheetIndex = 0; sheetIndex < expectedSheetCount; sheetIndex++) {
            this.assertSheetEquals(expected.getSheetAt(sheetIndex), actual.getSheetAt(sheetIndex));
        }
    }

    public void assertSheetEquals(Sheet expectedSheet, Sheet actualSheet) {
        String expectedSheetName = expectedSheet.getSheetName();
        String actualSheetName = actualSheet.getSheetName();
        assertEquals(expectedSheetName, actualSheetName, "Sheets have different names");

        int expectedRowCount = expectedSheet.getPhysicalNumberOfRows();
        int actualRowCount = actualSheet.getPhysicalNumberOfRows();
        assertEquals(expectedRowCount, actualRowCount, "Sheet " + actualSheet.getSheetName() + " have different number of rows");

        for (int rowIndex = 0; rowIndex < expectedRowCount; rowIndex++) {
            this.assertRowEquals(expectedSheet.getRow(rowIndex), actualSheet.getRow(rowIndex));
        }
    }

    public void assertRowEquals(Row expected, Row actual) {
        if (nonNull(expected) && nonNull(actual)) {
            int expectedCellCount = expected.getPhysicalNumberOfCells();
            int actualCellCount = actual.getPhysicalNumberOfCells();
            assertEquals(expectedCellCount, actualCellCount, "Sheet " + actual.getSheet().getSheetName() + " have different number of cells in row " + actual.getRowNum());

            for (int cellIndex = 0; cellIndex < expectedCellCount; cellIndex++) {
                this.assertCellEquals(expected.getCell(cellIndex), actual.getCell(cellIndex));
            }
        }
    }

    public void assertCellEquals(Cell expected, Cell actual) {
        if (nonNull(expected) && nonNull(actual)) {
            if (expected.getCellType().equals(actual.getCellType())) {
                switch (expected.getCellType()) {
                    case STRING:
                        String expectedCellStringValue = expected.getStringCellValue();
                        String actualCellStringValue = actual.getStringCellValue();
                        assertEquals(expectedCellStringValue, actualCellStringValue, "Sheet " + actual.getSheet().getSheetName() + " has different cell " + actual.getColumnIndex() + " values in row " + actual.getRow().getRowNum());
                        break;

                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(expected) | DateUtil.isCellDateFormatted(actual)) {
                            DataFormatter df = new DataFormatter();
                            String expectedCellDateValue = df.formatCellValue(expected);
                            String actualCellDateValue = df.formatCellValue(actual);
                            assertEquals(expectedCellDateValue, actualCellDateValue, "Sheet " + actual.getSheet().getSheetName() + " has different cell " + actual.getColumnIndex() + " values in row " + actual.getRow().getRowNum());
                        } else {
                            double expectedCellNumericValue = expected.getNumericCellValue();
                            double actualCellNumericValue = actual.getNumericCellValue();
                            assertEquals(expectedCellNumericValue, actualCellNumericValue, 10e-6, "Sheet " + actual.getSheet().getSheetName() + " has different cell " + actual.getColumnIndex() + " values in row " + actual.getRow().getRowNum());
                        }
                        break;

                    case BOOLEAN:
                        boolean expectedCellBooleanValue = expected.getBooleanCellValue();
                        boolean actualCellBooleanValue = actual.getBooleanCellValue();
                        assertEquals(expectedCellBooleanValue, actualCellBooleanValue, "Sheet " + actual.getSheet().getSheetName() + " has different cell " + actual.getColumnIndex() + " values in row " + actual.getRow().getRowNum());
                        break;

                    default:
                        fail("Unexpected cell type in row " + actual.getRow().getRowNum() + " cell " + actual.getColumnIndex());
                        break;
                }

            } else {
                fail("Sheet " + actual.getSheet().getSheetName() + " has non matching cell " + actual.getColumnIndex() + " type in row " + actual.getRow().getRowNum());
            }
        }
    }
}
