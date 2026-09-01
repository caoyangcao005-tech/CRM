package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/salesManager")

public class SalesChanceController {
    @RequestMapping("/salesChanceManager")
    public String salesChanceManager() {
        return "sale/saleChance";
    }

}
