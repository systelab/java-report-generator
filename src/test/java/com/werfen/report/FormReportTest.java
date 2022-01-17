package com.werfen.report;

import com.werfen.report.model.*;
import com.werfen.report.service.FormReportService;
import net.sf.jasperreports.engine.JRException;
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
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Test
    public void generateFormReport() {
        try {
            FormReportService formReportService = new FormReportService();
            File file = formReportService.build(this.getConfiguration("form_report"), this.getData(), PageFormat.A4);
            file.createNewFile();
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }
        try (PDDocument original = PDDocument.load(new File("form_report_golden.pdf"));
             PDDocument generated = PDDocument.load(new File("form_report.pdf"))) {
            PDFTextStripper textStripper = new PDFTextStripper();
            assertEquals(textStripper.getText(original), textStripper.getText(generated));
        } catch (Exception ex) {
            ex.printStackTrace();
        }    }

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

    private FormReportConfiguration getConfiguration(String fileName) throws IOException {
       return FormReportConfiguration.builder()
                .outputFilePath(fileName)
                .headerConfiguration(this.buildHeaderConfiguration())
                .footerConfiguration(this.buildReportFooterConfiguration())
                .build();
    }

    private ReportHeaderConfiguration buildHeaderConfiguration() {
        return ReportHeaderConfiguration.builder()
                .title("Grid report")
                .logoPath("src/main/resources/AF_WERFEN_BLUE_POS_RGB.png")
                .field1(GridReportField.of("Lab name","Name"))
                .field2(GridReportField.of("Second","Another"))
                .field3(GridReportField.of("Third","Another one"))
                .field4(GridReportField.of("Fourth","Last one"))

                .build();
    }

    private ReportFooterConfiguration buildReportFooterConfiguration() {
        return ReportFooterConfiguration.builder()
                .field1(GridReportField.of("Created at: ",ZonedDateTime.of(2021, 12, 1, 10, 1, 1, 1, ZoneId.systemDefault()).toOffsetDateTime().format(DateTimeFormatter.ofPattern(DATE_FORMAT))))
                .field2(GridReportField.of("Created by: ","My self"))
                .field3(GridReportField.of("Third: ","Another"))
                .build();
    }
}
