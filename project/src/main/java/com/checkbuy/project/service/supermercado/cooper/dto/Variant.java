package com.checkbuy.project.service.supermercado.cooper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Variant(
        String presentation,
        List<Price> prices,
        Product product
){}
