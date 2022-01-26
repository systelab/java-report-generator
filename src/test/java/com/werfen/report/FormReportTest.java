package com.werfen.report;

import com.werfen.report.model.*;
import com.werfen.report.service.FormReportService;
import com.werfen.report.exception.ReportException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormReportTest {
    private static final String GOLDEN_PATH = "src/test/resources/golden/";
    private static final String GOLDEN_SUFFIX = "_golden";
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Test
    public void generateFormReport() throws IOException, ReportException {
        String fileName = "form_report";

        FormReportService formReportService = new FormReportService();
        File file = formReportService.build(this.getConfiguration(fileName + ReportFormat.PDF.getFileExtension()), this.getData(), PageFormat.A4);
        file.createNewFile();

        PDDocument original = PDDocument.load(new File(GOLDEN_PATH + fileName + GOLDEN_SUFFIX + ReportFormat.PDF.getFileExtension()));
        PDDocument generated = PDDocument.load(new File(fileName + ReportFormat.PDF.getFileExtension()));
        PDFTextStripper textStripper = new PDFTextStripper();
        assertEquals(textStripper.getText(original), textStripper.getText(generated));
    }

    @Test
    public void generateFormReportWithLessFields() throws IOException, ReportException {
        String fileName = "form_report_less_fields";

        FormReportService formReportService = new FormReportService();
        File file = formReportService.build(this.getLessFieldsConfiguration(fileName + ReportFormat.PDF.getFileExtension()), this.getData(), PageFormat.A4);
        file.createNewFile();

        PDDocument original = PDDocument.load(new File(GOLDEN_PATH + fileName + GOLDEN_SUFFIX + ReportFormat.PDF.getFileExtension()));
        PDDocument generated = PDDocument.load(new File(fileName + ReportFormat.PDF.getFileExtension()));
        PDFTextStripper textStripper = new PDFTextStripper();
        assertEquals(textStripper.getText(original), textStripper.getText(generated));
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
