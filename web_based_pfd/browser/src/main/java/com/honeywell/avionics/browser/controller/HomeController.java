package com.honeywell.avionics.browser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @RequestMapping(value = "/flight")
    public String flight(
            @RequestParam(name = "flightId", defaultValue = "") String flightId,
            @RequestParam(name = "time", defaultValue = "0") long time) {
        return "flight";
    }
}
