package com.systelab.pdf.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "systelab.pdf")
@ComponentScan(basePackages = {"com.systelab.pdf"})
public class PDFUtilitiesAutoConfiguration {
}
