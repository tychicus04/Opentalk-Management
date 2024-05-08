package com.tychicus.opentalk.config_profile_demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "application")
@Data
public class ConfigListObject {
    private List<Object> profiles;
    public List<Object> getProfiles() {
        return profiles;
    }
    public void setProfiles(List<Object> profiles) {
        this.profiles = profiles;
    }

}