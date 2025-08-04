package com.example.project.service.supermercado.cooper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Price(
        String criteriaReferenceCode,
        double price,
        String shoppingStoreReferenceCode
){}
