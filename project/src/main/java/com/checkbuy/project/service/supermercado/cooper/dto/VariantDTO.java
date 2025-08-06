package com.checkbuy.project.service.supermercado.cooper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VariantDTO(
        String presentation,
        List<PriceDTO> prices,
        ImageListDTO product,
        List<VariantProductProperties> properties
){}
