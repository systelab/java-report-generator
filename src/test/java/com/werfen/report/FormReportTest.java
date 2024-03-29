package com.werfen.report;

import com.werfen.report.exception.ReportException;
import com.werfen.report.model.*;
import com.werfen.report.service.FormReportService;
import com.werfen.report.test.utils.assertions.ComparisonResultAssertions;
import com.werfen.report.test.utils.pdf.PDFComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FormReportTest {
    private static final String GOLDEN_PATH = "src/test/resources/golden/";
    private static final String TEST_PATH = "test_reports/";
    private static final String GOLDEN_SUFFIX = "_golden";
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @BeforeEach
    void beforeEach() {
        File directory = new File(TEST_PATH);
        if(!directory.exists()){
            directory.mkdirs();
        }
    }
    @Test
    public void generateFileFormReport() throws IOException, ReportException {
        String fileName = "form_report";

        FormReportService formReportService = new FormReportService();
        File file = formReportService.build(this.getConfiguration(TEST_PATH + fileName + ReportFormat.PDF.getFileExtension()), this.getData(), PageFormat.A4);
        file.createNewFile();

        File expectedFile = new File(GOLDEN_PATH + fileName + GOLDEN_SUFFIX + ReportFormat.PDF.getFileExtension());
        File actualFile = new File(TEST_PATH + fileName + ReportFormat.PDF.getFileExtension());
        ComparisonResultAssertions.assertEquals(PDFComparator.compareFiles(expectedFile, actualFile));
    }

    @Test
    public void generateStreamFormReport() throws IOException, ReportException {
        String fileName = "form_report";

        FormReportService formReportService = new FormReportService();
        ByteArrayOutputStream report = formReportService.buildToStream(this.getConfiguration(null), this.getData(), PageFormat.A4);

        InputStream actualStream = new ByteArrayInputStream(report.toByteArray());
        InputStream expectedStream = Files.newInputStream(Paths.get(GOLDEN_PATH + fileName + GOLDEN_SUFFIX + ReportFormat.PDF.getFileExtension()));
        ComparisonResultAssertions.assertEquals(PDFComparator.compareStreams(expectedStream, actualStream));
    }

    @Test
    public void generateFileFormReportWithLessFields() throws IOException, ReportException {
        String fileName = "form_report_less_fields";

        FormReportService formReportService = new FormReportService();
        File file = formReportService.build(this.getLessFieldsConfiguration(TEST_PATH + fileName + ReportFormat.PDF.getFileExtension()), this.getData(), PageFormat.A4);
        file.createNewFile();

        File expectedFile = new File(GOLDEN_PATH + fileName + GOLDEN_SUFFIX + ReportFormat.PDF.getFileExtension());
        File actualFile = new File(TEST_PATH + fileName + ReportFormat.PDF.getFileExtension());
        ComparisonResultAssertions.assertEquals(PDFComparator.compareFiles(expectedFile, actualFile));
    }

    private FormReportData getData() {
        return FormReportData.builder()
                .section(FormReportSection.builder()
                        .title("Section 1 title")
                        .field(FormReportField.builder()
                                .label("Field 1 label")
                                .value("Field 1 value")
                                .subfield(FormReportField.builder()
                                        .label("Field 2 label")
                                        .value("Field 2 value")
                                        .build())
                                .subfield(FormReportField.builder()
                                        .label("Field 3 label")
                                        .value("Field 3 value")
                                        .build()
                                ).build()
                        )
                        .field(FormReportField.builder()
                                .label("Field 4 label")
                                .value("Field 4 value")
                                .build())
                        .subsection(FormReportSection.builder()
                                .title("Subsection 1.1 title")
                                .field(FormReportField.builder()
                                        .label("Field 5 label")
                                        .value("Field 5 value")
                                        .build()
                                ).build()
                        ).build()
                )
                .section(FormReportSection.builder()
                        .title("Section 2 title")
                        .field(FormReportField.builder()
                                .label("Field 6 label")
                                .value("Field 6 value")
                                .build()
                        ).build()
                ).build();
    }

    private FormReportConfiguration getConfiguration(String fileName) {
        return FormReportConfiguration.builder()
                .outputFilePath(fileName)
                .headerConfiguration(this.buildHeaderConfiguration())
                .footerConfiguration(this.buildFooterConfiguration())
                .build();
    }

    private FormReportConfiguration getLessFieldsConfiguration(String fileName) {
        return FormReportConfiguration.builder()
                .outputFilePath(fileName)
                .headerConfiguration(this.buildHeaderLessFieldsConfiguration())
                .footerConfiguration(this.buildFooterLessFieldsConfiguration())
                .build();
    }

    private ReportHeaderConfiguration buildHeaderConfiguration() {
        return ReportHeaderConfiguration.builder()
                .title("Grid report")
                .logoPath("src/main/resources/AF_WERFEN_BLUE_POS_RGB.png")
                .field1(GridReportField.of("Lab name", "Name"))
                .field2(GridReportField.of("Second", "Another"))
                .field3(GridReportField.of("Third", "Another one"))
                .field4(GridReportField.of("Fourth", "Last one"))

                .build();
    }

    private ReportHeaderConfiguration buildHeaderLessFieldsConfiguration() {
        return ReportHeaderConfiguration.builder()
                .title("Grid report")
                .logoPath("src/main/resources/AF_WERFEN_BLUE_POS_RGB.png")
                .field1(GridReportField.of("Lab name", "Name"))
                .field2(GridReportField.of("Second", "Another"))
                .build();
    }

    private ReportFooterConfiguration buildFooterConfiguration() {
        return ReportFooterConfiguration.builder()
                .field1(GridReportField.of("Created at: ", ZonedDateTime.of(2021, 12, 1, 10, 1, 1, 1, ZoneId.systemDefault()).toOffsetDateTime().format(DateTimeFormatter.ofPattern(DATE_FORMAT))))
                .field2(GridReportField.of("Created by: ", "My self"))
                .field3(GridReportField.of("Third: ", "Another"))
                // TODO: This shall be set to true as soon as the related bug is fixed
                .showPageNumbers(false)
                .build();
    }

    private ReportFooterConfiguration buildFooterLessFieldsConfiguration() {
        return ReportFooterConfiguration.builder()
                .field1(GridReportField.of("Created at: ", ZonedDateTime.of(2021, 12, 1, 10, 1, 1, 1, ZoneId.systemDefault()).toOffsetDateTime().format(DateTimeFormatter.ofPattern(DATE_FORMAT))))
                .field2(GridReportField.of("Created by: ", "My self"))
                // TODO: This shall be set to true as soon as the related bug is fixed
                .showPageNumbers(false)
                .build();
    }
}
