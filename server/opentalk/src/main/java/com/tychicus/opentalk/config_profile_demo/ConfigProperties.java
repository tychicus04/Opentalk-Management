package com.tychicus.opentalk.config_profile_demo;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan// 1 trong 2 cách để sử dụng @ConfigurationProperties
@ConfigurationProperties(prefix = "mail1")
@Data
public class ConfigProperties {
    private String hostName;
    private int port;
    private String from;
}
