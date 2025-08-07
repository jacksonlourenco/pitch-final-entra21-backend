package com.checkbuy.project.service.supermercado.cooper.dto.variants.product;

import com.checkbuy.project.service.supermercado.cooper.dto.variants.product.images.ImageDTO;
import com.checkbuy.project.service.supermercado.cooper.dto.variants.product.properties.PropertiesDTO;

import java.util.List;

public record ProductDTO(
        List<ImageDTO> images,
        List<PropertiesDTO> properties
){}
