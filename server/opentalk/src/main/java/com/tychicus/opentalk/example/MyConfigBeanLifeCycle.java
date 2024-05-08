package com.tychicus.opentalk.example;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MyConfigBeanLifeCycle {
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public BeanLifeCycle myBeanForLifeCycle () {
        return new BeanLifeCycle();
    }
}
