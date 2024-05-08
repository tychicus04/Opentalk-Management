package com.tychicus.opentalk.config_profile_demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "mail2")
//@Configuration
@ConfigurationPropertiesScan
@Data
public class ConfigPropertiesWithScan {
    private String hostName;
    private int port;
    private String from;
}
