package com.werfen.report;

import com.werfen.report.model.*;
import com.werfen.report.service.GridReportService;
import net.sf.jasperreports.engine.JRException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GridReportTest {
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Test
    public void generateGridPdfReport() {
        try {
            GridReportService gridReportService = new GridReportService();
            File file = gridReportService.build(this.getConfiguration("grid_report"), this.getData(), ReportFormat.PDF, PageFormat.A4);
            file.createNewFile();
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }

        try (PDDocument original = PDDocument.load(new File("grid_report_golden.pdf"));
             PDDocument generated = PDDocument.load(new File("grid_report.pdf"))) {
                PDFTextStripper textStripper = new PDFTextStripper();
                assertEquals(textStripper.getText(original), textStripper.getText(generated));
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    @Disabled
    public void generateGridXlsxReport() {
        try {
            GridReportService gridReportService = new GridReportService();
            File file = gridReportService.build(this.getConfiguration("grid_report"), this.getData(), ReportFormat.EXCEL, PageFormat.A4);
            file.createNewFile();
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }
    }

    private GridPageDataSource getData() {
        return new GridPageDataSource() {

            private final int PAGE_SIZE = 2;
            private final List<GridReportRow> rows = GridReportData.builder()
                    .row(GridReportRow.builder().value(GridReportField.builder().name("col1").value("1.1").build()).value(GridReportField.builder().name("col2").value("2.1").build()).value(GridReportField.builder().name("col3").value("3.1").build()).build())
                    .row(GridReportRow.builder().value(GridReportField.builder().name("col1").value("1.2").build()).value(GridReportField.builder().name("col2").value("2.2").build()).value(GridReportField.builder().name("col3").value("3.2").build()).build())
                    .row(GridReportRow.builder().value(GridReportField.builder().name("col1").value("1.3").build()).value(GridReportField.builder().name("col2").value("2.3").build()).value(GridReportField.builder().name("col3").value("3.3").build()).build())
                    .row(GridReportRow.builder().value(GridReportField.builder().name("col1").value("1.4").build()).value(GridReportField.builder().name("col2").value("2.4").build()).value(GridReportField.builder().name("col3").value("3.4").build()).build())
                    .row(GridReportRow.builder().value(GridReportField.builder().name("col1").value("1.5").build()).value(GridReportField.builder().name("col2").value("2.5").build()).value(GridReportField.builder().name("col3").value("3.5").build()).build())
                    .row(GridReportRow.builder().value(GridReportField.builder().name("col1").value("1.6").build()).value(GridReportField.builder().name("col2").value("2.6").build()).value(GridReportField.builder().name("col3").value("3.6").build()).build())
                    .row(GridReportRow.builder().value(GridReportField.builder().name("col1").value("1.7").build()).value(GridReportField.builder().name("col2").value("2.7").build()).value(GridReportField.builder().name("col3").value("3.7").build()).build())
                    .row(GridReportRow.builder().value(GridReportField.builder().name("col1").value("1.8").build()).value(GridReportField.builder().name("col2").value("2.8").build()).value(GridReportField.builder().name("col3").value("3.8").build()).build())
                    .row(GridReportRow.builder().value(GridReportField.builder().name("col1").value("1.9").build()).value(GridReportField.builder().name("col2").value("2.9").build()).value(GridReportField.builder().name("col3").value("3.9").build()).build())
                    .row(GridReportRow.builder().value(GridReportField.builder().name("col1").value("1.10").build()).value(GridReportField.builder().name("col2").value("2.10").build()).value(GridReportField.builder().name("col3").value("3.10").build()).build())
                    .row(GridReportRow.builder().value(GridReportField.builder().name("col1").value("1.11").build()).value(GridReportField.builder().name("col2").value("2.11").build()).value(GridReportField.builder().name("col3").value("3.11").build()).build())
                    .row(GridReportRow.builder().value(GridReportField.builder().name("col1").value("1.12").build()).value(GridReportField.builder().name("col2").value("2.12").build()).value(GridReportField.builder().name("col3").value("3.12").build()).build())
                    .build().getRows();
            private int currentIndex = 0;
            private List<GridReportRow> currentRows;

            @Override
            public void moveFirst() {
                this.currentRows = rows.subList(currentIndex, currentIndex + PAGE_SIZE);
            }

            @Override
            public boolean nextPage() {
                currentIndex += PAGE_SIZE;
                if (currentIndex < this.rows.size()) {
                    this.currentRows = this.rows.subList(currentIndex, (currentIndex + PAGE_SIZE) < this.rows.size() ? currentIndex + PAGE_SIZE : this.rows.size() - 1);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public int getRowCount() {
                if (this.rows.isEmpty()) {
                    return 0;
                } else {
                    return rows.size();
                }
            }

            @Override
            public List<GridReportRow> getCurrentPageRows() {
                return currentRows;
            }
        };
    }

    private GridReportConfiguration getConfiguration(String fileName) throws IOException {
        return GridReportConfiguration.builder()
                .outputFilePath(fileName)
                .headerConfiguration(this.buildHeaderConfiguration())
                .footerConfiguration(this.buildReportFooterConfiguration())
                .gridColumnConfiguration(GridColumnConfiguration.builder().name("col1").width(GridReportColumnWidth.COLUMN_WIDTH_2).translation("column 1").build())
                .gridColumnConfiguration(GridColumnConfiguration.builder().name("col2").width(GridReportColumnWidth.COLUMN_WIDTH_3).translation("column 2").build())
                .gridColumnConfiguration(GridColumnConfiguration.builder().name("col3").width(GridReportColumnWidth.COLUMN_WIDTH_4).translation("column 3").build())
                .build();
    }

    private ReportHeaderConfiguration buildHeaderConfiguration() {
        return ReportHeaderConfiguration.builder()
                .title("Grid report")
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
                .field3(GridReportField.builder().name("Third: ").value("Another").build())
                .build();

    }
}
