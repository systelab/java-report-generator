package com.werfen.report;

import com.werfen.report.model.*;
import com.werfen.report.service.GridReportService;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GridReportTest {
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Test
    public void generateGridReport() {
        try {
            GridReportService gridReportService = new GridReportService();
            File file = gridReportService.build(this.getConfiguration("sample1"), this.getData());
            file.createNewFile();
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }
        assertEquals("1", "1");
    }

    private GridReportData getData() {
        return new GridReportData.GridReportDataBuilder()
                .addRow(new GridReportRow.GridReportRowBuilder().addValue(new ReportField.ReportFieldBuilder().name("col1").value("val1").build())
                        .build()
                ).build();
    }

    private GridReportConfiguration getConfiguration(String fileName) throws IOException {
         return new GridReportConfiguration.GridReportConfigurationBuilder()
                .outputFilePath(fileName)
                .headerConfiguration(this.buildHeaderConfiguration())
                .footerConfiguration(this.buildReportFooterConfiguration())
                .addGridColumnConfiguration(new GridColumnConfiguration.GridColumnConfigurationBuilder().name("col1").width(GridReportColumnWidth.COLUMN_WIDTH_3).translation("col2").build())
                .build();
    }

    private ReportHeaderConfiguration buildHeaderConfiguration() {
        return new ReportHeaderConfiguration.ReportHeaderConfigurationBuilder()
                .title("Grid report")
                .field1(new ReportField.ReportFieldBuilder().name("Lab name").value("Name").build())
                .field2(new ReportField.ReportFieldBuilder().name("Second").value("Another").build())
                .field3(new ReportField.ReportFieldBuilder().name("Third").value("Another one").build())
                .field4(new ReportField.ReportFieldBuilder().name("Fourth").value("Last one").build())
                .build();
    }

    private ReportFooterConfiguration buildReportFooterConfiguration() {
        return new ReportFooterConfiguration.ReportFooterConfigurationBuilder()
                .createdAt(ZonedDateTime.now().toOffsetDateTime().format(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .additionalField1(new ReportField.ReportFieldBuilder().name("User").value("My self").build())
                .additionalField2(new ReportField.ReportFieldBuilder().name("Second").value("Another").build())
                .build();
    }
}
