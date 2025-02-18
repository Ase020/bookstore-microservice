package com.asejnr.catalog_service.domain;

import java.math.BigDecimal;

public record Product(String code, String name, BigDecimal price, String description, String imageUrl) {}
