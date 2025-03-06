package com.asejnr.webapp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
class OrderController {

    @GetMapping("/cart")
    String cart(){
        return "cart";
    }

    @GetMapping("/orders/{orderNumber}")
    String showOrderDetails(@PathVariable String orderNumber, Model model){
        model.addAttribute("orderNumber", orderNumber);
        return "order_details";
    }

    @GetMapping("/orders")
    String showOrders(Model model){
        return "orders";
    }
}
