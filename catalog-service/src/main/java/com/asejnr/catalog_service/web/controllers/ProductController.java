package com.asejnr.catalog_service.web.controllers;

import com.asejnr.catalog_service.domain.PagedResult;
import com.asejnr.catalog_service.domain.Product;
import com.asejnr.catalog_service.domain.ProductNotFoundException;
import com.asejnr.catalog_service.domain.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int page) {
        return productService.getProducts(page);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code) {
        return productService
                .findByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }
}
