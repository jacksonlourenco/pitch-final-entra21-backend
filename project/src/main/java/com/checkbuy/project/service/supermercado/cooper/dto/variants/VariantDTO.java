package com.checkbuy.project.service.supermercado.cooper.dto.variants;

import com.checkbuy.project.service.supermercado.cooper.dto.variants.prices.PriceDTO;
import com.checkbuy.project.service.supermercado.cooper.dto.variants.product.ProductDTO;
import com.checkbuy.project.service.supermercado.cooper.dto.variants.product.properties.PropertiesDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VariantDTO(
        String presentation,
        List<PriceDTO> prices,
        ProductDTO product,
        List<PropertiesDTO> properties
){}
