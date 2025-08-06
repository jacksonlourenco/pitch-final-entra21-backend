package com.checkbuy.project.service.supermercado.cooper.dto;

import java.util.List;

public record VariantProductProperties(
        int id,
        String name,
        List<VariantProductPropertiesValues> values
) {}
