package com.checkbuy.project.service.supermercado.cooper.dto.variants.product.properties;

import com.checkbuy.project.service.supermercado.cooper.dto.variants.product.properties.value.PropertiesValueDTO;

import java.util.List;

public record PropertiesDTO(
        int id,
        String name,
        List<PropertiesValueDTO> values
) {}
