package com.checkbuy.project.service.supermercado.cooper.dto.variants.prices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PriceDTO(
        String criteriaReferenceCode,
        double price,
        String shoppingStoreReferenceCode
){}
