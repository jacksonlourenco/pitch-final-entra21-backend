package com.checkbuy.project.service.supermercado.cooper.dto.variants.product.properties.value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PropertiesValueDTO(
        String value
) {}
