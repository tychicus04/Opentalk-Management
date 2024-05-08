package com.tychicus.opentalk.example;

import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Setter
@Data
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)// This is the default scope
public class SingletonBean {
    private String message;

    public void showMessage() {
        System.out.println("Message: " + message);
    }
}