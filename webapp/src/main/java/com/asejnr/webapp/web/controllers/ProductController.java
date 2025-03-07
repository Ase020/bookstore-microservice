package com.asejnr.webapp.web.controllers;

import com.asejnr.webapp.clients.catalog.CatalogServiceClient;
import com.asejnr.webapp.clients.catalog.PageResult;
import com.asejnr.webapp.clients.catalog.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final CatalogServiceClient catalogServiceClient;

    ProductController(CatalogServiceClient catalogServiceClient) {
        this.catalogServiceClient = catalogServiceClient;
    }

    @GetMapping
    String index() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    String productsPage(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        model.addAttribute("pageNo", page);
        log.info("Fetching products for page: {}", page);
        return "products";
    }

    @GetMapping("/api/products")
    @ResponseBody
    PageResult<Product> products(@RequestParam(name = "page", defaultValue = "1") int page) {
        return catalogServiceClient.getProducts(page);
    }
}
