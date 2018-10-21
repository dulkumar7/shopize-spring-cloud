package com.shopizer.shop.services.taxservice.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaxServiceHomeController {

    @GetMapping("/")
    public String home() {
        return "TAX-SERVICE app if up and running";
    }
}
