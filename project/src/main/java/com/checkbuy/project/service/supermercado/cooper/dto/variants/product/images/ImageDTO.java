package com.checkbuy.project.service.supermercado.cooper.dto.variants.product.images;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ImageDTO(
        String urlOriginal
){}
