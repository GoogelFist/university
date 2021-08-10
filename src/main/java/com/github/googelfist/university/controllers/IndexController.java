package com.github.googelfist.university.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private static final String INDEX_VIEW_NAME = "/index";

    @GetMapping("/")
    public String index() {
        return INDEX_VIEW_NAME;
    }
}