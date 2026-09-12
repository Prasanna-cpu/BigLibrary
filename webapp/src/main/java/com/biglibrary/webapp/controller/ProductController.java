package com.biglibrary.webapp.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
public class ProductController {

    @Value("${bookstore.api-gateway-url}")
    private String apiGatewayUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public RedirectView index(){
        return new RedirectView("/products");
    }

    @GetMapping("/products")
    public String products(@RequestParam(defaultValue = "0") int pageNo, Model model){
        model.addAttribute("pageNo", pageNo);
        return "products";
    }

    @GetMapping("/api/products")
    public Object apiProducts(@RequestParam(defaultValue = "0") int pageNo){
        String url = apiGatewayUrl + "/products?page=" + pageNo;
        return restTemplate.getForObject(url, Object.class);
    }

}
