package edu.cit.pael.neilrossulysses.campusequipmentloan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // must be @Controller, not @RestController
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home"; // resolves templates/home.html
    }
}