package com.tychicus.opentalk.controller;

import com.tychicus.opentalk.example.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {
    @Autowired
    private Customer customer;

    // here we are using the RequestScope bean to store the name and retrieve it in the same request scope
    @RequestMapping("/nameRS")
    public String helloRS() {
        customer.getDataSessionScope().setName("Session Scope");
        return customer.getDataRequestScope().getName();
    }

    @RequestMapping("/nameSSUpdated")
    public String helloSSUpdated() {
        customer.getDataSessionScope().setName("Session Scope Updated Name");
        return customer.getDataSessionScope().getName();
    }

    @RequestMapping("/nameSS")
    public String helloSS() {
        return customer.getDataSessionScope().getName();
    }


    // cancel the session scope bean
    @RequestMapping("/cancelSS")
    public String cancelSS() {
        customer.setDataSessionScope(null);
        return "Session Scope Bean Cancelled";
    }
}
