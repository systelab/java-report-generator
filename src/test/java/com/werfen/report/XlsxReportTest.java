package com.werfen.report;

import com.werfen.report.model.*;
import com.werfen.report.service.XlsxService;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class XlsxReportTest {
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Test
    public void generateColumnXlsxReport() {
        try {
            XlsxService xlsxService = new XlsxService();
            File file = xlsxService.build(this.getConfiguration("excel_summary_report"), this.getData(), ReportFormat.EXCEL, PageFormat.FULL_SCREEN_720P);
            file.createNewFile();
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }

//        try (PDDocument original = PDDocument.load(new File("grid_report_golden.pdf"));
//             PDDocument generated = PDDocument.load(new File("excel_report.pdf"))) {
//                PDFTextStripper textStripper = new PDFTextStripper();
//                assertEquals(textStripper.getText(original), textStripper.getText(generated));
//        } catch(Exception ex) {
//            ex.printStackTrace();
//        }
    }

    private GridReportData getData() {
        return GridReportData.builder()
                .row(GridReportRow.builder().value(GridReportField.builder().name("col1").value("val1").build())
                        .build()
                ).build();
    }

    private GridReportConfiguration getConfiguration(String fileName) throws IOException {
        return GridReportConfiguration.builder()
                .outputFilePath(fileName)
                .headerConfiguration(this.buildHeaderConfiguration())
                .footerConfiguration(this.buildReportFooterConfiguration())
                .gridColumnConfiguration(GridColumnConfiguration.builder().name("col1").width(GridReportColumnWidth.COLUMN_WIDTH_3).translation("col2").build())
                .build();
    }

    private ReportHeaderConfiguration buildHeaderConfiguration() {
        return ReportHeaderConfiguration.builder()
                .title("Excel report")
                .logoPath("src/main/resources/AF_WERFEN_BLUE_POS_RGB.png")
                .field1(GridReportField.builder().name("Lab name").value("Name").build())
                .field2(GridReportField.builder().name("Second").value("Another").build())
                .field3(GridReportField.builder().name("Third").value("Another one").build())
                .field4(GridReportField.builder().name("Fourth").value("Last one").build())
                .build();
    }

    private ReportFooterConfiguration buildReportFooterConfiguration() {
        return ReportFooterConfiguration.builder()
                .field1(GridReportField.builder().name("Created at: ").value(ZonedDateTime.of(2021, 12, 1, 10, 1, 1, 1, ZoneId.systemDefault()).toOffsetDateTime().format(DateTimeFormatter.ofPattern(DATE_FORMAT))).build())
                .field2(GridReportField.builder().name("Created by: ").value("My self").build())
                .field3(GridReportField.builder().name("Persecuted by: ").value(":_S").build())
                .build();

    }
}
