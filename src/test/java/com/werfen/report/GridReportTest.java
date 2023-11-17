package com.werfen.report;

import com.werfen.report.exception.ReportException;
import com.werfen.report.model.*;
import com.werfen.report.service.GridReportService;
import com.werfen.report.test.utils.assertions.ComparisonResultAssertions;
import com.werfen.report.test.utils.excel.ExcelComparator;
import com.werfen.report.test.utils.pdf.PDFComparator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GridReportTest {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String COLUMN_PREFIX_NAME = "Cól";
    private static final String COLUMN_PREFIX_TRANSLATION = "cólumn ";
    private static final String COORDINATES_SEPARATOR = ".";
    private static final String GOLDEN_SUFFIX = "_golden";
    private static final String GOLDEN_PATH = "src/test/resources/golden/";
    private static final String TEST_PATH = "test_reports/";

    @BeforeEach
    void beforeEach() {
        File directory = new File(TEST_PATH);
        if(!directory.exists()){
            directory.mkdirs();
        }
    }

    @Test
    void generateFileGridPdfReport() throws IOException, ReportException {
        String fileName = "grid_report";

        GridReportService gridReportService = new GridReportService();
        GeneralConfiguration.setDefaultNullString("-");
        File file = gridReportService.buildFile(this.getConfiguration(TEST_PATH + fileName + ReportFormat.PDF.getFileExtension(), 12), this.getDataSource(), ReportFormat.PDF, PageFormat.A4);
        file.createNewFile();


        File expectedFile = new File(GOLDEN_PATH + fileName + GOLDEN_SUFFIX + ReportFormat.PDF.getFileExtension());
        File actualFile = new File(TEST_PATH + fileName + ReportFormat.PDF.getFileExtension());
        ComparisonResultAssertions.assertEquals(PDFComparator.compareFiles(expectedFile, actualFile));
    }

    @Test
    void generateFileGridPdfReportWithPassword() throws IOException, ReportException {
        String fileName = "grid_report_with_password";

        GridReportService gridReportService = new GridReportService();
        GeneralConfiguration.setDefaultNullString("-");
        File file = gridReportService.buildFile(this.getConfiguration(TEST_PATH + fileName + ReportFormat.PDF.getFileExtension(), 12), this.getDataSource(), ReportFormat.PDF, PageFormat.A4, "password");
        file.createNewFile();


        assertThrows(InvalidPasswordException.class, () -> PDDocument.load(file));
        assertThrows(InvalidPasswordException.class, () -> PDDocument.load(file, "wrong_password"));
        assertDoesNotThrow(() -> PDDocument.load(file, "password"));
    }

    @Test
    void generateStreamGridPdfReport() throws IOException, ReportException {
        String fileName = "grid_report";

        GridReportService gridReportService = new GridReportService();
        GeneralConfiguration.setDefaultNullString("-");
        ByteArrayOutputStream report = gridReportService.buildStream(this.getConfiguration(null, 12), this.getDataSource(), ReportFormat.PDF, PageFormat.A4);

        InputStream actualStream = new ByteArrayInputStream(report.toByteArray());
        InputStream expectedStream = Files.newInputStream(Paths.get(GOLDEN_PATH + fileName + GOLDEN_SUFFIX + ReportFormat.PDF.getFileExtension()));

        ComparisonResultAssertions.assertEquals(PDFComparator.compareStreams(expectedStream, actualStream));
    }

    @Test
    void generateFileGridPdfReportModifyDefault() throws IOException, ReportException {
        String fileName = "grid_report_null_values";
        GridReportService gridReportService = new GridReportService();
        GeneralConfiguration.setDefaultNullString("Nop");
        File file = gridReportService.buildFile(this.getConfiguration(TEST_PATH + fileName + ReportFormat.PDF.getFileExtension(), 12), this.getDataSource(), ReportFormat.PDF, PageFormat.A4);
        file.createNewFile();

        File expectedFile = new File(GOLDEN_PATH + fileName + GOLDEN_SUFFIX + ReportFormat.PDF.getFileExtension());
        File actualFile = new File(TEST_PATH + fileName + ReportFormat.PDF.getFileExtension());
        ComparisonResultAssertions.assertEquals(PDFComparator.compareFiles(expectedFile, actualFile));
    }

    @Test
    void generateFileGridXlsxReport() throws IOException, ReportException {
        String fileName = "grid_report";

        GridReportService gridReportService = new GridReportService();
        File file = gridReportService.buildFile(this.getConfiguration(TEST_PATH + fileName + ReportFormat.EXCEL.getFileExtension(), 12), this.getDataSource(), ReportFormat.EXCEL, PageFormat.A4);
        file.createNewFile();

        File expectedFile = new File(GOLDEN_PATH + fileName + GOLDEN_SUFFIX + ReportFormat.EXCEL.getFileExtension());
        File actualFile = new File(TEST_PATH + fileName + ReportFormat.EXCEL.getFileExtension());
        ComparisonResultAssertions.assertEquals(ExcelComparator.compareFiles(expectedFile, actualFile));
    }

    @Test
    void generateStreamGridXlsxReport() throws IOException, ReportException {
        String fileName = "grid_report";

        GridReportService gridReportService = new GridReportService();
        ByteArrayOutputStream report = gridReportService.buildStream(this.getConfiguration(null, 12), this.getDataSource(), ReportFormat.EXCEL, PageFormat.A4);


        InputStream expectedStream = Files.newInputStream(Paths.get(GOLDEN_PATH + fileName + GOLDEN_SUFFIX + ReportFormat.EXCEL.getFileExtension()));
        ByteArrayInputStream actualStream = new ByteArrayInputStream(report.toByteArray());
        ComparisonResultAssertions.assertEquals(ExcelComparator.compareStreams(expectedStream, actualStream));
    }

    private GridPageDataSource getDataSource() {
        return new ListGridPageDataSource(10, GridReportTest.getListReportData(12, 50));
    }

    private static List<GridReportRow> getListReportData(int columnCount, int rowCount) {


        List<GridReportRow> gridReportRows = new ArrayList<>();
        for (int row = 1; row <= rowCount; row++) {
            List<GridReportField> gridReportFields = new ArrayList<>();
            for (int column = 1; column <= columnCount - 2; column++) {
                gridReportFields.add(GridReportField.of(COLUMN_PREFIX_NAME + column, column + COORDINATES_SEPARATOR + row));
            }
            gridReportFields.add(GridReportField.of(COLUMN_PREFIX_NAME + (columnCount - 1), null));
            gridReportFields.add(GridReportField.of(COLUMN_PREFIX_NAME + columnCount, null, "N/A"));

            gridReportRows.add(GridReportRow.builder().values(gridReportFields).build());
        }

        return gridReportRows;
    }

    private GridReportConfiguration getConfiguration(String fileName, int columnCount) {

        List<GridColumnConfiguration> gridColumnConfigurations = new ArrayList<>();
        for (int column = 1; column <= columnCount; column++) {
            gridColumnConfigurations.add(GridColumnConfiguration.builder().name(COLUMN_PREFIX_NAME + column).width(GridReportColumnWidth.findByValue((column % 7) + 1)).translation(COLUMN_PREFIX_TRANSLATION + column).build());
        }
        return GridReportConfiguration.builder()
                .outputFilePath(fileName)
                .headerConfiguration(this.buildHeaderConfiguration())
                .footerConfiguration(this.buildReportFooterConfiguration())
                .gridColumnConfigurations(gridColumnConfigurations)
                .build();
    }

    private ReportHeaderConfiguration buildHeaderConfiguration() {
        return ReportHeaderConfiguration.builder()
                .title("Grid report")
                .logoPath("src/main/resources/AF_WERFEN_BLUE_POS_RGB.png")
                .field1(GridReportField.of("Lab name", "Name"))
                .field2(GridReportField.of("Second", "Número"))
                .field3(GridReportField.of("Third", "Another one"))
                .field4(GridReportField.of("Fourth", "Last one"))
                .build();
    }

    private ReportFooterConfiguration buildReportFooterConfiguration() {
        return ReportFooterConfiguration.builder()
                .field1(GridReportField.of("Created at: ", ZonedDateTime.of(2021, 12, 1, 10, 1, 1, 1, ZoneId.systemDefault()).toOffsetDateTime().format(DateTimeFormatter.ofPattern(DATE_FORMAT))))
                .field2(GridReportField.of("Created by: ", "My self"))
                .field3(GridReportField.of("Third: ", "Another"))
                // TODO: This shall be set to true as soon as the related bug is fixed
                .showPageNumbers(false)
                .build();

    }
}
