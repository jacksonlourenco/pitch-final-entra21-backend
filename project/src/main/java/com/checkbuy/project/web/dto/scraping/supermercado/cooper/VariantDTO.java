package com.checkbuy.project.web.dto.scraping.supermercado.cooper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VariantDTO(
        String presentation,
        List<PriceDTO> prices,
        ProductDTO product
){}
