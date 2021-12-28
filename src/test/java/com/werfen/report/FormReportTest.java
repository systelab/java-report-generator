package com.werfen.report;

import com.werfen.report.model.*;
import com.werfen.report.service.FormReportService;
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

public class FormReportTest {
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Test
    public void generateFormReport() {
        try {
            FormReportService formReportService = new FormReportService();
            File file = formReportService.build(this.getConfiguration("sample1"), this.getData());
            file.createNewFile();
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }
        assertEquals("1", "1");
    }

    private FormReportData getData() {
        return new FormReportData.FormReportDataBuilder()
                .addSection(new FormReportSection.FormReportSectionBuilder()
                        .title("Section 1 title")
                        .addField(new FormReportField.FormReportFieldBuilder()
                                .label("Field 1 label")
                                .value("Field 1 value")
                                .addSubfield(new FormReportField.FormReportFieldBuilder()
                                        .label("Field 2 label")
                                        .value("Field 2 value")
                                        .build())
                                .addSubfield(new FormReportField.FormReportFieldBuilder()
                                        .label("Field 3 label")
                                        .value("Field 3 value")
                                        .build()
                                ).build()
                        )
                        .addField(new FormReportField.FormReportFieldBuilder()
                                .label("Field 4 label")
                                .value("Field 4 value")
                                .build())
                        .addSubsection(new FormReportSection.FormReportSectionBuilder()
                                .title("Subsection 1.1 title")
                                .addField(new FormReportField.FormReportFieldBuilder()
                                        .label("Field 5 label")
                                        .value("Field 5 value")
                                        .build()
                                ).build()
                        ).build()
                )
                .addSection(new FormReportSection.FormReportSectionBuilder()
                        .title("Section 2 title")
                        .addField(new FormReportField.FormReportFieldBuilder()
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
