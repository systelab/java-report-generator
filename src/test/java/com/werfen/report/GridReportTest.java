package com.werfen.report;

import com.werfen.report.model.*;
import com.werfen.report.service.GridReportService;
import com.werfen.report.util.GeneralConfiguration;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GridReportTest {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String COLUMN_PREFIX_NAME = "col";
    private static final String COLUMN_PREFIX_TRANSLATION = "column ";
    private static final String COORDINATES_SEPARATOR = ".";
    private static final String LOT_OF_CHARACTERS_VALUE = "123456789012345678901234567890123456789012345678901234567890";

    public static List<GridReportRow> getListReportData(int columnCount, int rowCount) {


        List<GridReportRow> gridReportRows = new ArrayList<>();
        for (int row = 1; row <= rowCount; row++) {
            List<GridReportField> gridReportFields = new ArrayList<>();
            for (int column = 1; column <= columnCount-2; column++) {
                gridReportFields.add(GridReportField.of(COLUMN_PREFIX_NAME + column, column + COORDINATES_SEPARATOR + row));
            }
            gridReportFields.add(GridReportField.of(COLUMN_PREFIX_NAME + (columnCount - 1), null));
            gridReportFields.add(GridReportField.of(COLUMN_PREFIX_NAME + columnCount, null,"N/A"));

            gridReportRows.add(GridReportRow.builder().values(gridReportFields).build());
        }


        return gridReportRows;
    }

    @Test
    public void generateGridPdfReport() {
        try {
            GridReportService gridReportService = new GridReportService();
            GeneralConfiguration.setDefaultNullString("-");
            File file = gridReportService.build(this.getConfiguration("grid_report", 12), this.getDataSource(), ReportFormat.PDF, PageFormat.A4);
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
    public void generateGridPdfReportModifyDefault() {
        try {
            GridReportService gridReportService = new GridReportService();
            GeneralConfiguration.setDefaultNullString("Nop");
            File file = gridReportService.build(this.getConfiguration("grid_report_2", 12), this.getDataSource(), ReportFormat.PDF, PageFormat.A4);
            file.createNewFile();
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }

        try (PDDocument original = PDDocument.load(new File("grid_report_golden_null_values.pdf"));
             PDDocument generated = PDDocument.load(new File("grid_report_2.pdf"))) {
            PDFTextStripper textStripper = new PDFTextStripper();
            assertEquals(textStripper.getText(original), textStripper.getText(generated));
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void generateGridXlsxReport() {
        try {
            GridReportService gridReportService = new GridReportService();
            File file = gridReportService.build(this.getConfiguration("grid_report", 12), this.getDataSource(), ReportFormat.EXCEL, PageFormat.A4);
            file.createNewFile();
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }
    }

    private GridPageDataSource getDataSource() {
        return new ListGridPageDataSource(10, GridReportTest.getListReportData(12, 50));
    }

    private GridReportConfiguration getConfiguration(String fileName, int columnCount) throws IOException {

        List<GridColumnConfiguration> gridColumnConfigurations = new ArrayList<>();
        for (int column = 1; column <= columnCount; column++) {
            gridColumnConfigurations.add(GridColumnConfiguration.builder().name(COLUMN_PREFIX_NAME + column).width(GridReportColumnWidth.findByValue((column%7)+1)).translation(COLUMN_PREFIX_TRANSLATION + column).build());
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
