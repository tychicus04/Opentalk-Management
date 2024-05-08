package com.tychicus.opentalk.controller;


import com.tychicus.opentalk.Config.LangConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LangConfig langConfig;
    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request) {
        String message = messageSource.getMessage("hello", null, "default message", request.getLocale());
        System.out.println("Message: " + message);
        model.addAttribute("message", message);
        return "index";
    }


}
