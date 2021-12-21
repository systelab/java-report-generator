package com.werfen.report.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "report")
@ComponentScan(basePackages = {"com.werfen.report"})
public class PDFUtilitiesAutoConfiguration {
}
