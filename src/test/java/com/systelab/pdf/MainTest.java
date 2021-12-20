package com.systelab.pdf;

import com.systelab.pdf.model.*;
import com.systelab.pdf.service.GridReportService;
import com.systelab.pdf.service.GridReportTemplateBuilder;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;


@ExtendWith(SpringExtension.class)
public class MainTest {
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    MessageSource messageSource;

    @Test
    public void testGetUserList() {
        try {
            GridReportConfiguration configuration = this.getConfiguration("sample1");
            GridReportData data = this.getData();

            GridReportService gridReportService = new GridReportService(messageSource);
            File file = gridReportService.build(configuration,data, Locale.US);
            file.createNewFile();
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }
        assertEquals("1", "2");
    }

    private GridReportData getData() {
        List<GridReportRow> rows = new ArrayList<>();
        rows.add(new GridReportRow(Arrays.asList(ReportField.of("col1", "val1"))));
        return new GridReportData(rows);
    }

    private GridReportConfiguration getConfiguration(String fileName) throws IOException {
        ReportHeaderConfiguration header = this.buildHeaderConfiguration();
        ReportFooterConfiguration footer = this.buildReportFooterConfiguration();
        List<GridColumnConfiguration> columns = new ArrayList<>();
        columns.add(new GridColumnConfiguration("col1", GridReportColumnWidth.COLUMN_WIDTH_3,"col1"));
        return new GridReportConfiguration(fileName, header, footer, columns);
    }

    private ReportHeaderConfiguration buildHeaderConfiguration() throws IOException {
        return new ReportHeaderConfiguration("Sample list report",
                null,
                ReportField.of("Lab name", "Name"),
                ReportField.of("Second", "Another"),
                ReportField.of("Third", "Another one"),
                ReportField.of("Fourth", "Last one"));
    }

    private ReportFooterConfiguration buildReportFooterConfiguration() {
        return new ReportFooterConfiguration(
                ZonedDateTime.now().toOffsetDateTime().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                ReportField.of("User", "My self"),
                ReportField.of("Second", "Another"));
    }
}
